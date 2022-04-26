package com.fxingsign.profitrackr.util.functional.validator

//A predicate is a function that evaluates to true when its param matches the condition of the predicate
typealias Predicate = (value: String) -> Boolean

class DataValidator<T>(private val data: T) {
    private val validationRules = mutableListOf<Predicate>()
    private val errorMessages = mutableListOf<String>()


    var error: String? = ""

    //For checking if the liveData value matches the error condition set in the validation rule predicate
    //The livedata value is said to be valid when its value doesn't match an error condition set in the predicate
    fun isValid(): Boolean {
        for (i in 0 until validationRules.size) {
            if (validationRules[i](data.toString())) {
                emitErrorMessage(errorMessages[i])
                return false
            }
        }

        emitErrorMessage(null)
        return true
    }

    //For emitting error messages
    private fun emitErrorMessage(messageRes: String?) {
        error = messageRes
    }

    //For adding validation rules
    fun addRule(errorMsg: String, predicate: Predicate) {
        validationRules.add(predicate)
        errorMessages.add(errorMsg)
    }
}