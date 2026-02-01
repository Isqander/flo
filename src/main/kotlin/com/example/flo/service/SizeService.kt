package com.example.flo.service

import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Size
import com.example.flo.repository.SizeRepository
import org.springframework.stereotype.Service

@Service
class SizeService(private val sizeRepository: SizeRepository) {
  fun saveSize(size: Size): Size = sizeRepository.save(size)
  fun getAllSizes(): List<Size> = sizeRepository.findByDeletedFalseOrderBySortOrderAsc()
  fun deleteSizeById(id: Long) {
    val size = sizeRepository.findById(id)
      .orElseThrow { ResourceNotFoundException("Size not found with id: $id") }

    // Soft delete: mark as deleted instead of physically removing
    sizeRepository.save(size.copy(deleted = true))
  }

  fun reorderSizes(ids: List<Long>): List<Size> {
    if (ids.isEmpty()) return getAllSizes()
    val sizes = sizeRepository.findAllById(ids)
    if (sizes.size != ids.size) {
      val foundIds = sizes.map { it.id }.toSet()
      val missingIds = ids.filter { it !in foundIds }
      throw ResourceNotFoundException("Sizes not found: $missingIds")
    }
    val byId = sizes.associateBy { it.id }
    val reordered = ids.mapIndexed { index, id ->
      byId.getValue(id).copy(sortOrder = index)
    }
    return sizeRepository.saveAll(reordered).toList()
  }
}
