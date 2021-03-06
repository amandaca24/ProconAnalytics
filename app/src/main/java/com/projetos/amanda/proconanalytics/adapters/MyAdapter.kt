package com.projetos.amanda.proconanalytics.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetos.amanda.proconanalytics.MapsActivity
import com.projetos.amanda.proconanalytics.R
import com.projetos.amanda.proconanalytics.models.FbData
import kotlinx.android.synthetic.main.itemlist.view.*

class MyAdapter (val context: Context, val postos: ArrayList<FbData>, val clickListener:(FbData)  -> Unit):
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        postos?.let {
            val posto = postos[position]

            holder.itemView.idNomePosto.text = posto.nomeFB
            holder.itemView.idPreco.text = posto.valorFB
            holder.itemView.idBairro.text = posto.bairroFB
            holder.itemView.idBandeira.text = posto.bandeiraFB
            holder.itemView.idProduto.text = posto.produtoFB
            holder.itemView.setOnClickListener { openMaps(postos[position]) }

        }
    }

    override fun getItemCount(): Int {
        if(postos != null){
            return postos.size
        }else{
            return 0
        }

    }

    private fun openMaps(posto: FbData){
        var intent = Intent(context, MapsActivity::class.java)
        intent.putExtra("latitude", posto.latitude)
        intent.putExtra("longitude",posto.longitude)
        intent.putExtra("name", posto.nomeFB)
        context.startActivity(intent)

    }

}