package com.example.submisi3_made_dicoding.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.R
import kotlinx.android.synthetic.main.model_view.view.*

class MovieAdapter(private val List:ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

 /*   fun setMovie(items:ArrayList<Movie>){
        List.clear()
        List.addAll(items)
        notifyDataSetChanged()
    }*/


    inner class MovieViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
            fun bind(movie:Movie){
                with(itemView){
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w342/" + movie.poster)
                        .into(img_photo)
                    txt_name.text = movie.title
                    itemView.setOnClickListener{
                        onItemClickCallback?.onItemClicked(movie)
                    }
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.model_view,parent,false)
        return MovieViewHolder(inflater)
    }

    override fun getItemCount(): Int = List.size

    override fun onBindViewHolder(holder:MovieViewHolder, position: Int) {
        holder.bind(List[position])
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:Movie)
    }
}