package com.projetos.amanda.proconanalytics.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast


class ConnReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        var cm:ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

        var netInfo = cm.activeNetworkInfo

        if(netInfo != null && netInfo.isConnectedOrConnecting){
            Log.i("INTERNET SERVICE","INTERNET CONECTADA")
        }else{
            Toast.makeText(context.applicationContext, "Precisa estar conectado a internet para ter acesso aos dados", Toast.LENGTH_LONG).show()
            Log.i("INTERNET SERVICE","DESCONECTADO. PRECISA DE INTERNET PARA ACESSO AOS DADOS")
        }

    }
}