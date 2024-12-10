package com.example.ecommerce.model

data class Product (
    val id: Long,
    val name: String,
    val price: Double,
    var onCart: Boolean = false
)