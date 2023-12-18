package com.shreeti.medico.Model

import android.app.ActivityManager.TaskDescription

data class TaskManagementModel(
    var taskId:String?=null,
    var taskTitle:String?=null,
    var taskDuration:String?=null,
    var taskDescription:String?=null
) {
}