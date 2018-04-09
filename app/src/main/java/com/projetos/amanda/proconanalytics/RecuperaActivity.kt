package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RecuperaActivity : AppCompatActivity() {
    private val TAG = "RecuperaActivity"
    //UI elements
    private lateinit var recEmail: TextInputEditText
    private lateinit var btnRecuperaSenha: Button
    //Firebase references
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recupera)
    }

    private fun initRecupera(){
        recEmail = findViewById(R.id.recEmail)
        btnRecuperaSenha = findViewById(R.id.btnRecuperaSenha)

        auth = FirebaseAuth.getInstance()

        btnRecuperaSenha.setOnClickListener { enviaRecuperarSenha() }

    }

    private fun enviaRecuperarSenha(){
        val email = recEmail.text.toString()

        if (!TextUtils.isEmpty(email)) {
            auth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val message = "Email enviado."
                    Log.d(TAG, message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    updateUI()
                } else {
                    Log.w(TAG, task.exception!!.message)
                    Toast.makeText(this, "Não existe usuário com esse e-mail.", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Digite o e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@RecuperaActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    }

