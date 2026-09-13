package com.example.flo.DTO

import com.example.flo.model.Currency
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.Instant

@Schema(description = "Latest exchange rates relative to the EUR base currency")
data class CurrencyRatesDto(
  @Schema(description = "Base currency", example = "EUR")
  val baseCurrency: Currency = Currency.EUR,
  @Schema(description = "Amount of each currency for one EUR")
  val rates: Map<String, BigDecimal>,
  @Schema(description = "Time of the last successful update")
  val updatedAt: Instant?
)
