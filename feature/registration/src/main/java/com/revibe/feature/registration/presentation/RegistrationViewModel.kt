package com.revibe.feature.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state

    fun onFullNameChange(value: String) {
        _state.value = _state.value.copy(fullName = value)
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun onConfirmPasswordChange(value: String) {
        _state.value = _state.value.copy(confirmPassword = value)
    }

    fun register() {
        val s = _state.value

        if (s.password != s.confirmPassword) {
            _state.value = s.copy(errorMessage = "Пароли не совпадают")
            return
        }

        _state.value = s.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = registerUserUseCase(
                    RegisterRequest(
                        fullName = s.fullName,
                        email = s.email,
                        password = s.password
                    )
                )
                _state.value = s.copy(isLoading = false, success = true)
                // Тут сохранить токен в DataStore/SharedPreferences
            } catch (e: Exception) {
                _state.value = s.copy(isLoading = false, errorMessage = e.message ?: "Неизвестная ошибка")
            }
        }
    }
}