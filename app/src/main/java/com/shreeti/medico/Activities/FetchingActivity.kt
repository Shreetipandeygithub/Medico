package com.shreeti.medico.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.Intents
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.shreeti.medico.Adapter.TaskManagementAdapter
import com.shreeti.medico.Model.TaskManagementModel
import com.shreeti.medico.R

class FetchingActivity : AppCompatActivity() {

    private lateinit var rvTaskRecyclerView:RecyclerView
    private lateinit var tvLoadingData:TextView

    private lateinit var dbRef:DatabaseReference


    //
    private lateinit var mList: ArrayList<TaskManagementModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        tvLoadingData=findViewById(R.id.tvLoadingData)


        rvTaskRecyclerView=findViewById(R.id.rvTaskRecyclerView)
        rvTaskRecyclerView.layoutManager=LinearLayoutManager(this)
        rvTaskRecyclerView.setHasFixedSize(true)

        mList= arrayListOf<TaskManagementModel>()

        getTaskManagementData()
    }

    private fun getTaskManagementData(){
        rvTaskRecyclerView.visibility=View.GONE
        tvLoadingData.visibility=View.VISIBLE

        dbRef=FirebaseDatabase.getInstance().getReference("Task Management")


        // write dbRef.addValueEventListener(object :ValueEventListener
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               mList.clear()
                if (snapshot.exists()){
                    for (taskSnap in snapshot.children){
                        val takData=taskSnap.getValue(TaskManagementModel::class.java)
                        mList.add(takData!!)
                    }

                    val mAdapter=TaskManagementAdapter(mList)
                    rvTaskRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object:TaskManagementAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent=Intent(this@FetchingActivity,Task_of_recyclerview_display_Activity::class.java)

                            //put Extra
                            intent.putExtra("taskId",mList[position].taskId)
                            intent.putExtra("taskTitle",mList[position].taskTitle)
                            intent.putExtra("taskDuration",mList[position].taskDuration)
                            intent.putExtra("taskDescription",mList[position].taskDescription)
                            startActivity(intent)

                        }

                    } )

                    rvTaskRecyclerView.visibility=View.VISIBLE
                    tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}