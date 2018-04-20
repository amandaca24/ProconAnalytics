package com.projetos.amanda.proconanalytics

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
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.projetos.amanda.proconanalytics.R.*
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.app_bar_nav.*

class NavActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Firebase references
    private var auth: FirebaseAuth? = null
    //UI elements
    private var userName: TextView? = null
    private var userEmail: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_nav)
        setSupportActionBar(toolbar)

        userEmail = findViewById(R.id.userEmail)
        userName = findViewById(R.id.userName)

        val user = FirebaseAuth.getInstance().currentUser

        initUserFirebase(user)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, string.navigation_drawer_open, string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initUserFirebase(user: FirebaseUser?){

        if(user != null){
            userEmail!!.text = user.email
            userName!!.text = user.displayName
        }



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
                revokeAccess()
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

    private fun revokeAccess() {
        // Firebase sign out
        auth!!.signOut()

    }



}
