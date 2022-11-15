package com.example.firebasefirestore.cats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.firebasefirestore.R
import com.example.firebasefirestore.details.presentation.DetailsFragment

class CatsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        val detailsFragment = DetailsFragment.newInstance("same information")
        view.findViewById<View>(R.id.buttonInfoCat).setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(detailsFragment.javaClass.simpleName)
                .commit()
        }
    }


}