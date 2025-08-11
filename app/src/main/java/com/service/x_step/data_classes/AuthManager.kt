package com.service.x_step

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


object AuthManager {

    fun signUpWithEmail(
        name: String,
        email: String,
        password: String,
        upiId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = auth.currentUser?.uid

                val userData = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "upiId" to upiId,
                    "joinedAt" to System.currentTimeMillis()
                )

                if (userId != null) {
                    db.collection("user").document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e->
                            onFailure(e)
                        }
                }
                else{
                    onFailure(Exception("User Id not found"))
                }
            }
            .addOnFailureListener{ exception ->
                onFailure(exception)
            }
    }

    fun logInWithEmail(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    onSuccess()
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }
}