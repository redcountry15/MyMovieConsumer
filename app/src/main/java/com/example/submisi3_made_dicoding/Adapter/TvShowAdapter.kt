package com.example.submisi3_made_dicoding.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import kotlinx.android.synthetic.main.model_view.view.*

class TvShowAdapter(private val List:ArrayList<TvShow>): RecyclerView.Adapter<TvShowAdapter.ShowViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ShowViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
            fun bind(show:TvShow){
                with(itemView){
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w185/" + show.poster)
                        .into(img_photo)

                    txt_name.text = show.title
                    itemView.setOnClickListener{
                        onItemClickCallback?.onItemClicked(show)
                    }

                }

            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ShowViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.model_view,parent,false)
        return ShowViewHolder(inflater)
    }

    override fun getItemCount(): Int = List.size

    override fun onBindViewHolder(holder:ShowViewHolder, position: Int) {
            holder.bind(List[position])
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:TvShow)
    }

}