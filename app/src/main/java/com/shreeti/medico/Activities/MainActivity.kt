package com.shreeti.medico.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shreeti.medico.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMActivity: Toolbar
    private lateinit var btninsertData: Button
    private lateinit var btnfetchData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMActivity=findViewById(R.id.toolbar_MainActivity)
        val firebase:DatabaseReference= FirebaseDatabase.getInstance().getReference()
        toolbarMActivity=findViewById(R.id.toolbar_MainActivity)
        btninsertData=findViewById(R.id.InsertData)
        btnfetchData=findViewById(R.id.fetchData)

        setSupportActionBar(toolbarMActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarMActivity.setOnClickListener{
            onBackPressed()
        }

        btninsertData.setOnClickListener{
            val intent= Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
        btnfetchData.setOnClickListener{
            val intent= Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}