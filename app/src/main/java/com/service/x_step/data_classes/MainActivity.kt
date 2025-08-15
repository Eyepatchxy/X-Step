package com.service.x_step

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.service.x_step.ui.screens.EditProfile
import com.service.x_step.ui.screens.ForgotPasswordScreen
import com.service.x_step.ui.screens.HistoryScreen
import com.service.x_step.ui.screens.LoginScreen
import com.service.x_step.ui.screens.PostATrip
import com.service.x_step.ui.screens.PostRequest
import com.service.x_step.ui.screens.Profile
import com.service.x_step.ui.screens.RequestDetails
import com.service.x_step.ui.screens.RequestHistory
import com.service.x_step.ui.screens.RequestList
import com.service.x_step.ui.screens.SignUpScreen
import com.service.x_step.ui.screens.TripDetails
import com.service.x_step.ui.screens.TripHistory
import com.service.x_step.ui.screens.TripListScreen
import com.service.x_step.ui.screens.YourActivity
import com.service.x_step.ui.theme.XStepTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XStepTheme {
                FirebaseApp.initializeApp(this)

                val navController = rememberNavController()
                val user = FirebaseAuth.getInstance().currentUser
                val startdestination = if( user != null ) "triplist" else "login"

                NavHost(navController, startDestination = startdestination){
                    composable("login") { LoginScreen(navController) }
                    composable("signup") { SignUpScreen(navController) }
                    composable("forgot") { ForgotPasswordScreen(navController) }
                    composable("triplist") { TripListScreen(navController) }
                    composable("postatrip") { PostATrip(navController) }
                    composable("profile") { Profile(navController) }
                    composable("editprofile") { EditProfile(navController) }
                    composable("history") { HistoryScreen(navController) }
                    composable("youractivity"){ YourActivity(navController) }
//                    composable("yourpostedtrips") { TripHistory(navController) }
//                    composable("yourpostedrequests") { RequestHistory(navController) }
                    composable("tripDetail/{tripId}") { backStackEntry ->
                        val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
                        TripDetails( tripId = tripId, navController = navController)
                    }
                    composable("postrequest/{tripId}") { backStackEntry ->
                        val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
                        PostRequest( tripId = tripId, navController = navController)
                    }
                    composable("requestlist/{tripId}") { backStackEntry ->
                        val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
                        RequestList( tripId = tripId, navController = navController)
                    }
                    composable("requestDetail/{reqId}") { backStackEntry ->
                        val reqId = backStackEntry.arguments?.getString("reqId") ?: ""
                        RequestDetails( reqId = reqId , navController = navController)
                    }


                }
            }
        }
    }
}

