package com.example.diegoalvis.watchersexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.diegoalvis.watchersexplorer.R
import com.example.diegoalvis.watchersexplorer.databinding.DetailFragmentBinding
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel


class DetailFragment : Fragment() {

    companion object {
        val TAG = "detail_fragment"
        fun newInstance() = DetailFragment()
    }

    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel.getWatchers()

        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        val view = binding.root

        binding.itemRepo.repo = viewModel.selected.value


        return view

    }

}
