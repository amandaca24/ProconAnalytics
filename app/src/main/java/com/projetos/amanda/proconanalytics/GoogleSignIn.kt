package com.projetos.amanda.proconanalytics

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.support.design.widget.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignInClient



open class GoogleSignInActivity : AppCompatActivity(), View.OnClickListener {

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null
    // [END declare_auth]

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var logGoogle: Button?= null


    protected override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logGoogle = findViewById<Button>(R.id.btnGoogle)

        logGoogle!!.setOnClickListener { firebaseAuthWithGoogle() }


        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser

    }
    // [END on_start_check_user]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Login com google falhou", e)
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }

        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(GoogleSignInAccount: acct) {
        Log.d(TAG, """firebaseAuthWithGoogle:${acct.id!!}""")
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth!!.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException())
                        Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show()

                    }

                    // [START_EXCLUDE]
                    // [END_EXCLUDE]
                }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]


    override fun onClick(v: View) {
        val i = v.getId()
        if (i == R.id.btnGoogle) {
            signIn()
        }
    }

    companion object {

        private val TAG = "GoogleActivity"
        private val RC_SIGN_IN = 9001
    }
}