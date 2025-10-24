package com.example.flo.service

import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Size
import com.example.flo.repository.SizeRepository
import org.springframework.stereotype.Service

@Service
class SizeService(private val sizeRepository: SizeRepository) {
  fun saveSize(size: Size): Size = sizeRepository.save(size)
  fun getAllSizes(): List<Size> = sizeRepository.findAll()
  fun deleteSizeById(id: Long) {
    if (!sizeRepository.existsById(id)) {
      throw ResourceNotFoundException("Size not found with id: $id")
    }
    sizeRepository.deleteById(id)
  }
}
