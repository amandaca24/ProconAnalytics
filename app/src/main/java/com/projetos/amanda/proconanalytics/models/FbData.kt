package com.projetos.amanda.proconanalytics.models

import com.google.firebase.database.*
import java.util.*

class FbData {

    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mDatabaseRef: DatabaseReference? = null

    var bairroFB:String = ""
    var bandeiraFB:String = ""
    var nomeFB:String = ""
    var produtoFB:String = ""
    var valorFB:String = ""
    var zonaFB:String = ""


    private fun getDataFB(){

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseRef = mFirebaseDatabase!!.getReference()

        val mRef = mFirebaseDatabase!!.getReference("Pesquisas")

        mRef.addValueEventListener( object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {

                for(snapshot in p0.children){

                    bairroFB = mRef.child("bairro").limitToFirst(10).toString()
                    bandeiraFB = mRef.child("bandeira").limitToFirst(10).toString()
                    nomeFB = mRef.child("nome").limitToFirst(10).toString()
                    valorFB = mRef.child("valor").limitToFirst(10).toString()
                    produtoFB = mRef.child("combustivel").limitToFirst(10).toString()
                    zonaFB = mRef.child("zona").limitToFirst(10).toString()


                    }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun saveBD(){
        var p:Posto = Posto()

        p.bairro = bairroFB
        p.bandeira = bandeiraFB
        p.nome = nomeFB
        p.produto = produtoFB
        p.valor = valorFB
        p.zona = zonaFB
    }
}