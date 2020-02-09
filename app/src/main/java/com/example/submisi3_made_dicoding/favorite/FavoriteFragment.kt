package com.example.submisi3_made_dicoding.favorite


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import com.example.submisi3_made_dicoding.Adapter.FavoritePageAdapter
import com.example.submisi3_made_dicoding.Adapter.SectionPageAdapter
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    companion object {
        //movie needs
        lateinit var movieHelper: MovieHelper
        lateinit var favoriteMovie: ArrayList<Movie>
        //show needs
        lateinit var showHelper: ShowHelper
        lateinit var favoriteShow: ArrayList<TvShow>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionPager = FavoritePageAdapter(view.context,childFragmentManager)

        view_pager_fav.adapter = sectionPager
        tab_layout_fav.setupWithViewPager(view_pager_fav)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }


}
