package com.projetos.amanda.proconanalytics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.projetos.amanda.proconanalytics.models.SettingsModel
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var itemTV:TextView
    private lateinit var itemET:EditText
    private lateinit var content: View
    private lateinit var etChoice: EditText
    private lateinit var btSave: Button

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        content = findViewById(R.id.idSettings)
        etChoice = findViewById(R.id.etChoice)
        btSave = findViewById(R.id.btnSave)

        btSave.setOnClickListener { getUserConfig() }

        mAuth = FirebaseAuth.getInstance()



        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this@SettingsActivity, R.array.top10Array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTop.adapter = adapter

        spTop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                itemTV.text = itemET.text.toString()
                itemET.setText("")
                if (parent != null) {
                    etChoice.setText(parent.getItemAtPosition(position).toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Snackbar.make(content, "Selecione um item", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun getUserConfig(){
        val userId = mAuth!!.currentUser!!.uid

        val setTop10 = SettingsModel()
        setTop10.setTop = etChoice.text.toString()
        setTop10.userId = userId

        setTop10.save()

    }
}
