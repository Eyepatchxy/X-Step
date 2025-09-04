package com.service.x_step.data_classes

data class User (
    val uid : String = "",
    val name : String = "",
    val email : String = "" + "@sggs.ac.in",
    val fcmToken : String = "",
    val mobile : String = "",
    val upiId : String = "",
)