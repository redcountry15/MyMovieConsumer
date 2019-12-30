package com.example.submisi3_made_dicoding.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submisi3_made_dicoding.Adapter.MovieAdapter
import com.example.submisi3_made_dicoding.Adapter.TvShowAdapter
import com.example.submisi3_made_dicoding.DetailShow
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.ViewModel.MovieViewModel
import com.example.submisi3_made_dicoding.ViewModel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_show.*
import kotlinx.android.synthetic.main.fragment_show.rv_show

/**
 * A simple [Fragment] subclass.
 */
class ShowFragment : Fragment() {

    companion object{
        private lateinit var tvShowAdapter: TvShowAdapter
        private lateinit var tvShowViewModel:TvShowViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel::class.java)
        tvShowViewModel.show.observe(this,getShowList)
        tvShowViewModel.setShow()

        progresBar.visibility = View.VISIBLE
    }


        private val getShowList =
            Observer<ArrayList<TvShow>> { show ->
                if (show != null) {
                    tvShowAdapter = TvShowAdapter(show)
                    tvShowAdapter.setOnItemClickCallback(object :
                        TvShowAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: TvShow) {
                            val intentShow = Intent(context,DetailShow::class.java)
                            intentShow.putExtra(DetailShow.DATA_SHOW,data)
                            startActivity(intentShow)
                        }
                    })
                    rv_show.apply {
                        layoutManager = GridLayoutManager(activity, 2)
                        adapter = tvShowAdapter
                    }
                    progresBar.visibility = View.GONE
                }
            }

}
