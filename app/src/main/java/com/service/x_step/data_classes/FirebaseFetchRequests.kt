package com.service.x_step.data_classes

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.service.x_step.Request
import com.service.x_step.Trip

class FirebaseFetchRequests {

    fun getUser(
        uid: String,
        onResult: (User?) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("user").document(uid)
            .get()
            .addOnSuccessListener { user->
                if (user.exists()){
                    val details = User(
                        uid = user.id,
                        name = user.getString("name") ?: "User",
                        email = user.getString("email") ?: "Email",
                        mobile = user.getString("mobile") ?: "",
                        fcmToken = user.getString("fcmToken") ?: "",
                        upiId = user.getString("upiId") ?: ""
                    )
                    onResult(details)
                    Log.d("Firebase", "User Fetched successfully.")
                }
                else{
                    Log.d("Firebase", "User doesn't exist.")
                }
            }
            .addOnFailureListener{ e ->
                Log.e("Firebase", "User fetch failed.", e)
            }
    }

    fun fetchtrips(
        list : (List<Trip>) -> Unit
    ){
        val now = System.currentTimeMillis()

        FirebaseFirestore.getInstance().collection("trip")
            .whereGreaterThanOrEqualTo("triptime", now)
            .get()
            .addOnSuccessListener { result ->
                val trips = result.map { doc ->
                    Trip(
                        id = doc.id,
                        userId = doc.getString("userId") ?: "",
                        posttime = doc.getLong("postTime") ?: 0L,
                        from = doc.getString("startLoc") ?: "",
                        to = doc.getString("endLoc") ?: "",
                        date = doc.getString("tripDate") ?: "",
                        starttime = doc.getString("tripStartTime") ?: "",
                        arrivaltime = doc.getString("tripArrivalTime") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        description = doc.getString("description") ?: "",
                        triptime = doc.getLong("triptime") ?: 0L
                    )
                }
                list(trips)
                Log.d("FIREBASE", "Fetched: docs")
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE", "Error fetching", e)
            }
    }

    fun fetchtripbyid(
        tripId : String,
        onResult : (Trip?) -> Unit
    ){
        FirebaseFirestore.getInstance().collection("trip").document(tripId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val trip = Trip(
                        id = doc.id,
                        userId = doc.getString("userId") ?: "",
                        posttime = doc.getLong("postTime") ?: 0L,
                        from = doc.getString("startLoc") ?: "",
                        to = doc.getString("endLoc") ?: "",
                        date = doc.getString("tripDate") ?: "",
                        starttime = doc.getString("tripStartTime") ?: "",
                        arrivaltime = doc.getString("tripArrivalTime") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        description = doc.getString("description") ?: "",
                        triptime = doc.getLong("triptime") ?: 0L
                    )
                    onResult(trip)
                }
                else{
                    onResult(null)
                }
            }
            .addOnFailureListener{
                onResult(null)
            }
    }

    fun fetchtripbyuser(
        uid : String,
        list : (List<Trip>) -> Unit
    ){
        FirebaseFirestore.getInstance().collection("trip")
            .whereEqualTo("userId", uid)
            .get()
            .addOnSuccessListener { result ->
                val trips = result.map { doc ->
                    Trip(
                        id = doc.id,
                        userId = doc.getString("userId") ?: "",
                        posttime = doc.getLong("postTime") ?: 0L,
                        from = doc.getString("startLoc") ?: "",
                        to = doc.getString("endLoc") ?: "",
                        date = doc.getString("tripDate") ?: "",
                        starttime = doc.getString("tripStartTime") ?: "",
                        arrivaltime = doc.getString("tripArrivalTime") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        description = doc.getString("description") ?: "",
                        triptime = doc.getLong("triptime") ?: 0L
                    )
                }
                list(trips)
            }
            .addOnFailureListener{
                list(emptyList())
            }
    }

    fun fetchrequests(
        list: (List<Request>) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("request")
            .get()
            .addOnSuccessListener { result ->
                val request = result.map { doc ->
                    Request(
                        reqId = doc.getString("reqId") ?: "",
                        tripId = doc.getString("tripId") ?: "",
                        rqTime = doc.getLong("rqTime") ?: 0L,
                        rqUser = doc.getString("rqUser") ?: "",
                        item = doc.getString("item") ?: "",
                        itemDesc = doc.getString("itemDesc") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        pickupLoc = doc.getString("pickupLoc") ?: "",
                        cost = doc.getString("cost") ?: "",
                        status = doc.getBoolean("status")
                    )
                }
                list(request)
            }
            .addOnFailureListener {
                list(emptyList())
            }
    }

    fun fetchrequestbyid(
        reqId : String,
        onResult: (Request?) -> Unit
    ){
        FirebaseFirestore.getInstance().collection("request").document(reqId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()){
                    val request = Request(
                        reqId = doc.id,
                        tripId = doc.getString("tripId") ?: "",
                        rqTime = doc.getLong("rqTime") ?: 0L,
                        rqUser = doc.getString("rqUser") ?: "",
                        item = doc.getString("item") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        itemDesc = doc.getString("itemDesc") ?: "",
                        pickupLoc = doc.getString("pickupLoc") ?: "",
                        cost = doc.getString("cost") ?: "",
                        status = doc.getBoolean("status")
                    )
                    onResult(request)
                }
                else{
                    onResult(null)
                }
            }
            .addOnFailureListener{
                onResult(null)
            }
    }

    fun fetchrequestbytripId(
        tripId: String,
        list: (List<Request>) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("request")
            .whereEqualTo("tripId", tripId)
            .get()
            .addOnSuccessListener { result ->
                val request = result.map { doc ->
                    Request(
                        reqId = doc.getString("reqId") ?: "",
                        tripId = doc.getString("tripId") ?: "",
                        rqTime = doc.getLong("rqTime") ?: 0L,
                        rqUser = doc.getString("rqUser") ?: "",
                        item = doc.getString("item") ?: "",
                        itemDesc = doc.getString("itemDesc") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        pickupLoc = doc.getString("pickupLoc") ?: "",
                        cost = doc.getString("cost") ?: "",
                        status = doc.getBoolean("status")
                    )
                }
                list(request)
            }
            .addOnFailureListener {
                list(emptyList())
            }
    }

    fun fetchrequestbyuser(
        uid : String,
        list : (List<Request>) -> Unit
    ){
        FirebaseFirestore.getInstance().collection("request")
            .whereEqualTo("rqUser", uid)
            .get()
            .addOnSuccessListener { result ->
                val requests = result.map { doc ->
                    Request(
                        reqId = doc.getString("reqId") ?: "",
                        tripId = doc.getString("tripId") ?: "",
                        rqTime = doc.getLong("rqTime") ?: 0L,
                        rqUser = doc.getString("rqUser") ?: "",
                        item = doc.getString("item") ?: "",
                        itemDesc = doc.getString("itemDesc") ?: "",
                        itemSize = doc.getString("itemSize") ?: "",
                        pickupLoc = doc.getString("pickupLoc") ?: "",
                        cost = doc.getString("cost") ?: "",
                        status = doc.getBoolean("status")
                    )
                }
                list(requests)
            }
            .addOnFailureListener{
                list(emptyList())
            }
    }
}