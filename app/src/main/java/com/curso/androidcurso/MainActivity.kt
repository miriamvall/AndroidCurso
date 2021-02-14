package com.curso.androidcurso

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.curso.androidcurso.dialogs.ConfirmExitDialog
import com.curso.androidcurso.fragments.*
import com.curso.androidcurso.model.GestorPartidasDB
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GestorPartidasDB.initDb(this)

        supportFragmentManager.beginTransaction().replace(R.id.mainActivity, MainScreen()).commit()

        // sincronizar actionBar y drawer

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout,
                R.string.open,
                R.string.close
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById<NavigationView>(R.id.navigation_view)

        //loadDefaultFragment()

        navigationView.setNavigationItemSelectedListener {menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)

            when(menuItem.itemId) {
                R.id.calcFragment -> {
                    // abrir fragment calculadora
                    supportFragmentManager.beginTransaction().replace(R.id.mainActivity, MainScreen()).commit()
                    true
                }
                R.id.gameFragment -> {
                    // abrir fragment juego
                    supportFragmentManager.beginTransaction().replace(R.id.mainActivity, FragmentGame()).commit()
                    true
                }
                R.id.statsFragment -> {
                    // abrir fragment partidas
                    supportFragmentManager.beginTransaction().replace(R.id.mainActivity, FragmentTabs()).commit()
                    true

                }
                R.id.locationFragment -> {
                    // abrir fragment partidas
                    supportFragmentManager.beginTransaction().replace(R.id.mainActivity, FragmentLocation()).commit()
                    true
                }
                else -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                ConfirmExitDialog.build(this, {
                    this.finish()
                }).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            else -> return super.onKeyDown(keyCode, event)
        }
    }

    // override android menu clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // override android back physical button
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun loadDefaultFragment() {
        navigationView.setCheckedItem(R.id.calcFragment)
        supportFragmentManager.beginTransaction().replace(R.id.mainActivity, MainScreen()).commit()
    }

}