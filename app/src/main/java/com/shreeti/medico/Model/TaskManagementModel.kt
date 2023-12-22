package com.shreeti.medico.Model

import android.app.ActivityManager.TaskDescription

data class TaskManagementModel(
    var drugId:String?=null,
    var drugName:String?=null,
    var drugDescription:String?=null,
    var drugSideEffects:String?=null,
    var drugPrevention:String?=null,
    var drugTreatment:String?=null

) {
}