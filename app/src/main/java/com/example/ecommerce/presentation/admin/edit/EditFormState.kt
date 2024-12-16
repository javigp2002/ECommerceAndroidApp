package com.example.ecommerce.presentation.admin.edit

data class EditFormState(
    val nameError: Int? = null,
    val priceError: Int? = null,
    val isDataValid: Boolean = false
)
