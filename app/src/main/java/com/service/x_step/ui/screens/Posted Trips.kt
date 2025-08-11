package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.service.x_step.Trip
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripHistory(navController: NavController){

    var tripList by remember { mutableStateOf<List<Trip>>(emptyList()) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(Unit) {
        if (uid != null) {
            fetchtripbyuser(uid){ list ->
                tripList = list
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
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
                            modifier = Modifier.size(50.dp)
                        )
                    }
                },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("history") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Trip List"
                        )
                    }
                }

            )
        }
    ){ innerpadding ->

        Column(
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
               items(tripList) { trip ->

                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(15.dp)
                           .clickable { navController.navigate("requestlist/${trip.id}") }
                   ) {
                       Column {
                           Text(
                               text = trip.to,
                               style = MaterialTheme.typography.bodyMedium,
                               textAlign = TextAlign.Start
                           )

                           Spacer(modifier = Modifier.padding(10.dp))

                           Row {
                               Text(
                                   text = trip.from,
                                   style = MaterialTheme.typography.bodySmall
                               )

                               Spacer(modifier = Modifier.weight(2f))

                               Text(
                                   text = trip.date,
                                   style = MaterialTheme.typography.bodySmall
                               )

                               Spacer(modifier = Modifier.weight(2f))

                               Text(
                                   text = trip.starttime,
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