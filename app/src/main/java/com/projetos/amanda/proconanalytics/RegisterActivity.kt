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
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var nomeInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var senhaInput: TextInputEditText
    private var btnCadastrar: Button?=null
    private lateinit var content: View

    private lateinit var mProgressBar: ProgressBar


    //Firebase references
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null
    private var auth: FirebaseAuth? = null

    private val tagLog = "Register_Activity"
    //global variables
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initFirebase()
    }

    private fun initFirebase(){
        emailInput = findViewById(R.id.emailInputTI)
        senhaInput = findViewById(R.id.senhaInputTI)
        nomeInput = findViewById(R.id.nomeInputTI)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        content = findViewById(R.id.idRegisterAct)
        mProgressBar = findViewById(R.id.pgBar)

        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference!!.child("Users")
        auth = FirebaseAuth.getInstance()

        btnCadastrar!!.setOnClickListener { criarNovaConta()

        }

    }

    private fun criarNovaConta(){
        email = emailInput.text.toString()
        password = senhaInput.text.toString()
        name = nomeInput.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {
            auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(tagLog, "Usuário cadastrado com sucesso!")
                    Snackbar.make(content, "Usuário cadastrado com sucesso!", Snackbar.LENGTH_LONG).show()
                    val userId = auth!!.currentUser!!.uid
                    //Verify Email
                    verifyEmail()
                    //update user profile information
                    val currentUserDb = databaseReference!!.child(userId)
                    currentUserDb.child("Email").setValue(email)
                    currentUserDb.child("Name").setValue(name)
                    updateUserInfoAndUI()

                    mProgressBar.progress = 20
                    mProgressBar.secondaryProgress = 50
                } else {
                            // If sign in fails, display a message to the user.
                    Log.w(tagLog, "Usuário não cadastrado", task.exception)
                    Snackbar.make(content, "Problemas ao cadastrar o usuário, tente novamente", Snackbar.LENGTH_LONG).show()} }
        } else {
            Snackbar.make(content, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
        }


    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        }

    private fun verifyEmail() {
        val user = auth!!.currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(content, """E-mail de verificação enviado para ${user.email}""",
                                Snackbar.LENGTH_LONG).show()
                    } else {
                        Log.e(tagLog, "sendEmailVerification", task.exception)
                        Snackbar.make(content, "Problemas ao enviar e-mail de verificação", Snackbar.LENGTH_LONG).show()
                    }
                }
    }


}



