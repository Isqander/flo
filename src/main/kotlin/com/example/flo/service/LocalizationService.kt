package com.example.flo.service

import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Size

enum class SupportedLanguage {
    EN,
    RU,
    ZH,
    ES,
    KA
}

object LocalizationService {
    fun resolveLanguage(acceptLanguage: String?): SupportedLanguage {
        val requestedLanguages = acceptLanguage
            ?.split(",")
            ?.asSequence()
            ?.map { it.substringBefore(";").trim().lowercase() }
            ?.map { it.substringBefore("-") }
            ?.mapNotNull(::languageFromCode)
            ?.toList()
            .orEmpty()

        return requestedLanguages.firstOrNull() ?: SupportedLanguage.EN
    }

    fun localizeProduct(product: Product, language: SupportedLanguage): Product = product.copy(
        name = select(
            language,
            product.nameEn,
            product.nameRu,
            product.nameZh,
            product.nameEs,
            product.nameKa,
            product.name
        ),
        description = select(
            language,
            product.descriptionEn,
            product.descriptionRu,
            product.descriptionZh,
            product.descriptionEs,
            product.descriptionKa,
            product.description
        ),
        categories = product.categories.map { localizeCategory(it, language) },
        sizes = product.sizes.map { localizeSize(it, language) }
    )

    fun localizeCategory(category: Category, language: SupportedLanguage): Category = category.copy(
        name = select(language, category.nameEn, category.nameRu, category.nameZh, category.nameEs, category.nameKa, category.name)
    )

    fun localizeSize(size: Size, language: SupportedLanguage): Size = size.copy(
        name = select(language, size.nameEn, size.nameRu, size.nameZh, size.nameEs, size.nameKa, size.name)
    )

    private fun languageFromCode(code: String): SupportedLanguage? = when (code) {
        "en" -> SupportedLanguage.EN
        "ru" -> SupportedLanguage.RU
        "zh" -> SupportedLanguage.ZH
        "es" -> SupportedLanguage.ES
        "ka", "ge" -> SupportedLanguage.KA
        else -> null
    }

    private fun select(
        language: SupportedLanguage,
        english: String?,
        russian: String?,
        chinese: String?,
        spanish: String?,
        georgian: String?,
        legacy: String?
    ): String = when (language) {
        SupportedLanguage.EN -> english
        SupportedLanguage.RU -> russian
        SupportedLanguage.ZH -> chinese
        SupportedLanguage.ES -> spanish
        SupportedLanguage.KA -> georgian
    }?.takeIf { it.isNotBlank() }
        ?: english?.takeIf { it.isNotBlank() }
        ?: legacy.orEmpty()
}
