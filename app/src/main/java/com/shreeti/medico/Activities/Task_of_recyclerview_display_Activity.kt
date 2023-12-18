package com.shreeti.medico.Activities

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

@Suppress("DEPRECATION")
class Task_of_recyclerview_display_Activity : AppCompatActivity() {
    private lateinit var tvTaskDescription:TextView
    private lateinit var tvTaskDuration:TextView
    private lateinit var tvTaskTitle:TextView
    private lateinit var tvTaskId:TextView
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
                intent.getStringExtra("taskId").toString(),
                intent.getStringExtra("taskTitle").toString()

            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("taskId").toString()
            )
        }
    }

    private fun initView(){
        tvTaskId=findViewById(R.id.tvTaskId)
        tvTaskTitle=findViewById(R.id.tvTaskTitle)
        tvTaskDuration=findViewById(R.id.tvTaskDuration)
        tvTaskDescription=findViewById(R.id.tvTaskDescription)
    }

    private fun setValueToView(){
        tvTaskId.text=intent.getStringExtra("taskId")
        tvTaskTitle.text=intent.getStringExtra("taskTitle")
        tvTaskDuration.text=intent.getStringExtra("taskDuration")
        tvTaskDescription.text=intent.getStringExtra("taskDescription")

    }

    private fun openUpdateDialog(
        taskId:String,
        taskTitle:String){
        val mDialog=AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView=inflater.inflate(R.layout.update_dialog_box,null)

        mDialog.setView(mDialogView)

        val etTaskTitle=mDialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etTaskDuration=mDialogView.findViewById<EditText>(R.id.etTaskDuration)
        val etTaskDescription=mDialogView.findViewById<EditText>(R.id.etTaskDescription)
        val btnUpdateData=mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etTaskTitle.setText(intent.getStringExtra("taskTitle").toString())
        etTaskDuration.setText(intent.getStringExtra("taskDuration").toString())
        etTaskTitle.setText(intent.getStringExtra("taskDescription").toString())

        mDialog.setTitle("Updating $taskTitle Record")
        val alertDialog=mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{

            updateTaskData(

                //these are the edit text view that has to be updated on the fetch activity text views
                taskId,
                etTaskTitle.text.toString(),
                etTaskDuration.text.toString(),
                etTaskDescription.text.toString()
            )

            Toast.makeText(applicationContext,"Task Management Data Updated",Toast.LENGTH_SHORT).show()


            //we are setting updated data to our textViews
            tvTaskTitle.text=etTaskTitle.text.toString()
            tvTaskDuration.text=etTaskDuration.text.toString()
            tvTaskDescription.text=etTaskDescription.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateTaskData(
        id:String,
        title:String,
        duration: String,
        description:String
    ){
        val dbRef=FirebaseDatabase.getInstance().getReference("Task Management").child(id)
        val taskInfo=TaskManagementModel(id,title,duration,description)

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