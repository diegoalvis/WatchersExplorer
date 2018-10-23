package com.example.diegoalvis.watchersexplorer.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diegoalvis.watchersexplorer.R
import com.example.diegoalvis.watchersexplorer.databinding.ItemRepoBinding
import com.example.diegoalvis.watchersexplorer.models.Repo
import com.example.diegoalvis.watchersexplorer.utils.inflate

class RepoAdapter(private val callback:(pos: Int) -> Unit): RecyclerView.Adapter<RepoViewHolder>() {

    var data = listOf<Repo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.binding?.repo = data[position]
        holder.binding?.root?.tag = position
        holder.binding?.handler = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(parent.inflate(R.layout.item_repo))

    override fun getItemCount(): Int = data.size

    fun onRepoClick(pos: Int) {
        callback(pos)
    }
}

class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding: ItemRepoBinding? = DataBindingUtil.bind(view)
}