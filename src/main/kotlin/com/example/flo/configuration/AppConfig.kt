package com.example.flo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun telegramBotToken(
        @Value("\${telegram.bot-token}") botToken: String
    ): String = botToken

    @Bean
    fun telegramChatId(
        @Value("\${telegram.chat-id}") chatId: String
    ): String = chatId
}