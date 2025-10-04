package com.example.flo.controller

import com.example.flo.service.PhotoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileNotFoundException

@RestController
@RequestMapping("/api/photos")
@Tag(name = "Photo API", description = "API for retrieving product photos")
class PhotoController(private val photoService: PhotoService) {

    @Operation(summary = "Get photo by filename", description = "Returns a photo file by its filename")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Photo found",
            content = [Content(mediaType = "image/jpeg")]),
        ApiResponse(responseCode = "404", description = "Photo not found", content = [Content()])
    ])
    @GetMapping("/{filename}")
    fun getPhoto(
        @Parameter(description = "Photo filename", required = true)
        @PathVariable filename: String,
        response: HttpServletResponse
    ) {
        try {
            val contentType = photoService.getContentType(filename)
            response.contentType = contentType ?: "application/octet-stream"
            val photoBytes = photoService.getPhoto(filename)
            response.setContentLength(photoBytes.size)
            response.outputStream.use { it.write(photoBytes) }
        } catch (e: FileNotFoundException) {
            response.status = HttpServletResponse.SC_NOT_FOUND
        }
    }
}