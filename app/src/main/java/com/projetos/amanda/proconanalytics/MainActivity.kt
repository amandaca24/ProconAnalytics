package com.projetos.amanda.proconanalytics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements
//    private var etEmail: TextInputEditText? = null
//    private var etPassword: TextInputEditText? = null
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private var btnLogin: Button? = null
    private var btnCadatrar: Button? = null
    private var idRecupera: Button? = null
    private var checkConecta: CheckBox? = null

    private var auth: FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLogin()

    }

    private fun initLogin(){

        etEmail = findViewById<EditText>(R.id.etEmail)
        etPassword = findViewById<EditText>(R.id.etPassword)
        btnLogin = findViewById<Button>(R.id.btnLog)
        btnCadatrar = findViewById<Button>(R.id.btnCadastro)
        idRecupera = findViewById<Button>(R.id.idRecuperaSenha)
        checkConecta = findViewById<CheckBox>(R.id.btnCheckBox)

        auth = FirebaseAuth.getInstance()

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
                            Toast.makeText(this@MainActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@MainActivity, NavActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }




}
