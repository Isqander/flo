package com.example.flo.service

import com.example.flo.DTO.CurrencyRatesDto
import com.example.flo.model.Currency
import com.example.flo.model.CurrencyRate
import com.example.flo.repository.CurrencyRateRepository
import com.example.flo.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Service
class CurrencyRateService(
  private val restTemplate: RestTemplate,
  private val currencyRateRepository: CurrencyRateRepository,
  private val productRepository: ProductRepository,
  private val telegramService: TelegramService,
  @Value("\${currency.api-url:https://open.er-api.com/v6/latest/USD}")
  private val apiUrl: String,
  @Value("\${currency.initial-conversion-enabled:true}")
  private val initialConversionEnabled: Boolean
) {
  private val logger = LoggerFactory.getLogger(javaClass)
  private val tbilisiZone = ZoneId.of("Asia/Tbilisi")

  private var cycleDate: LocalDate? = null
  private var cycleSucceeded = false
  private var notificationSentDate: LocalDate? = null

  @EventListener(ApplicationReadyEvent::class)
  fun initializeLegacyPrices() {
    if (!initialConversionEnabled || productRepository.findByPriceCurrency(Currency.RUB).isEmpty()) {
      return
    }

    try {
      refreshRatesAndConvertLegacyPrices()
      logger.info("Converted legacy product prices from RUB to EUR")
    } catch (exception: Exception) {
      logger.error("Could not convert legacy product prices during startup", exception)
    }
  }

  @Scheduled(cron = "\${currency.refresh-cron:0 0 8,9,13 * * *}", zone = "Asia/Tbilisi")
  fun refreshScheduled() {
    val now = Instant.now().atZone(tbilisiZone)
    val today = now.toLocalDate()
    synchronized(this) {
      if (now.hour == 8 || cycleDate != today) {
        cycleDate = today
        cycleSucceeded = false
      }
    }

    try {
      refreshRatesAndConvertLegacyPrices()
      synchronized(this) {
        cycleSucceeded = true
      }
      logger.info("Currency rates updated successfully")
    } catch (exception: Exception) {
      logger.error("Currency rate update failed at {}", now, exception)
      if (now.hour >= 13) {
        sendDailyFailureNotification(today)
      }
    }
  }

  @Transactional
  fun refreshRatesAndConvertLegacyPrices() {
    val ratesToEur = fetchRatesToEur()
    val fetchedAt = Instant.now()
    currencyRateRepository.saveAll(
      ratesToEur.map { (currency, rate) -> CurrencyRate(currency, rate, fetchedAt) }
    )

    val rubRateToEur = ratesToEur.getValue(Currency.RUB)
    val legacyProducts = productRepository.findByPriceCurrency(Currency.RUB)
    if (legacyProducts.isNotEmpty()) {
      productRepository.saveAll(
        legacyProducts.map { product ->
          product.copy(
            price = product.price.multiply(rubRateToEur).setScale(2, RoundingMode.HALF_UP),
            priceCurrency = Currency.EUR
          )
        }
      )
    }
  }

  fun getCurrentRates(): CurrencyRatesDto {
    val savedRates = currencyRateRepository.findAll()
    val rates = linkedMapOf<String, BigDecimal>(Currency.EUR.name to BigDecimal.ONE)
    savedRates.forEach { rate -> rates[rate.currency.name] = rate.unitsPerEur }
    return CurrencyRatesDto(
      rates = rates,
      updatedAt = savedRates.maxOfOrNull { it.fetchedAt }
    )
  }

  private fun fetchRatesToEur(): Map<Currency, BigDecimal> {
    val response = restTemplate.exchange(
      apiUrl,
      HttpMethod.GET,
      HttpEntity.EMPTY,
      object : ParameterizedTypeReference<ExchangeRateResponse>() {}
    )
    val body = response.body ?: error("Currency API returned an empty response")
    if (body.result != "success") {
      error("Currency API returned result '${body.result ?: "unknown"}'")
    }

    val eurRatePerUsd = body.rates[Currency.EUR.name]
      ?: error("Currency API response does not contain EUR")
    return Currency.values().associateWith { currency ->
      if (currency == Currency.EUR) {
        BigDecimal.ONE
      } else {
        val targetRatePerUsd = body.rates[currency.name]
          ?: error("Currency API response does not contain ${currency.name}")
        targetRatePerUsd.divide(eurRatePerUsd, 10, RoundingMode.HALF_UP)
      }
    }
  }

  private fun sendDailyFailureNotification(today: LocalDate) {
    synchronized(this) {
      if (cycleSucceeded || notificationSentDate == today) return
      notificationSentDate = today
    }

    try {
      telegramService.sendTextMessage("Не смогли обновить курсы валют")
    } catch (exception: Exception) {
      logger.error("Could not send currency rate failure notification to Telegram", exception)
    }
  }

  private data class ExchangeRateResponse(
    val result: String? = null,
    val rates: Map<String, BigDecimal> = emptyMap()
  )
}
