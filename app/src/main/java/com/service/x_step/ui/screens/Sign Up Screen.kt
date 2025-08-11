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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PermIdentity
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.service.x_step.AuthManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import com.service.x_step.ui.theme.LinkBlue
import com.service.x_step.ui.theme.backGradient


@Composable
fun SignUpScreen(navController: NavHostController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var upiId by remember { mutableStateOf("") }
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Spacer(modifier = Modifier.padding(15.dp))

                    underlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name (First and Last)",
                        icon = Icons.Default.PermIdentity,
                        description = "Name"
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    underlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "College Email",
                        icon = Icons.Default.Email,
                        description = "Email"
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    passwordTextField(
                        Password = password,
                        onPasswordChange = { password = it },
                        label = "Password"
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    passwordTextField(
                        Password = confirmpassword,
                        onPasswordChange = { confirmpassword = it },
                        label = "Confirm Password"
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    underlinedTextField(
                        value = upiId,
                        onValueChange = { upiId = it },
                        label = "UPI Id",
                        icon = Icons.Default.AttachMoney,
                        description = "Name"
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    Button(
                        onClick = {
                            if (!emailconstraint(email)) {
                                Toast.makeText(context, "Use College Email.", Toast.LENGTH_SHORT)
                                    .show()
                                return@Button
                            }

                            if (!dopasswordsmatch(password, confirmpassword)) {
                                Toast.makeText(
                                    context,
                                    "Passwords do not match.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            AuthManager.signUpWithEmail(
                                name = name,
                                email = email,
                                password = password,
                                upiId = upiId,
                                onSuccess = {
                                    navController.navigate("login")
                                },
                                onFailure = { exception ->
                                    errorMessage = exception.message
                                }
                            )
                        },
                        modifier = Modifier.padding(all = 8.dp)

                    ) {
                        Text("Sign Up")
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
                text = "Already Signed Up? Log In",
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

