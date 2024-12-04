package com.example.flo.controller

import com.example.flo.service.PhotoService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileNotFoundException

@RestController
@RequestMapping("/api/photos")
class PhotoController(private val photoService: PhotoService) {

    @GetMapping("/{filename}")
    fun getPhoto(@PathVariable filename: String, response: HttpServletResponse) {
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