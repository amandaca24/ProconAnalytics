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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var nomeInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var senhaInput: TextInputEditText
    private var btnCadastrar: Button?=null


    //Firebase references
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null
    private var auth: FirebaseAuth? = null

    private val TAG = "RegisterActivity"
    //global variables
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initFirebase()
    }

    private fun initFirebase(){
        emailInput = findViewById(R.id.emailInput)
        senhaInput = findViewById(R.id.senhaInput)
        btnCadastrar = findViewById(R.id.btnCadastrar)

        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference!!.child("Users")
        auth = FirebaseAuth.getInstance()

        btnCadastrar!!.setOnClickListener { criarNovaConta() }
    }

    private fun criarNovaConta(){
        email = emailInput.text.toString()
        password = senhaInput.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "Usuário cadastrado com sucesso!")
                    val userId = auth!!.currentUser!!.uid
                    //Verify Email
                    verifyEmail()
                    //update user profile information
                    val currentUserDb = databaseReference!!.child(userId)
                    currentUserDb.child("Email").setValue(email)
                    updateUserInfoAndUI()
                } else {
                            // If sign in fails, display a message to the user.
                    Log.w(TAG, "Usuário não cadastrado", task.exception)
                    Toast.makeText(this@RegisterActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()} }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this@RegisterActivity,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(this@RegisterActivity,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}



