package com.example.flo.controller

import com.example.flo.DTO.CurrencyRatesDto
import com.example.flo.service.CurrencyRateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/currency")
@Tag(name = "Currency API", description = "Exchange rates used for catalog prices")
class CurrencyController(private val currencyRateService: CurrencyRateService) {

  @Operation(summary = "Get current currency rates", description = "Returns rates relative to one EUR")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Currency rates returned successfully")
  ])
  @GetMapping("/rates")
  fun getRates(): ResponseEntity<CurrencyRatesDto> =
    ResponseEntity.ok(currencyRateService.getCurrentRates())
}
