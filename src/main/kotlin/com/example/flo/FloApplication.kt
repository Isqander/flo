package com.example.flo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FloApplication

fun main(args: Array<String>) {
  runApplication<FloApplication>(*args)
}
