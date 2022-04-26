package com.fxingsign.profitrackr.util.functional.validator

class LiveDataValidatorResolver<T>(
    private val validators: List<DataValidator<T>>
) {
    fun isValid(): Boolean {
        for (validator in validators) {
            if (!validator.isValid()) return false
        }
        return true
    }
}