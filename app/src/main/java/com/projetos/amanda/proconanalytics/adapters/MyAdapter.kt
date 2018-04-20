package com.projetos.amanda.proconanalytics.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetos.amanda.proconanalytics.R
import com.projetos.amanda.proconanalytics.models.FbData
import com.projetos.amanda.proconanalytics.models.Posto
import kotlinx.android.synthetic.main.itemlist.view.*

class MyAdapter constructor(val context: Context, private val postos: ArrayList<FbData>?, val clickListener:(FbData)  -> Unit):
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        val vh = ViewHolder(v)

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        postos?.let {
            var posto = postos[position]

            holder.itemView.idNomePosto.text = posto.nomeFB
            holder.itemView.idPreco.text = posto.valorFB
            holder.itemView.idBairro.text = posto.bairroFB
            holder.itemView.idBandeira.text = posto.bandeiraFB
            holder.itemView.idProduto.text = posto.produtoFB
            holder.itemView.setOnClickListener { clickListener(postos[position]) }

        }
    }

    override fun getItemCount(): Int {
        if(postos != null){
            return postos.size
        }else
            return 0
    }
}