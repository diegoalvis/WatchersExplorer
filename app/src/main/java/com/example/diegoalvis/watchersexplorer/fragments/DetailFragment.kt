package com.example.diegoalvis.watchersexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diegoalvis.watchersexplorer.R
import com.example.diegoalvis.watchersexplorer.adapters.WatcherAdapter
import com.example.diegoalvis.watchersexplorer.databinding.DetailFragmentBinding
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.detail_fragment.view.*


class DetailFragment : Fragment() {

    companion object {
        const val TAG = "detail_fragment"
        fun newInstance() = DetailFragment()
    }

    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: SharedViewModel

    private val adapter = WatcherAdapter(this::watcherSelected)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel.watchers.observe(this, Observer { adapter.data = it })

        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        val view = binding.root

        binding.itemRepo.repo = viewModel.selected.value

        view.watcherList.adapter = adapter
        view.watcherList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 2)

        // fetch data
        viewModel.getWatchers()

        return view
    }

    private fun watcherSelected(watcherPage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}