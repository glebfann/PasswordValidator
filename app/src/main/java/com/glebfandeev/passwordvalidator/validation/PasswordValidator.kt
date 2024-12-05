package com.glebfandeev.passwordvalidator.validation

class PasswordValidator(
    private var rules: List<ValidationRule>
) {
    fun validate(password: String): Pair<Boolean, List<String>> {
        val failedRules = rules.filterNot { it.validate(password) }
        return failedRules.isEmpty() to failedRules.map { it.errorMessage }
    }

    fun update(rules:  List<ValidationRule>) {
        this.rules = rules
    }
}