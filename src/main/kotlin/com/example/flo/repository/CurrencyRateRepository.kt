package com.example.flo.repository

import com.example.flo.model.Currency
import com.example.flo.model.CurrencyRate
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRateRepository : JpaRepository<CurrencyRate, Currency>
