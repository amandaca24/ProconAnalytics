package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth




class MainActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private val PREF_NAME: String = "MainActivityPref"
    private val PREF_TOKEN: String = "Token"

    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private var btnLogin: Button? = null
    private var btnCadatrar: Button? = null
    private var idRecupera: Button? = null
    private var checkConecta: CheckBox? = null
    private lateinit var contentV: View

    private var auth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    //var pref: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val pref = this.getSharedPreferences("MainActivityPref", 0)

        initLogin()

    }

    private fun initLogin(){

        etEmail = findViewById(R.id.idEmailInput)
        etPassword = findViewById(R.id.idSenhaInput)
        btnLogin = findViewById(R.id.btnLog)
        btnCadatrar = findViewById(R.id.btnCadastro)
        idRecupera = findViewById(R.id.idRecuperaSenha)
        checkConecta = findViewById(R.id.btnCheckBox)
        contentV = findViewById(R.id.idMainAct)

        auth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {  }

        btnCadatrar!!
                .setOnClickListener { startActivity(Intent(this@MainActivity,
                        RegisterActivity::class.java)) }
        btnLogin!!.setOnClickListener { loginUser() }

        idRecupera!!.setOnClickListener{ startActivity(Intent(this@MainActivity, RecuperaActivity::class.java))}



    }

    private fun loginUser() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            auth!!.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            updateUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                            Snackbar.make(contentV, "Problemas na autenticação do usuário", Snackbar.LENGTH_LONG).show()
                        }
                    }
        } else {
            Snackbar.make(contentV, "Preencha todos os campos", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@MainActivity, NavActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }




}
