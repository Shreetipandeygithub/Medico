package com.shreeti.medico.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shreeti.medico.R
import com.shreeti.medico.Model.TaskManagementModel

@Suppress("DEPRECATION")
class InsertionActivity : AppCompatActivity() {

    private lateinit var title: AppCompatEditText
    private lateinit var duration: AppCompatEditText
    private lateinit var description: AppCompatEditText
    private lateinit var btnSave:Button
    private lateinit var toolbarinsertion: Toolbar
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        toolbarinsertion=findViewById(R.id.toolbar_Insertion)

        setSupportActionBar(toolbarinsertion)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarinsertion.setOnClickListener {
            onBackPressed()
        }


        title=findViewById(R.id.TaskTitle)
        duration=findViewById(R.id.TaskDuration)
        description=findViewById(R.id.TaskDescription)
        btnSave=findViewById(R.id.btnSave)

        dbRef= FirebaseDatabase.getInstance().getReference("Task Management")

        btnSave.setOnClickListener{
            saveTaskData()
        }
    }

    private fun saveTaskData(){

        //getting values from insertion activity

        val Title=title.text.toString()
        val Duration=duration.text.toString()
        val Description=description.text.toString()

        if (Title.isEmpty()){
            title.error="Please Enter The Title"
        }
        if (Description.isEmpty()){
            description.error="Please Enter the Description"
        }
        if (Duration.isEmpty()){
            duration.error="Please Enter the Description"
        }

        //to differentiate same types of data eg Task1 and Task1
        val taskId=dbRef.push().key!!

        val taskManagement= TaskManagementModel(taskId,Title,Duration,Description)    //used from saveTaskData
        dbRef.child(taskId).setValue(taskManagement)
            .addOnCompleteListener{
                Toast.makeText(this,"Successfully Inserted",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{ error->
                Toast.makeText(this,"Error ${error.message}",Toast.LENGTH_SHORT).show()
            }


    }
}