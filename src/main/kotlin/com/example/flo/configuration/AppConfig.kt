package com.example.flo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import java.time.Duration

@Configuration
class AppConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(10))
        .setReadTimeout(Duration.ofSeconds(10))
        .build()

    @Bean
    fun telegramBotToken(
        @Value("\${telegram.bot-token}") botToken: String
    ): String = botToken

    @Bean
    fun telegramChatId(
        @Value("\${telegram.chat-id}") chatId: String
    ): String = chatId
}
