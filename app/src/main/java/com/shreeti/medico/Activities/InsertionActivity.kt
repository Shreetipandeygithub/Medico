package com.shreeti.medico.Activities

import android.annotation.SuppressLint
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

class InsertionActivity : AppCompatActivity() {

    private lateinit var drugName: AppCompatEditText
    private lateinit var drugDescription: AppCompatEditText
    private lateinit var drugSideEffect: AppCompatEditText
    private lateinit var drugPrevention: AppCompatEditText
    private lateinit var drugTreatment: AppCompatEditText

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


        drugName=findViewById(R.id.DrugName)
        drugDescription=findViewById(R.id.DrugDescription)
        drugSideEffect=findViewById(R.id.DrugSideEffect)
        drugPrevention=findViewById(R.id.DrugPrevention)
        drugTreatment=findViewById(R.id.DrugTreatment)

        btnSave=findViewById(R.id.btnSave)

        dbRef= FirebaseDatabase.getInstance().getReference("Task Management")

        btnSave.setOnClickListener{
            saveTaskData()
        }
    }

    private fun saveTaskData(){

        //getting values from insertion activity

        val DrugName=drugName.text.toString()
        val DrugSideEffect=drugSideEffect.text.toString()
        val DrugDescription=drugDescription.text.toString()
        val DrugPrevention=drugPrevention.text.toString()
        val DrugTreatment=drugTreatment.text.toString()

        if (DrugName.isEmpty()){
            drugName.error="Please Enter The Name"
        }
        if (DrugDescription.isEmpty()){
            drugDescription.error="Please Enter the Description"
        }
        if (DrugSideEffect.isEmpty()){
            drugSideEffect.error="Please Enter the Side Effect"
        }
        if (DrugPrevention.isEmpty()){
            drugPrevention.error="Please Enter the Prevention"
        }
        if (DrugTreatment.isEmpty()){
            drugTreatment.error="Please Enter the Treatment"
        }

        //to differentiate same types of data eg Task1 and Task1
        val DrugId=dbRef.push().key!!

        val taskManagement= TaskManagementModel(DrugId,DrugName,DrugDescription,DrugSideEffect,DrugPrevention,DrugTreatment)    //used from saveTaskData
        dbRef.child(DrugId).setValue(taskManagement)
            .addOnCompleteListener{
                Toast.makeText(this,"Successfully Inserted",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{ error->
                Toast.makeText(this,"Error ${error.message}",Toast.LENGTH_SHORT).show()
            }


    }
}