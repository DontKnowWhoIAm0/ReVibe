package com.revibe.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.core.data.local.TokenDataStore
import com.revibe.feature.login.domain.model.LoginRequest
import com.revibe.feature.login.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(
            email = value,
            emailError = validateEmail(value)
        )
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(
            password = value,
            passwordError = validatePassword(value)
        )
    }

    fun onTogglePasswordVisibility() {
        _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
    }

    fun login() {
        val s = _state.value

        val emailError = validateEmail(s.email)
        val passwordError = validatePassword(s.password)

        if (emailError != null || passwordError != null) {
            _state.value = s.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        _state.value = s.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = loginUserUseCase(
                    LoginRequest(
                        email = s.email.trim(),
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

    private fun validateEmail(value: String): String? = when {
        value.isBlank() -> "Введите email"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches() -> "Некорректный email"
        else -> null
    }

    private fun validatePassword(value: String): String? = when {
        value.isBlank() -> "Введите пароль"
        value.length < 8 -> "Пароль должен быть не менее 8 символов"
        else -> null
    }
}