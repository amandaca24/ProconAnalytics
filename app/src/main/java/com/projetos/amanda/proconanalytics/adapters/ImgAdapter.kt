package com.projetos.amanda.proconanalytics.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetos.amanda.proconanalytics.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.img_list.view.*

class ImgAdapter (val context: Context, private val userImage: ArrayList<String>? ):
        RecyclerView.Adapter<ImgAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.img_list, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ImgAdapter.ViewHolder, position: Int) {
        userImage?.let {
            var img = userImage[position]
            Picasso.get().load(img).into(holder.itemView.dashImg)
        }
    }

    override fun getItemCount(): Int {
        if(userImage != null){
            return userImage.size
        }else{
            return 0
        }

    }

}
