package com.service.x_step

data class Request (
    val reqId : String = "",
    val tripId : String = "",
    val rqTime : Long = 0L,
    val rqUser : String = "",
    val item : String = "",
    val itemDesc : String = "",
    val itemSize : String = "",
    val pickupLoc : String = "",
    val cost : String = "",
    val status : Boolean? = null
)