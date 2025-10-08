package com.example.flo

import com.example.flo.security.SecurityProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties::class)
class FloApplication

fun main(args: Array<String>) {
  runApplication<FloApplication>(*args)
}
