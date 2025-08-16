package com.service.x_step.ui.screens

import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cottage
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.isPopupLayout
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.service.x_step.ui.theme.FontBlue
import com.service.x_step.ui.theme.backGradient
import com.service.x_step.ui.theme.scafColor
import org.intellij.lang.annotations.JdkConstants.BoxLayoutAxis

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController){

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var name by remember { mutableStateOf("User") }
    var email by remember { mutableStateOf("Email") }
    var savedmobile by remember { mutableStateOf("") }
    var inputmobile by remember { mutableStateOf("") }
    var inputupiId by remember { mutableStateOf("") }
    var savedupiId by remember { mutableStateOf("") }

     LaunchedEffect (uid) {
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("user").document(uid)
                .addSnapshotListener { snapshot, _ ->
                    snapshot?.let { document ->
                        name = document.getString("name") ?: "User"
                        email = document.getString("email") ?: "Email"
                        savedmobile = document.getString("mobile") ?: ""
                        savedupiId = document.getString("upiId") ?: ""
                    }
                }
            /*FirebaseFirestore.getInstance().collection("user").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    name = document.getString("name") ?: "User"
                }
                .addOnSuccessListener { document->
                    email = document.getString("email") ?: "Email"
                }
                .addOnSuccessListener { document->
                    savedmobile = document.getString("mobile") ?: ""
                }
                .addOnSuccessListener { document->
                    upiId = document.getString("upiId") ?: ""
                }*/
        }
    }



    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = scafColor
                ),
                title = { Text("Profile") },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("triplist") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Trip List",
                            modifier = Modifier.size(20.dp),
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

            Spacer( modifier = Modifier.padding(40.dp) )

            Card (
                modifier = Modifier
                    .padding(0.dp, 50.dp)
                    .clickable {

                    },
                colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp),
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.padding(30.dp))

                    Column {

                        Spacer(modifier = Modifier.padding(10.dp))

                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                    Spacer(modifier = Modifier.padding(15.dp))

                }
            }


            if ( savedmobile == "" ){
                enterTextField(
                    value = inputmobile,
                    onValueChange = { inputmobile = it },
                    label = "Enter your Mobile No.",
                    field = "mobile",
                    onSuccess = { updated ->
                        savedmobile = updated
                    }
                )
            }
            else{
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Phone :" )
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(savedmobile)
                    }
                }
                }

            Spacer(modifier = Modifier.padding(15.dp))

            if ( savedupiId == "" ){
                enterTextField(
                    value = inputupiId,
                    onValueChange = { inputupiId = it },
                    label = "Enter your UPI ID",
                    field = "upiId",
                    onSuccess = { updated ->
                        savedupiId = updated
                    }
                )
            }
            else{
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("UPI ID :" )
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(savedupiId)
                    }
                }
            }

            Spacer(modifier = Modifier.padding(30.dp))

            Button(
                onClick = { navController.navigate("forgot") }
            ) {
                Text(
                    text = "Reset Password"
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Button(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login"){
                    popUpTo("home") {inclusive = true}
                }
            }) {
                Text("Log Out")
            }
        }
    }
}