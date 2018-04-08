package com.projetos.amanda.proconanalytics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.projetos.amanda.proconanalytics.models.Posto
import com.projetos.amanda.proconanalytics.MyAdapter
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        rvTopId.setHasFixedSize(true)

        val postos:ArrayList<Posto> = ArrayList<Posto>()
        for (i in 0..10){
            postos.add(Posto("Nome ${i}", "Preço ${i}", "Posição ${i}"))
        }

        val myAdapter: RecyclerView.Adapter<*> = MyAdapter(this@TopActivity, postos){
            Toast.makeText(this@TopActivity, it.toString(), Toast.LENGTH_SHORT).show()
        }

        rvTopId.adapter = myAdapter
    }
}
