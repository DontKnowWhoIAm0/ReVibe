package com.revibe.feature.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.core.data.local.TokenDataStore
import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationUiState())
    val state: StateFlow<RegistrationUiState> = _state

    fun onFullNameChange(value: String) {
        _state.value = _state.value.copy(
            fullName = value,
            fullNameError = validateFullName(value)
        )
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(
            email = value,
            emailError = validateEmail(value)
        )
    }

    fun onPasswordChange(value: String) {
        val confirmError = if (
            _state.value.confirmPassword.isNotEmpty() &&
            _state.value.confirmPassword != value
        ) "Пароли не совпадают" else null

        _state.value = _state.value.copy(
            password = value,
            passwordError = validatePassword(value),
            confirmPasswordError = confirmError
        )
    }

    fun onConfirmPasswordChange(value: String) {
        _state.value = _state.value.copy(
            confirmPassword = value,
            confirmPasswordError = if (value != _state.value.password) "Пароли не совпадают" else null
        )
    }

    fun onTogglePasswordVisibility() {
        _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
    }

    fun onToggleConfirmPasswordVisibility() {
        _state.value = _state.value.copy(isConfirmPasswordVisible = !_state.value.isConfirmPasswordVisible)
    }

    fun register() {
        val s = _state.value

        val fullNameError = validateFullName(s.fullName)
        val emailError = validateEmail(s.email)
        val passwordError = validatePassword(s.password)
        val confirmError = if (s.password != s.confirmPassword) "Пароли не совпадают" else null

        if (fullNameError != null || emailError != null || passwordError != null || confirmError != null) {
            _state.value = s.copy(
                fullNameError = fullNameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmError
            )
            return
        }

        _state.value = s.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = registerUserUseCase(
                    RegisterRequest(
                        fullName = s.fullName.trim(),
                        email    = s.email.trim(),
                        password = s.password
                    )
                )
                tokenDataStore.saveToken(response.token)

                _state.value = _state.value.copy(isLoading = false, success = true)
            } catch (e: Exception) {
                _state.value = s.copy(isLoading = false, errorMessage = e.message ?: "Неизвестная ошибка")
            }
        }
    }

    private fun validateFullName(value: String): String? = when {
        value.isBlank() -> "Введите имя"
        value.trim().length < 2 -> "Имя слишком короткое"
        else -> null
    }

    private fun validateEmail(value: String): String? = when {
        value.isBlank() -> "Введите email"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches() -> "Некорректный email"
        else -> null
    }

    private fun validatePassword(value: String): String? = when {
        value.isBlank() -> "Введите пароль"
        value.length < 8 -> "Пароль должен быть не менее 8 символов"
        !value.any { it.isUpperCase() } -> "Пароль должен содержать заглавную букву"
        !value.any { it.isDigit() } -> "Пароль должен содержать цифру"
        else -> null
    }
}