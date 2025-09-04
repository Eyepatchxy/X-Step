package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.service.x_step.AuthManager.logInWithEmail
import com.service.x_step.ui.theme.LinkBlue
import com.service.x_step.ui.theme.backGradient
import com.google.firebase.messaging.FirebaseMessagingService


@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var token by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGradient)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge
            )



            Spacer(modifier = Modifier.padding(15.dp))

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Spacer(modifier = Modifier.padding(15.dp))

                    underlinedTextField(
                        value = email,
                        onValueChange = { email = it},
                        label = "College Email",
                        icon = Icons.Default.Email,
                        description = "Email",
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    passwordTextField(
                        Password = password,
                        onPasswordChange = { password = it },
                        label = "Password"
                    )//Password

                    Spacer(modifier = Modifier.padding(15.dp))

                    Button(
                        onClick = {
                            logInWithEmail(
                                email = email,
                                password = password,
                                onSuccess = {
                                    navController.navigate("triplist")
                                },
                                onFailure = { exception ->
                                    errorMessage = exception.message
                                }
                            )

                            FirebaseMessaging.getInstance().token
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        token = task.result

                                        val uid = FirebaseAuth.getInstance().currentUser?.uid

                                        data class Token (val fmcToken: String)

                                        if (uid != null) {
                                            FirebaseFirestore.getInstance().collection("user")
                                                .document(uid)
                                                .set( Token(token), SetOptions.merge() )
                                        }
                                    }


                                    android.util.Log.d("FCM", "FCM token: $token")

                                }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    //Work on this
                    errorMessage?.let {
                        Text(
                            it, modifier = Modifier
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterHorizontally),
                            color = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))


            Text(
                text = "Forgot Password?",
                color = Color(0xFF42A5F5),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("forgot")
                    }
            )


            Text(
                text = "New to X-Step? Sign Up",
                color = LinkBlue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("signup")
                    }
            )


        }
    }
}