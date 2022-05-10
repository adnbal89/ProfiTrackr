package com.fxingsign.profitrackr.util.validator

data class ValidationResult(
    var successful: Boolean,
    var errorMessage: String? = null
)