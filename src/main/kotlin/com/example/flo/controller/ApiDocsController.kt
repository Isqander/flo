package com.example.flo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView

/**
 * Controller that provides convenient redirects to the API documentation
 */
@Controller
class ApiDocsController {

    /**
     * Redirects the root path to the Swagger UI
     */
    @GetMapping("/")
    fun redirectToSwaggerUi(): RedirectView {
        return RedirectView("/swagger-ui.html")
    }

    /**
     * Redirects the /api path to the Swagger UI
     */
    @GetMapping("/api")
    fun apiToSwaggerUi(): RedirectView {
        return RedirectView("/swagger-ui.html")
    }

    /**
     * Redirects the /docs path to the Swagger UI
     */
    @GetMapping("/docs")
    fun docsToSwaggerUi(): RedirectView {
        return RedirectView("/swagger-ui.html")
    }
}
