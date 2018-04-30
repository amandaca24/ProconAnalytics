package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class RecuperaActivity : AppCompatActivity() {
    private val TAG = "RecuperaActivity"
    //UI elements
    private lateinit var recEmail: TextInputEditText
    private lateinit var btnRecuperaSenha: Button
    private lateinit var content: View
    //Firebase references
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recupera)

        initRecupera()
    }

    private fun initRecupera(){
        recEmail = findViewById(R.id.recEmail)
        btnRecuperaSenha = findViewById(R.id.btnRecuperaSenha)
        content = findViewById(R.id.layRecupera)

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
                    Snackbar.make(content, message, Snackbar.LENGTH_LONG).show()
                    updateUI()
                } else {
                    Log.w(TAG, task.exception!!.message)
                    Snackbar.make(content, "Não existe usuário com esse e-mail", Snackbar.LENGTH_LONG).show()
                        }
                    }
        } else {
            Snackbar.make(content, "Digite o e-mail", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@RecuperaActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    }

