package com.example.diegoalvis.watchersexplorer.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diegoalvis.watchersexplorer.R
import com.example.diegoalvis.watchersexplorer.databinding.ItemWatcherBinding
import com.example.diegoalvis.watchersexplorer.models.Owner
import com.example.diegoalvis.watchersexplorer.utils.inflate

class WatcherAdapter(private val callback:(watcherPage: String) -> Unit): RecyclerView.Adapter<WatcherViewHolder>() {

    var data = mutableListOf<Owner>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: WatcherViewHolder, position: Int) {
        holder.binding?.watcher = data[position]
        holder.binding?.handler = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WatcherViewHolder(parent.inflate(R.layout.item_watcher))

    override fun getItemCount(): Int = data.size

    fun onWatcherClick(watcherPage: String) {
        callback(watcherPage)
    }
}

class WatcherViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding: ItemWatcherBinding? = DataBindingUtil.bind(view)
}