package com.example.ecommerce.presentation.admin.edit

data class AddFormState(
    val nameError: Int? = null,
    val priceError: Int? = null,
    val isDataValid: Boolean = false
)
