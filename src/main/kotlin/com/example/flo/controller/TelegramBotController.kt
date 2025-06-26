package com.example.flo.controller

import com.example.flo.model.Status
import com.example.flo.service.CategoryService
import com.example.flo.service.ProductService
import com.example.flo.service.TelegramService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/telegram")
class TelegramBotController(
        private val telegramService: TelegramService,
        private val categoryService: CategoryService,
        private val productService: ProductService
) {

  @PostMapping("/updates")
  fun handleUpdate(@RequestBody update: Map<String, Any>) {
    // Обратите внимание, что структура update может отличаться:
    // - если пришло обычное текстовое сообщение, это будет update["message"]
    // - если это нажатие на inline-кнопку, будет update["callback_query"]
    // - и т.д.
    // Нужно аккуратно разбирать входящие данные.

    // Проверяем, есть ли у нас поле "message" — значит, пользователь отправил текст:
    val message = update["message"] as? Map<*, *>
    if (message != null) {
      handleIncomingMessage(message)
      return
    }

    // Иначе, если пришёл callback_query (нажатие на inline-кнопку):
    val callbackQuery = update["callback_query"] as? Map<*, *>
    if (callbackQuery != null) {
      handleCallbackQuery(callbackQuery)
      return
    }

    // Другие типы апдейтов можно обрабатывать при необходимости
  }

  private fun handleIncomingMessage(message: Map<*, *>) {
    val chat = message["chat"] as Map<*, *>
    val chatId = chat["id"].toString()  // идентификатор чата, откуда пришло сообщение
    val text = message["text"]?.toString() ?: ""

    when {
      text.startsWith("/start") -> {
        // Пользователю пишем "Введите /catalog"
        telegramService.sendTextMessageToChat(chatId, "Введите /catalog для отображения каталога")
      }

      text.startsWith("/catalog") -> {
        // Выводим пользователю список категорий с inline-кнопками
        sendCategoriesInlineKeyboard(chatId)
      }

      else -> {
        // Можно сделать какую-то обработку по умолчанию
        telegramService.sendTextMessageToChat(chatId, "Неизвестная команда. Введите /catalog.")
      }
    }
  }

  private fun handleCallbackQuery(callbackQuery: Map<*, *>) {
    val chatId = (callbackQuery["message"] as Map<*, *>)["chat"]?.let { (it as Map<*, *>)["id"] }?.toString()
            ?: return
    val callbackData = callbackQuery["data"]?.toString() ?: return

    // Предположим, что в callback_data мы кладём что-то вроде "category_5"
    // где 5 — это ID категории
    if (callbackData.startsWith("category_")) {
      val categoryIdStr = callbackData.removePrefix("category_")
      val categoryId = categoryIdStr.toLongOrNull() ?: return

      // Находим товары в этой категории:
      val categoryProducts = productService.getProductsByFilters(
              categoryIds = listOf(categoryId),
              statuses = listOf(Status.ACTIVE) // например, только активные товары
      )

      if (categoryProducts.isEmpty()) {
        telegramService.sendTextMessageToChat(chatId, "В этой категории пока нет товаров.")
      } else {
        // Отправляем каждый товар в том же формате, как при добавлении
        categoryProducts.forEach { product ->
          telegramService.sendProductMessageToChat(chatId, product)
        }
      }
    }
  }

  private fun sendCategoriesInlineKeyboard(chatId: String) {
    val categories = categoryService.getAllCategories()

    // Формируем inline-клавиатуру
    // Каждый элемент — это список кнопок (список списков).
    // Упрощённо делаем по одной кнопке в ряду.
    val inlineKeyboard: List<List<Map<String, String>>> = categories.map { category ->
      listOf(
              mapOf(
                      "text" to category.name,
                      "callback_data" to "category_${category.id}"
              )
      )
    }

    // Оборачиваем в markup
    val replyMarkup = mapOf("inline_keyboard" to inlineKeyboard)

    telegramService.sendTextMessageToChat(
            chatId = chatId,
            text = "Выберите категорию:",
            replyMarkup = replyMarkup
    )
  }
}
