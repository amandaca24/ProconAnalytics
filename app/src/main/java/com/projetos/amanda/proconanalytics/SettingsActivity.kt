package com.projetos.amanda.proconanalytics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.projetos.amanda.proconanalytics.constants.Constants
import com.projetos.amanda.proconanalytics.models.UserFB
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var content: View
    private lateinit var etChoice: TextView
    private lateinit var btnSave: Button
    private lateinit var btnVoltar: Button
    private lateinit var confNome:TextView
    private lateinit var confEmail:TextView
    private lateinit var confSenha:TextView
    private var users:ArrayList<UserFB> = ArrayList<UserFB>()

    private var databaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().getReference("disconnectmessage").onDisconnect().setValue("Disconectado!")

        content = findViewById(R.id.idSettings)
        btnSave = findViewById(R.id.btnSave)
        btnVoltar = findViewById(R.id.btnVoltar)
        etChoice = findViewById(R.id.etChoice)
        confEmail = findViewById(R.id.confEmail)
        confNome = findViewById(R.id.confNome)
        confSenha = findViewById(R.id.confSenha)

        val user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().reference!!


        btnSave.setOnClickListener {updateUser(user)

        }

        btnVoltar.setOnClickListener { saveUserConfig(Constants.SP_PREFERENCES, etChoice.text.toString())
            Snackbar.make(content, "Preferências salvas", Snackbar.LENGTH_LONG).show() }


        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this@SettingsActivity, R.array.top10Array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTop.adapter = adapter

        spTop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    etChoice.text = parent.getItemAtPosition(position).toString()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Snackbar.make(content, "Selecione um item", Snackbar.LENGTH_LONG).show()
            }
        }

        getFBUser()
    }

    private fun saveUserConfig(key:String, value: String){
        val pref = this.getSharedPreferences("com.projetos.amanda.proconanalytics.settings", android.content.Context.MODE_PRIVATE)

        val editor = pref.edit()

        editor.putString(key, value)

        editor.apply()
    }

    /*private fun voltarTela(){
        val intent = Intent(this@SettingsActivity, NavActivity::class.java)
        startActivity(intent)
    }*/

   private fun getFBUser(){

       val user = FirebaseAuth.getInstance().currentUser!!.uid
       databaseReference = FirebaseDatabase.getInstance().reference!!.child("Users").child(user)

        databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                for(snapshot in p0.children) {
                    val nome = snapshot.child("Name").value.toString()
                    val email = snapshot.child("Email").value.toString()

                    users.add(UserFB(nome, email))

                    confNome.text = nome
                    confEmail.text = email

                }


                }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    private fun updateUser(user: FirebaseUser?){

        if(user!= null){
            val email = user.updateEmail(confEmail.text.toString())
            user.updatePassword(confSenha.text.toString())

            val currentUserDb = databaseReference!!.child(user.uid)
            currentUserDb.child("Email").setValue(email)
            currentUserDb.child("Name").setValue(confNome.text.toString())
        }
    }

}

