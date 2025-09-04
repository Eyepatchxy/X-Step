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
import androidx.compose.material3.ButtonDefaults
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
import com.google.firebase.firestore.FirebaseFirestore
import com.service.x_step.Request
import com.service.x_step.data_classes.FirebaseFetchRequests
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetails(
    reqId : String,
    navController: NavController
) {

    val ff = remember { FirebaseFetchRequests() }
    var request by remember { mutableStateOf<Request?>(null) }
    var errormessage by remember { mutableStateOf("") }

    LaunchedEffect(reqId) {
        ff.fetchrequestbyid(reqId) { req ->
            request = req
        }
    }

    request?.let { req ->
        var tripId = req.tripId


    Scaffold(
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
                        onClick = { navController.navigate("requestlist/${tripId}") }
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
        ) {

            HorizontalDivider(
                color = FontBlue,
                thickness = 2.dp
            )

            request.let { req ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.padding(15.dp))

                    req?.item?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Row {
                            Text("Description :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${request!!.itemDesc}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }


                        Row {
                            Text("Estimated Cost :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${request!!.cost}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }


                        Row {
                            Text("Pickup Location :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${request!!.pickupLoc}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }

                        Row {
                            Text("Item Size :", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.weight(1f))

                            Text("${request!!.itemSize}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.padding(15.dp))
                        }


                        if (request?.status == true) {
                            Row {
                                Text("Status :", style = MaterialTheme.typography.bodySmall)

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "Confirmed",
                                    color = Color.Green,
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.padding(15.dp))
                            }
                        }
                        if (request?.status == null) {
                            Row {
                                Text("Status :", style = MaterialTheme.typography.bodySmall)

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "Pending",
                                    color = Color.Gray,
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.padding(15.dp))
                            }
                        }
                        if (request?.status == false) {
                            Row {
                                Text("Status :", style = MaterialTheme.typography.bodySmall)

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "Rejected",
                                    color = Color.Red,
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.padding(15.dp))
                            }
                        }

                        Spacer(modifier = Modifier.padding(25.dp))

                        if ( req?.status == null ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        FirebaseFirestore.getInstance().collection("request")
                                            .document(reqId)
                                            .update("status", true)
                                            .addOnSuccessListener {/*
                                                sendStatusNotificationToUser(
                                                    targetUid = reqId,  // this should be the receiver's UID
                                                    isConfirmed = true,
                                                    requestId = reqId
                                                )*/
                                                navController.navigate("requestlist/${tripId}")
                                            }
                                            .addOnFailureListener { exception ->
                                                errormessage = exception.message.toString()
                                                navController.navigate("requestlist/${tripId}")
                                            }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                                ) {
                                    Text(
                                        text = "Confirm"
                                    )
                                }

                                Spacer(modifier = Modifier.padding(25.dp))

                                Button(
                                    onClick = {
                                        FirebaseFirestore.getInstance().collection("request")
                                            .document(reqId)
                                            .update("status", false)
                                            .addOnSuccessListener {/*
                                                sendStatusNotificationToUser(
                                                    targetUid = reqId,  // this should be the receiver's UID
                                                    isConfirmed = true,
                                                    requestId = reqId
                                                )*/
                                                navController.navigate("requestlist/${tripId}")
                                            }
                                            .addOnFailureListener { exception ->
                                                errormessage = exception.message.toString()
                                                navController.navigate("requestlist/${tripId}")
                                            }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Text("Reject")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

