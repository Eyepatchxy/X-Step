package com.service.x_step.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor
import kotlinx.coroutines.launch



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostATrip(navController: NavController) {

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val itemSizeOptions = listOf("Small (Handbag)", "Medium (Backpack)", "Large (Multiple Backpacks)")
    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var departureTime by remember { mutableStateOf("") }
    var arrivalTime by remember { mutableStateOf("") }
    var itemSize by remember { mutableStateOf("") }
    var tempUpiId by remember { mutableStateOf("") }
    var permUpiId: String? by remember { mutableStateOf("") }
    var roundTrip by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(false) }
    var cod by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    var errorMessage by remember { mutableStateOf<String?> ("null") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var tripUpiId: String? by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (uid != null) {
            fetchtripbyuser(uid){ list ->
                permUpiId = list.firstOrNull()?.upiId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = scafColor
                ),
                title = { Text(
                    text = "Post A Trip",
                    style = MaterialTheme.typography.titleLarge
                ) },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("triplist") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "TripList",
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
        },

        snackbarHost = { SnackbarHost (hostState = snackbarHostState) }

    ) { innerpadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerpadding)
                .background(backGradient),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            HorizontalDivider(
                color = FontBlue,
                thickness = 2.dp
            )

//            Spacer(modifier = Modifier.padding(15.dp))

            //Icon

            Column(
                modifier = Modifier.padding(30.dp)
            ) {

                Card(
                    modifier = Modifier

                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),

                    ) {
                    Column(
                        modifier = Modifier
//                        .width(300.dp)
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        //Spacer(modifier = Modifier.padding(1.dp))

                        Text(
                            "Trip Details :",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.padding(1.dp))

                        underlinedFormField(to, { to = it }, "Destination")

                        underlinedFormField(from, { from = it }, "Starting Place")

                        DatePickerField(
                            selectedDate = date,
                            onDateSelected = { date = it }
                        )

                        //underlinedFormField(departureTime, { departureTime = it }, "Time of Departure")
                        TimePickerField(
                            selectedTime = departureTime,
                            onTimeSelected = { departureTime = it },
                            label = "Time of Departure"
                        )

                        //underlinedFormField(arrivalTime, { arrivalTime = it }, "Estimated Time of Arrival")
                        TimePickerField(
                            selectedTime = arrivalTime,
                            onTimeSelected = { arrivalTime = it },
                            label = "Estimated Time of Arrival"
                        )

                        ///underlinedFormField(itemSize, { itemSize = it }, "Expected Request Item Size")
                        DropdownMenuField(
                            label = "Item Size",
                            options = itemSizeOptions,
                            selectedOption = itemSize,
                            onOptionSelected = { itemSize = it }
                        )

                        underlinedFormField(notes, { notes = it }, "Description (Optional)")

                        /*toggleSwitch(
                        label = "Is this a return trip?",
                        checked = roundTrip,
                        onCheckedChange = { roundTrip = it }
                    )*/

                        toggleSwitch(
                            label = "Use default UPI Id?",
                            checked = show,
                            onCheckedChange = { show = it }
                        )


                        if (show) {
                            underlinedFormField(tempUpiId, { tempUpiId = it }, "Temporary UPI Id")
                        }

                        /*
                    toggleSwitch(
                        label = "Pay on delivery?",
                        checked = cod,
                        onCheckedChange = { cod = it }
                    )
                    */

                        //underlinedFormField(allowedItems, { allowedItems = it }, "Item Restrictions (Optional)")

                        Spacer(modifier = Modifier.padding(15.dp))




                        Button(
                            onClick = {

                                if (from.isBlank() || to.isBlank() || date.isBlank() || departureTime.isBlank() || itemSize.isBlank()) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Please fill all required fields.")
                                    }
                                    return@Button
                                }

                                val triptime = try {
                                    millicon(date, departureTime)
                                } catch (e: Exception) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Invalid date or time format.")
                                    }
                                    return@Button
                                }

                                itemSize = when (itemSize) {
                                    "Large (Multiple Backpacks)" -> "Large"
                                    "Medium (Backpack)" -> "Medium"
                                    "Small (Handbag)" -> "Small"
                                    else -> itemSize
                                }

                                if (show) {
                                    tripUpiId = tempUpiId
                                } else {
                                    tripUpiId = permUpiId
                                }

                                val tripData = hashMapOf(
                                    "postTime" to System.currentTimeMillis(),
                                    "startLoc" to from,
                                    "endLoc" to to,
                                    "tripDate" to date,
                                    "tripStartTime" to departureTime,
                                    "tripArrivalTime" to arrivalTime,
                                    "roundTrip" to roundTrip,
                                    "cod" to cod,
                                    "itemSize" to itemSize,
                                    "description" to notes,
                                    "userId" to uid,
                                    "triptime" to triptime,
                                    "upiId" to tripUpiId
                                )


                                db.collection("trip")
                                    .add(tripData)
                                    .addOnSuccessListener { docRef ->
                                        val tripId = docRef.id
                                        docRef.update("tripId", tripId)
                                        navController.navigate("triplist")
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Your Trip has been saved.")
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        errorMessage = exception.message
                                    }

                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(15.dp)
                                .width(150.dp)
                                .height(50.dp)
                        ) {
                            Text("Post")
                        }
                    }
                }
            }
        }
    }
}



