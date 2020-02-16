package com.example.submisi3_made_dicoding.favorite


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submisi3_made_dicoding.Adapter.MovieAdapter
import com.example.submisi3_made_dicoding.Adapter.TvShowAdapter
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow

import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import com.example.submisi3_made_dicoding.ui.TvShow.DetailShow
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.android.synthetic.main.fragment_show_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class ShowFavorite : Fragment() {

    private lateinit var showAdapter: TvShowAdapter
    private lateinit var showHelper: ShowHelper
    private lateinit var favorite: ArrayList<TvShow>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showHelper = ShowHelper.getInstance(context!!)
        showHelper.open()
        favorite = showHelper.getAllShow()
        showAdapter = TvShowAdapter(favorite)

        rv_favorite_show.apply {
            adapter = showAdapter
            layoutManager = GridLayoutManager(context,2)
            showAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback{
                override fun onItemClicked(data: TvShow) {
                    val intent = Intent(context, DetailShowFavorite::class.java)
                    intent.putExtra(DetailShowFavorite.DATA_SHOW_FAVORITE,data)
                    startActivity(intent)
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()
        favorite.clear()
        favorite.addAll(showHelper.getAllShow())
        rv_favorite_show.adapter?.notifyDataSetChanged()
    }
}
