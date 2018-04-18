package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.projetos.amanda.proconanalytics.constants.Constants
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var content: View
    private lateinit var etChoice: EditText
    private lateinit var btSave: Button
    private lateinit var btVoltar: Button

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        content = findViewById(R.id.idSettings)
        etChoice = findViewById(R.id.etChoice)
        btSave = findViewById(R.id.btnSave)
        btVoltar = findViewById(R.id.btnVoltar)

        btSave.setOnClickListener { saveUserConfig(Constants.SP_PREFERENCES, etChoice.text.toString())
            Snackbar.make(content, "Preferências salvas", Snackbar.LENGTH_LONG).show()

        }

        btVoltar.setOnClickListener { voltarTela() }

        mAuth = FirebaseAuth.getInstance()



        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this@SettingsActivity, R.array.top10Array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTop.adapter = adapter

        spTop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    etChoice.setText(parent.getItemAtPosition(position).toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Snackbar.make(content, "Selecione um item", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun saveUserConfig(key:String, value: String){
        val pref = this.getSharedPreferences("com.projetos.amanda.proconanalytics.settings", android.content.Context.MODE_PRIVATE)

        val editor = pref.edit()

        editor.putString(key, value)

        editor.apply()
    }

    private fun voltarTela(){
        val intent = Intent(this@SettingsActivity, NavActivity::class.java)
        startActivity(intent)
    }

    }

