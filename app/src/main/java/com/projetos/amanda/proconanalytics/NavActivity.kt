package com.projetos.amanda.proconanalytics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.*
import com.projetos.amanda.proconanalytics.R.*
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.app_bar_nav.*
import com.projetos.amanda.proconanalytics.constants.Constants
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import android.support.annotation.NonNull
import com.google.firebase.database.FirebaseDatabase


class NavActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Firebase references
    private var auth: FirebaseAuth? = null
    private var user:FirebaseUser? = null


    //UI elements
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var contentV: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_nav)
        setSupportActionBar(toolbar)


        auth = FirebaseAuth.getInstance()
        contentV = findViewById(R.id.drawer_layout)
        user = auth!!.currentUser


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, string.navigation_drawer_open, string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val headerView:View  = nav_view.getHeaderView(0)
        userName = headerView.findViewById(R.id.userName)
        userEmail = headerView.findViewById(R.id.userEmail)

        initUserFirebase(user)


    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        }

        return true

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            id.nav_dashboard -> startActivity(Intent(this@NavActivity, DashboardActivity::class.java))
            id.nav_top10 -> startActivity(Intent(this@NavActivity, TopActivity::class.java))
            id.id_map -> {

            }
            id.nav_logout -> {
                revokeAccess(user)
                startActivity(Intent(this@NavActivity, MainActivity::class.java))
            }

            id.nav_about_procon -> {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.joaopessoa.pb.gov.br/secretarias/procon/")))
            }

            id.nav_about_analytics -> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun revokeAccess(u: FirebaseUser?) {
        if(u!= null){
            auth!!.signOut()
        }else{
            Snackbar.make(contentV, "Nenhum usuário logado!!", Snackbar.LENGTH_LONG).show()
        }

        //Deletando SharedPreferences
        val key = ""
        val pref = this.getSharedPreferences("com.projetos.amanda.proconanalytics.main_activity", android.content.Context.MODE_PRIVATE)
        val result = pref.getString(key, "")

        if(result == Constants.SP_TOKEN_USER){
            pref.edit().remove(Constants.SP_TOKEN_USER).apply()
        }
    }

    private fun initUserFirebase(user: FirebaseUser?){

        if(user != null){
            userEmail.text = user.email
            userName.text = user.displayName
        }else{
            userEmail.text = Constants.emailUserDefault
            userName.text = Constants.nameUserDefault
        }

    }



    private fun getSPUser(key: String){
        val pref  = getSharedPreferences("com.projetos.amanda.proconanalytics.main_activity", android.content.Context.MODE_PRIVATE)
        val result = pref.getString(key, "")

        if(result == Constants.SP_TOKEN_USER){
            val uid = result[0]
        }else{
            initUserFirebase(user)
        }

    }



}
