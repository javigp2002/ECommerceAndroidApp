package com.example.ecommerce.domain.repository.model

data class Product (
    val id: Long,
    val name: String,
    val price: Double,
    var onCart: Boolean = false
)