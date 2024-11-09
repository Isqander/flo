package com.example.flo.service

import com.example.flo.model.Product
import com.example.flo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {

  private val uploadDir: Path = Paths.get("uploads")

  init {
    Files.createDirectories(uploadDir)
  }

  fun saveProduct(product: Product, photos: List<MultipartFile>?): Product {
    val photoPaths = photos?.map { file ->
      val filename = UUID.randomUUID().toString() + "_" + file.originalFilename
      val filepath = uploadDir.resolve(filename)
      file.transferTo(filepath)
      filepath.toString()
    }
    val productWithPhotos = product.copy(photos = photoPaths)
    return productRepository.save(productWithPhotos)
  }

  fun getProductById(id: Long): Product = productRepository.findById(id).orElseThrow { Exception("Product not found") }

  fun updateProduct(id: Long, updatedProduct: Product, photos: List<MultipartFile>?): Product {
    val existingProduct = getProductById(id)
    val photoPaths = photos?.map { file ->
      val filename = UUID.randomUUID().toString() + "_" + file.originalFilename
      val filepath = uploadDir.resolve(filename)
      file.transferTo(filepath)
      filepath.toString()
    } ?: existingProduct.photos

    val productToSave = existingProduct.copy(
      name = updatedProduct.name,
      description = updatedProduct.description,
      categories = updatedProduct.categories,
      price = updatedProduct.price,
      status = updatedProduct.status,
      photos = photoPaths
    )
    return productRepository.save(productToSave)
  }

  fun deleteProduct(id: Long) {
    val product = getProductById(id)
    product.photos?.forEach { File(it).delete() }
    productRepository.deleteById(id)
  }

  fun getProductsByCategory(categoryId: Long?): List<Product> {
    return if (categoryId != null) {
      productRepository.findDistinctByCategories_IdIn(listOf(categoryId))
    } else {
      productRepository.findAll()
    }
  }
}