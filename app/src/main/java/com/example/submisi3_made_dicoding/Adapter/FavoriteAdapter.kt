package com.example.submisi3_made_dicoding.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Favorite
import kotlinx.android.synthetic.main.model_view.view.*
import kotlinx.android.synthetic.main.model_view_favorite.view.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    var myFavorite = ArrayList<Favorite>()

        set(favList){
            if(favList.size > 0){
                this.myFavorite.clear()
            }
            this.myFavorite.addAll(favList)
            notifyDataSetChanged()
        }


    private  var onItemClickCallback : OnItemClickCallback? =null

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(myFav : Favorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w342/" + myFav.poster)
                    .into(img_photo)

                txt_name.text = myFav.title
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(myFav,position)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int  = this.myFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(myFavorite[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite, position: Int)
    }
}