package com.example.flo.service

import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Size
import com.example.flo.repository.SizeRepository
import org.springframework.stereotype.Service

@Service
class SizeService(private val sizeRepository: SizeRepository) {
  fun saveSize(size: Size): Size = sizeRepository.save(size)
  fun getAllSizes(): List<Size> = sizeRepository.findByDeletedFalse()
  fun deleteSizeById(id: Long) {
    val size = sizeRepository.findById(id)
      .orElseThrow { ResourceNotFoundException("Size not found with id: $id") }

    // Soft delete: mark as deleted instead of physically removing
    sizeRepository.save(size.copy(deleted = true))
  }
}
