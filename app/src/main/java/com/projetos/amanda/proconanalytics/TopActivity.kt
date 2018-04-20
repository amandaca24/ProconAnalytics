package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.*
import com.projetos.amanda.proconanalytics.adapters.MyAdapter
import com.projetos.amanda.proconanalytics.models.FbData
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mDataReference: DatabaseReference? = null
    private var postos: ArrayList<FbData>? = null

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

       rvTopId.setHasFixedSize(true)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDataReference = mFirebaseDatabase!!.getReference()

        //val postos:ArrayList<FbData> = ArrayList<FbData>()
        //for (i in 0..10){
            //postos.add(Posto("Nome ${i}", "Preço ${i}", "Posição ${i}"))
        //}

        val myAdapter: RecyclerView.Adapter<*> = MyAdapter(this@TopActivity, postos) {
            getDataFB()
        }

        rvTopId.adapter = myAdapter

    }

    private fun getDataFB(){

        var dataReference = FirebaseDatabase.getInstance().getReference("Pesquisas")

        val query = dataReference!!.limitToLast(10).orderByChild("valor")

        query.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                postos!!.clear()

                for(snapshot in p0!!.children){
                    val bairro = snapshot.child("bairro").toString()
                    val bandeira = snapshot.child("bandeira").toString()
                    val nome = snapshot.child("nome").toString()
                    val produto = snapshot.child("combustivel").toString()
                    val valor = snapshot.child("valor").toString()


                    postos!!.add(FbData(bairro, bandeira, nome, produto, valor))

                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}
