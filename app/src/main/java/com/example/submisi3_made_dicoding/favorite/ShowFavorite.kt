package com.example.submisi3_made_dicoding.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.submisi3_made_dicoding.R

/**
 * A simple [Fragment] subclass.
 */
class ShowFavorite : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_favorite, container, false)
    }


}
