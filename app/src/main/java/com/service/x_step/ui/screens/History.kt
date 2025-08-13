package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen ( navController: NavController ){


    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = scafColor
                ),
                title = { Text(
                    text = "Trips",
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
                        onClick = { navController.navigate("triplist") }
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(backGradient),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            HorizontalDivider(
                color = FontBlue,
                thickness = 2.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = { navController.navigate("yourpostedtrips") }
                ) {
                    Text(
                        text = "Your Posted Trips"
                    )
                }

                Spacer(modifier = Modifier.padding(15.dp))

                Button(
                    onClick = { navController.navigate("yourpostedrequests") }
                ) {
                    Text(
                        text = "Your Posted Requests"
                    )
                }
            }
        }

    }
}