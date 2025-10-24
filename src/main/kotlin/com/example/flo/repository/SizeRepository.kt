package com.example.flo.repository

import com.example.flo.model.Size
import org.springframework.data.jpa.repository.JpaRepository

interface SizeRepository : JpaRepository<Size, Long>
