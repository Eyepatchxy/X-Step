package com.service.x_step

data class Trip (
    val id : String = "",
    val userId : String = "",
    val posttime : Long = 0L,
    val to : String = "",
    val from : String = "",
    val date : String = "",
    val starttime : String = "",
    val arrivaltime : String = "",
    val itemSize : String = "",
    val description : String = "",
    val triptime : Long = 0L,
    val upiId : String = ""
)