
package com.example.shopproject.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopproject.data.NetworkModule
import com.example.shopproject.data.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val apiService = NetworkModule.apiService

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorMessage.isNotEmpty()
        )

        // Error Message
        AnimatedVisibility(
            visible = errorMessage.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    errorMessage = "Please fill in all fields"
                    return@Button
                }
                if (password.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                    return@Button
                }
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }
                isLoading = true
                errorMessage = ""
                coroutineScope.launch {
                    try {
                        val response = apiService.register(
                            RegisterRequest(email, password, confirmPassword)
                        )
                        // Save token (e.g., in SharedPreferences) and user data
                        // For simplicity, assume success
                        isLoading = false
                        isSuccess = true
                        onSignUpSuccess()
                    } catch (e: HttpException) {
                        isLoading = false
                        errorMessage = "Sign-up failed: ${e.message()}"
                    } catch (e: IOException) {
                        isLoading = false
                        errorMessage = "Network error: ${e.message}"
                    } catch (e: Exception) {
                        isLoading = false
                        errorMessage = "Unexpected error: ${e.message}"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .animateContentSize(),
            enabled = !isLoading,
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            } else {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Link
        TextButton(onClick = onLoginClick) {
            Text(
                text = "Already have an account? Log in",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}