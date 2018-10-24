package com.example.diegoalvis.watchersexplorer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diegoalvis.watchersexplorer.R
import com.example.diegoalvis.watchersexplorer.adapters.WatcherAdapter
import com.example.diegoalvis.watchersexplorer.databinding.DetailFragmentBinding
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.detail_fragment.view.*
import kotlinx.android.synthetic.main.list_repo_fragment.view.*


class DetailFragment : Fragment() {

    companion object {
        const val TAG = "detail_fragment"
        fun newInstance() = DetailFragment()
    }

    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: SharedViewModel

    private val adapter = WatcherAdapter(this::watcherSelected)

    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        val view = binding.root

        view.watcherList.adapter = adapter
        view.watcherList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 2)

        var mPreviousTotal = 0
        var mLoading = true
        view.watcherList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount

                if (mLoading) {
                    if (totalItemCount != null) {
                        if (totalItemCount > mPreviousTotal) {
                            mLoading = false
                            mPreviousTotal = totalItemCount
                        }
                    }
                }

                val visibleThreshold = 8
                if (totalItemCount != null) {
                    val itemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (!mLoading && (itemPosition + visibleThreshold) >= totalItemCount) {
                        mLoading = true
                        onLoadMore()
                    }
                }
            }

            @SuppressLint("CheckResult")
            fun onLoadMore() {
                viewModel
                    .getMoreWatchers()
                    ?.subscribe({
                        val startIndex = adapter.data.size
                        adapter.data.addAll(it)
                        adapter.notifyItemRangeInserted(startIndex, adapter.data.size)
                    }, { it.printStackTrace() })
            }
        })

        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel.watchers.observe(this, Observer {
            mLoading = false
            mPreviousTotal = 0
            adapter.data = it.distinct().toMutableList()
        })

        binding.itemRepo.repo = viewModel.selected.value


        // fetch data
        viewModel.getWatchers()?.subscribe({ adapter.data = it }, { it.printStackTrace() })

        return view
    }

    private fun watcherSelected(watcherPage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}