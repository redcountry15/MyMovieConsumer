package com.example.submisi3_made_dicoding

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchManager != null){
            val searchView = (menu?.findItem(R.id.action_search)?.actionView) as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,SearchResultActivity::class.java)
                    intent.putExtra(SearchResultActivity().EXTRA_QUERY,query)
                    startActivity(intent)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false


            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }




}
