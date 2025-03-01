package com.example.shopproject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(onSignUpSuccess: () -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = email, onValueChange = { email = it }, label = {Text("email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = {Text("password") })
        Button(onClick = {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null) {
                        val userData = hashMapOf(
                            "uid" to user.uid,
                            "email" to email,
                            "isAdmin" to (email == "admin@example.com")
                        )
                        firestore.collection("users")
                            .document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                println("User registered with isAdmin set!")
                            }

                    }
                }
                .addOnFailureListener { exception ->
                    println("Sign-up failed: ${exception.message}")
                }


        }){
            Text("Sign Up")
        }
    }
}