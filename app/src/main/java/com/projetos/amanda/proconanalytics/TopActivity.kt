package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase.*
import com.projetos.amanda.proconanalytics.adapters.MyAdapter
import com.projetos.amanda.proconanalytics.models.FbData
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    private var postos: ArrayList<FbData> = ArrayList<FbData>()

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var myRef:DatabaseReference


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
        setContentView(R.layout.activity_top)

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        //FirebaseDatabase.getInstance().getReference("disconnectmessage").onDisconnect().setValue("Disconectado!")

        rvTopId.setHasFixedSize(true)
        rvTopId.layoutManager = LinearLayoutManager(this)


        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase.reference


        getDataFB()


    }

    private fun getDataFB(){

        val dataReference = getInstance().getReference("Pesquisas")

        val query = dataReference!!.limitToLast(10).orderByChild("valor")


        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                postos.clear()

                for(snapshot in p0.children){
                    val bairro = snapshot.child("bairro").value.toString()
                    val bandeira = snapshot.child("bandeira").value.toString()
                    val nome = snapshot.child("nome").value.toString()
                    val produto = snapshot.child("combustivel").value.toString()
                    val valor = snapshot.child("valor").value.toString()

                    postos.add(FbData(bairro, bandeira, nome, produto, valor))

                }

                val myAdapter = MyAdapter(this@TopActivity, postos) {

                }

                rvTopId.adapter = myAdapter


            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

}
