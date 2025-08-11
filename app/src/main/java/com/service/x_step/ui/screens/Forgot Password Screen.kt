package com.service.x_step.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.service.x_step.ui.theme.GradBlue
import com.service.x_step.ui.theme.LinkBlue
import com.service.x_step.ui.theme.backGradient

@Composable
fun ForgotPasswordScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
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
                        onValueChange = { email = it },
                        label = "College Email",
                        icon = Icons.Default.Email,
                        description = "Email"

                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    Button(
                        onClick = {

                            if (emailconstraint(email = email)) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                    .addOnCompleteListener { task ->
                                        message = if (task.isSuccessful) {
                                            "Password reset link sent to the email address."
                                        } else {
                                            task.exception?.message ?: "Something went wrong."
                                        }
                                    }
                            } else {
                                message = "Enter College Email ID Only."
                            }

                        }
                    ) {
                        Text("Click to get link")
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

            message?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = "Return to Login",
                color = LinkBlue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("login")
                    }
            )
        }
    }
}