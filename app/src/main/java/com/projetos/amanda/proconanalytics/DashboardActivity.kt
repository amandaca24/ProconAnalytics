package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import com.projetos.amanda.proconanalytics.adapters.ImgAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    var userImageFromFB : ArrayList<String> = ArrayList<String>()

    var firebaseDatabase: FirebaseDatabase? = null
    var myRef : DatabaseReference? = null


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        }

        return true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase!!.reference


        imgRV.setHasFixedSize(true)
        imgRV.layoutManager = LinearLayoutManager(this)

        getImgFB()

    }

    fun getImgFB(){

        val newRef = firebaseDatabase!!.getReference("Images")

        newRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot?) {
                userImageFromFB.clear()

                for(snapshot in p0!!.children){
                    val imgFB = snapshot.child("img").value.toString()

                    userImageFromFB.add(imgFB)
                }

                val imgAdapter = ImgAdapter(this@DashboardActivity, userImageFromFB)

                imgRV.adapter = imgAdapter

            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })





    }
}
