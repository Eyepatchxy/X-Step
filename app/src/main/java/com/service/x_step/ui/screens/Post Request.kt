package com.service.x_step.ui.screens

import android.nfc.cardemulation.CardEmulation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.service.x_step.Trip
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostRequest (
    navController: NavController,
    tripId : String
){
    val tripId = tripId
    var trip by remember { mutableStateOf<Trip?>(null) }
    var item by remember { mutableStateOf("") }
    var itemdes by remember { mutableStateOf("") }
    var pickuploc by remember { mutableStateOf("") }
    val itemSizeOptions = listOf("Small (Handbag)", "Medium (Backpack)", "Large (Multiple Backpacks)")
    var itemSize by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    val payoptions = listOf("Cash on Delivery", "Pay now")
    var upiId by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var errormessage by remember { mutableStateOf<String?> ("null") }


    LaunchedEffect(Unit) {
        fetchtripbyid(tripId){ onResult ->
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
                    Text("Request")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("tripDetail/${tripId}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Trip Details",
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

        snackbarHost = { SnackbarHost( hostState = snackbarHostState ) }

    ){ innerpadding ->

        Column (
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

            Spacer(modifier = Modifier.padding(50.dp))

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = 

                ) {

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .width(300.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        "Item Details :",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    underlinedFormField(item, { item = it }, "Item")

                    underlinedFormField(itemdes, { itemdes = it }, "Item Description")

                    underlinedFormField(cost, { cost = it }, "Estimated Cost")

                    underlinedFormField(pickuploc, { pickuploc = it }, "Pickup Location")

                    DropdownMenuField(
                        label = "Item Size",
                        options = itemSizeOptions,
                        selectedOption = itemSize,
                        onOptionSelected = { itemSize = it }
                    )

                    /*
                DropdownMenuField(
                    label = "Payment Method",
                    options = payoptions,
                    selectedOption = pay,
                    onOptionSelected = { pay = it }
                )

                if ( pay == "Pay now" ){
                    FirebaseFirestore.getInstance().collection("trip").document(tripId)
                        .get()
                        .addOnSuccessListener { doc ->
                            if ( doc.exists() ){
                                upiId = doc.getString("upiId").toString()
                            }
                        }

                    Text(
                        text = upiId
                    )
                }
                */
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = {

                                itemSize = when (itemSize) {
                                    "Large (Multiple Backpacks)" -> "Large"
                                    "Medium (Backpack)" -> "Medium"
                                    "Small (Handbag)" -> "Small"
                                    else -> itemSize
                                }

                                val requestData = hashMapOf(
                                    "tripId" to tripId,
                                    "rqTime" to System.currentTimeMillis(),
                                    "rqUser" to Firebase.auth.currentUser?.uid,
                                    "item" to item,
                                    "itemDesc" to itemdes,
                                    "itemSize" to itemSize,
                                    "pickupLoc" to pickuploc,
                                    "cost" to cost,
                                    "status" to null
                                )

                                FirebaseFirestore.getInstance().collection("request")
                                    .add(requestData)
                                    .addOnSuccessListener { docRef ->
                                        val reqId = docRef.id
                                        docRef.update("reqId", reqId)
                                        navController.navigate("tripDetail/${tripId}")
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Your Request has been noted")
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        errormessage = exception.message
                                    }
                            }
                        ) {
                            Text(
                                text = "Submit"
                            )
                        }
                    }

                }
            }
        }
    }
}


