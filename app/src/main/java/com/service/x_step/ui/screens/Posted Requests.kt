package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.service.x_step.Request
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestHistory( navController: NavController ) {

    var requestList by remember { mutableStateOf<List<Request>>(emptyList()) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(uid) {
        if (uid != null) {
            fetchrequestbyuser(uid){ list ->
                requestList = list
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = scafColor
                ),
                title = { Text(
                    text = "Requests",
                    style = MaterialTheme.typography.titleLarge) },

                actions = {
                    IconButton(
                        onClick = { navController.navigate("profile") }
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(50.dp),
                            tint = Color.White
                        )
                    }
                },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("history") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Trip List",
                            tint = Color.White
                        )
                    }
                }

            )
        }
    ) { innerpadding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(backGradient),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            HorizontalDivider(
                color = FontBlue,
                thickness = 2.dp
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(requestList) { req ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Column {
                            Text(
                                text = req.item,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )

                            Spacer(modifier = Modifier.padding(10.dp))

                            Row {
                                Text(
                                    text = req.pickupLoc,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.weight(2f))

                                Text(
                                    text = req.cost,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.weight(2f))

                                if (req.status == true) {
                                    Text(
                                        text = "Confirmed",
                                        color = Color.Green,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                if (req.status == null) {
                                    Text(
                                        text = "Pending",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                if (req.status == false) {
                                    Text(
                                        text = "Rejected",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}