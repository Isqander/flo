package com.example.flo.service

import com.example.flo.model.Product
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@Service
class TelegramService(
    private val restTemplate: RestTemplate,
    private val telegramBotToken: String,
    private val telegramChatId: String
) {

    fun sendProductMessage(product: Product) {
        if (product.photos.isNullOrEmpty()) {
            sendTextMessage(buildMessage(product))
        } else {
            sendMediaGroup(product)
        }
    }

    private fun sendMediaGroup(product: Product) {
        val url = "https://api.telegram.org/bot$telegramBotToken/sendMediaGroup"

        val media = product.photos!!.mapIndexed { index, photoUrl ->
            mutableMapOf(
                "type" to "photo",
                "media" to photoUrl//TODO сделать ссылку на полный урл!
            ).apply {
                if (index == 0) {
                    this["caption"] = buildMessage(product)
                    this["parse_mode"] = "HTML"
                }
            }
        }

        val request = mapOf(
            "chat_id" to telegramChatId,
            "media" to media
        )

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val entity = HttpEntity(request, headers)
        restTemplate.postForEntity(url, entity, String::class.java)
    }

    private fun sendTextMessage(message: String) {
        val url = "https://api.telegram.org/bot$telegramBotToken/sendMessage"

        val request = mapOf(
            "chat_id" to telegramChatId,
            "text" to message,
            "parse_mode" to "HTML"
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(request, headers)
        restTemplate.postForEntity(url, entity, String::class.java)
    }

    private fun buildMessage(product: Product): String {
        return """
            <b>${product.name}</b> 
            ${product.description}
            <b>Стоимость:</b> ${product.price}₽
        """.trimIndent()
    }
}