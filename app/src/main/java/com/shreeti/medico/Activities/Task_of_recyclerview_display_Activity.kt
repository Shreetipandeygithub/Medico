package com.shreeti.medico.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.FirebaseDatabase
import com.shreeti.medico.Model.TaskManagementModel
import com.shreeti.medico.R
import java.time.Duration

class Task_of_recyclerview_display_Activity : AppCompatActivity() {
    private lateinit var tvDrugId:TextView
    private lateinit var tvDrugName:TextView
    private lateinit var tvDrugDescription:TextView
    private lateinit var tvDrugSideEffect:TextView
    private lateinit var tvDrugPrevention:TextView
    private lateinit var tvDrugTreatment:TextView

    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button
    private lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_of_recyclerview_display)

        toolbar=findViewById(R.id.toolbar_Fetching)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setOnClickListener{
            onBackPressed()
        }


        btnUpdate=findViewById(R.id.btnUpdate)
        btnDelete=findViewById(R.id.btnDelete)

        initView()
        setValueToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("drugId").toString(),
                intent.getStringExtra("drugName").toString(),
                intent.getStringExtra("drugDescription").toString(),
                intent.getStringExtra("drugSideEffect").toString(),
                intent.getStringExtra("drugPrevention").toString(),
                intent.getStringExtra("drugTreatment").toString()



                )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("drugId").toString()
            )
        }
    }

    private fun initView(){
        tvDrugId=findViewById(R.id.tvDrugId)
        tvDrugName=findViewById(R.id.tvDrugName)
        tvDrugDescription=findViewById(R.id.tvDrugDescription)
        tvDrugSideEffect=findViewById(R.id.tvDrugSideEffect)
        tvDrugPrevention=findViewById(R.id.tvDrugPrevention)
        tvDrugTreatment=findViewById(R.id.tvDrugTreatments)

    }

    private fun setValueToView(){
        tvDrugId.text=intent.getStringExtra("drugId")
        tvDrugName.text=intent.getStringExtra("drugName")
        tvDrugDescription.text=intent.getStringExtra("drugDescription")
        tvDrugSideEffect.text=intent.getStringExtra("drugSideEffect")
        tvDrugPrevention.text=intent.getStringExtra("drugPrevention")
        tvDrugTreatment.text=intent.getStringExtra("drugTreatment")


    }

    private fun openUpdateDialog(
        drugId:String,
        drugName:String,
        drugDescription:String,
        drugSideEffects:String,
        drugPrevention:String,
        drugTreatment:String
    ){
        val mDialog=AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView=inflater.inflate(R.layout.update_dialog_box,null)

        mDialog.setView(mDialogView)

        val etDrugName=mDialogView.findViewById<EditText>(R.id.etDrugName)
        val etDrugDescription=mDialogView.findViewById<EditText>(R.id.etDrugDescription)
        val etDrugSideEffects=mDialogView.findViewById<EditText>(R.id.etDrugSideEffect)
        val etDrugPrevention=mDialogView.findViewById<EditText>(R.id.etDrugPrevention)
        val etDrugTreatment=mDialogView.findViewById<EditText>(R.id.etDrugTreatment)

        val btnUpdateData=mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etDrugName.setText(intent.getStringExtra("drugName").toString())
        etDrugDescription.setText(intent.getStringExtra("drugDescription").toString())
        etDrugSideEffects.setText(intent.getStringExtra("drugSideEffect").toString())
        etDrugPrevention.setText(intent.getStringExtra("drugPrevention").toString())
        etDrugTreatment.setText(intent.getStringExtra("drugTreatment").toString())


        mDialog.setTitle("Updating $etDrugName Record")
        val alertDialog=mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{

            updateTaskData(

                //these are the edit text view that has to be updated on the fetch activity text views
                drugId,
                etDrugName.text.toString(),
                etDrugDescription.text.toString(),
                etDrugSideEffects.text.toString(),
                etDrugPrevention.text.toString(),
                etDrugTreatment.text.toString()
            )

            Toast.makeText(applicationContext,"Medico Data Updated",Toast.LENGTH_SHORT).show()


            //we are setting updated data to our textViews

            tvDrugName.text=etDrugName.text.toString()
            tvDrugDescription.text=etDrugDescription.text.toString()
            tvDrugSideEffect.text=etDrugSideEffects.text.toString()
            tvDrugPrevention.text=etDrugPrevention.text.toString()
            tvDrugTreatment.text=etDrugTreatment.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateTaskData(
        id:String,
        name:String,
        description:String,
        sideEffect:String,
        prevention:String,
        treatment:String

    ){
        val dbRef=FirebaseDatabase.getInstance().getReference("Task Management").child(id)
        val taskInfo=TaskManagementModel(id,name,description,sideEffect,prevention,treatment)

        dbRef.setValue(taskInfo)

    }

    private fun deleteRecord(
        id: String
    ){

        val dbRef=FirebaseDatabase.getInstance().getReference("Task Management").child(id)
        val mTask=dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Successfully deleted",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->

            Toast.makeText(this,"Deleting ${error.message}",Toast.LENGTH_SHORT).show()
        }

    }
}