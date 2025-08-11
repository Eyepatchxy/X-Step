package com.service.x_step.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.service.x_step.Request
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestList(
    tripId: String,
    navController: NavController
){
    var requestList by remember { mutableStateOf<List<Request>>(emptyList()) }
    var errormessage by remember { mutableStateOf<String?>("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(tripId) {
        fetchrequestbytripId(tripId) { list ->
            requestList = list
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
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
                            modifier = Modifier.size(50.dp)
                        )
                    }
                },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("yourpostedtrips") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Trip List"
                        )
                    }
                }
            )
        },

        snackbarHost = { SnackbarHost( hostState = snackbarHostState ) }

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
                thickness = 2.dp,
                color = FontBlue
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(requestList) { request ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable { navController.navigate("requestDetail/${request.reqId}") }
                    ){
                        Column {
                            Text(
                                text = request.item,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )

                            Spacer(modifier = Modifier.padding(10.dp))

                            Row {
                                Text(
                                    text = request.cost,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.weight(2f))

                                Text(
                                    text = request.pickupLoc,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.weight(2f))

                                if (request.status == true) {
                                    Text(
                                        text = "Confirmed",
                                        color = Color.Green,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                if (request.status == null) {
                                    Text(
                                        text = "Pending",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                if (request.status == false) {
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
/*        if ( selectedrequest != null ){
            AlertDialog(
                onDismissRequest = { selectedrequest = null },

                title = { Text(
                    text = "Confirm Request",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ) },

                text = { Text(
                    text = "Do you want to confirm this request?",
                    fontSize = 15.sp
                    ) },

                confirmButton =  {
                    TextButton(
                        onClick = {
                            selectedrequest?.let { req ->
                                FirebaseFirestore.getInstance().collection("request")
                                    .document(req.reqId)
                                    .update("status", true)
                                    .addOnSuccessListener {
                                        selectedrequest = null
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Request Confirmed")
                                        }
                                    }
                                    .addOnFailureListener{ exception ->
                                        errormessage = exception.message
                                        selectedrequest = null
                                    }
                            }
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedrequest = null                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
*/
    }
}
