package com.example.flo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "currency_rates")
data class CurrencyRate(
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "currency", length = 3)
  val currency: Currency,
  @Column(name = "units_per_eur", precision = 20, scale = 10, nullable = false)
  val unitsPerEur: BigDecimal,
  @Column(name = "fetched_at", nullable = false)
  val fetchedAt: Instant
)
