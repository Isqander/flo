package com.example.flo.service

import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class PhotoService {

    final val uploadDir: Path = Paths.get("uploads")

    init {
        Files.createDirectories(uploadDir)
    }

    fun getPhoto(filename: String): ByteArray {
        val file = uploadDir.resolve(filename).toFile()

        if (file.exists()) {
            return file.readBytes()
        } else {
            throw FileNotFoundException("File not found: $filename")
        }
    }

    fun getContentType(filename: String): String? {
        val file = uploadDir.resolve(filename).toFile()
        return if (file.exists()) {
            Files.probeContentType(file.toPath())
        } else {
            null
        }
    }
}