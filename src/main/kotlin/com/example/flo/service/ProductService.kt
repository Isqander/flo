package com.example.flo.service

import com.example.flo.exception.BadRequestException
import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val photoService: PhotoService,
    private val telegramService: TelegramService
) {

  private val uploadDir: Path = Paths.get("uploads")

  init {
    Files.createDirectories(uploadDir)
  }

  fun saveProduct(product: Product, photos: List<MultipartFile>?): Product {
    val photoPaths = try {
      photos?.map { file ->
        if (file.isEmpty) {
          throw BadRequestException("One or more uploaded files are empty")
        }
        val filename = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filepath = photoService.uploadDir.resolve(filename)
        file.transferTo(filepath)
        filename
      }
    } catch (e: IOException) {
      throw BadRequestException("Failed to upload photos: ${e.message}")
    }

    val productWithPhotos = product.copy(photos = photoPaths)
    val savedProduct = productRepository.save(productWithPhotos)
//    telegramService.sendProductMessage(savedProduct)  //TODO enable Telegram later. Send async (may be WebClient)

    return savedProduct
  }

  fun getProductById(id: Long): Product = productRepository.findById(id)
    .orElseThrow { ResourceNotFoundException("Product not found with id: $id") }

  fun updateProduct(id: Long, updatedProduct: Product, photos: List<MultipartFile>?, photosToDelete: List<String>?): Product {
    val existingProduct = getProductById(id)

    // Delete specified photos from disk and remove from list
    val remainingPhotos = existingProduct.photos?.toMutableList() ?: mutableListOf()
    photosToDelete?.forEach { filename ->
      val file = uploadDir.resolve(filename).toFile()
      if (file.exists()) {
        file.delete()
      }
      remainingPhotos.remove(filename)
    }

    // Add new photos to existing ones
    val newPhotoPaths = try {
      photos?.map { file ->
        if (file.isEmpty) {
          throw BadRequestException("One or more uploaded files are empty")
        }
        val filename = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filepath = uploadDir.resolve(filename)
        file.transferTo(filepath)
        filename
      } ?: emptyList()
    } catch (e: IOException) {
      throw BadRequestException("Failed to upload photos: ${e.message}")
    }

    // Combine remaining and new photos
    val allPhotos = remainingPhotos + newPhotoPaths

    val productToSave = existingProduct.copy(
      name = updatedProduct.name,
      description = updatedProduct.description,
      categories = updatedProduct.categories,
      sizes = updatedProduct.sizes,
      price = updatedProduct.price,
      status = updatedProduct.status,
      photos = allPhotos.ifEmpty { null }
    )
    return productRepository.save(productToSave)
  }

  fun deleteProduct(id: Long) {
    val product = getProductById(id)
    product.photos?.forEach { filename ->
      val file = uploadDir.resolve(filename).toFile()
      if (file.exists()) {
        file.delete()
      }
    }
    productRepository.deleteById(id)
  }

  fun getProductsByFilters(categoryIds: List<Long>?, statuses: List<Status>): List<Product> {
    return if (!categoryIds.isNullOrEmpty()) {
      productRepository.findDistinctByCategories_IdInAndStatusIn(categoryIds, statuses)
    } else {
      productRepository.findByStatusIn(statuses)
    }
  }
}