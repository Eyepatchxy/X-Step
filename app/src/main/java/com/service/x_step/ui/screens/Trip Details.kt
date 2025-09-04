package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.service.x_step.Trip
import com.service.x_step.data_classes.FirebaseFetchRequests
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetails(
    navController: NavController,
    tripId : String
){

    val ff = remember { FirebaseFetchRequests() }
    var trip by remember { mutableStateOf<Trip?>(null) }

    LaunchedEffect(Unit) {
        ff.fetchtripbyid (tripId) { onResult ->
            trip = onResult
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = scafColor
                ),
                title = {
                    Text(
                        text = "Details"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("triplist") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Trip List",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("profile") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(50.dp),
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
                .verticalScroll(rememberScrollState())
                .background(backGradient),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            HorizontalDivider(
                color = FontBlue,
                thickness = 2.dp
            )

            if (trip != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.padding(15.dp))

                    Text(
                        text = "${trip!!.to}",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.padding(15.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Row() {
                            Text("Starting from :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.from}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }


                        Row() {
                            Text("Date :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.date}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }


                        Row() {
                            Text("Starting Time :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.starttime}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }

                        Row() {
                            Text("Be back by ", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.arrivaltime}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }

                        Row() {
                            Text("Item Size :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.itemSize}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }

                        Row() {
                            Text("Description :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${trip!!.description}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }

                    }

                    Spacer(modifier = Modifier.padding(25.dp))

                    Button(
                        onClick = { navController.navigate("postrequest/${tripId}") },
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        ) {
                        Text("Request Items")
                    }

                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}