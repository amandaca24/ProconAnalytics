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
import android.widget.CheckBox
import android.widget.ProgressBar
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import com.projetos.amanda.proconanalytics.constants.Constants

//import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val tagErro = "LoginActivity"

    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private var btnLogin: Button? = null
    private var btnCadatrar: Button? = null
    private var idRecupera: Button? = null
    private lateinit var checkConecta: CheckBox
    private lateinit var contentV: View
    private lateinit var mProgressBar: ProgressBar


    private var auth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().getReference("disconnectmessage").onDisconnect().setValue("Disconectado!")


        getUserSP(Constants.SP_TOKEN_USER)

    }

    private fun initLogin(){



        etEmail = findViewById(R.id.idEmailInputTI)
        etPassword = findViewById(R.id.idSenhaInputTI)
        btnLogin = findViewById(R.id.btnLog)
        btnCadatrar = findViewById(R.id.btnCadastro)
        idRecupera = findViewById(R.id.idRecuperaSenha)
        contentV = findViewById(R.id.idMainAct)
        checkConecta = findViewById(R.id.btnCheckBox)
        mProgressBar = findViewById(R.id.pgBar)

        auth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {  }

        btnCadatrar!!
                .setOnClickListener { startActivity(Intent(this@MainActivity,
                        RegisterActivity::class.java)) }
        btnLogin!!.setOnClickListener { loginUser()
            mProgressBar.progress = 20
            mProgressBar.secondaryProgress = 50
        }


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
                            Log.d(tagErro, "signInWithEmail:success")
                            val userId = auth!!.currentUser!!.uid
                            if(checkConecta.isChecked){
                                saveUserSP(Constants.SP_TOKEN_USER, value = userId)
                            }

                            updateUI()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(tagErro, "signInWithEmail:failure", task.exception)
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

    private fun saveUserSP(key:String, value: String){
        val pref = this.getSharedPreferences("com.projetos.amanda.proconanalytics.main_activity", android.content.Context.MODE_PRIVATE)

        val editor = pref.edit()

        editor.putString(key, value)

        editor.apply()

    }

    private fun getUserSP(key: String){

        val pref = this.getSharedPreferences("com.projetos.amanda.proconanalytics.main_activity", android.content.Context.MODE_PRIVATE)

        val result = pref.getString(key, "")

        if(result == ""){
            initLogin()
        }else{
            intent = Intent(this@MainActivity, NavActivity::class.java)
            startActivity(intent)
        }

    }


}


