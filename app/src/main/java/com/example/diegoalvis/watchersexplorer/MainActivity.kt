package com.example.diegoalvis.watchersexplorer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diegoalvis.watchersexplorer.adapters.RepoAdapter
import com.example.diegoalvis.watchersexplorer.databinding.ActivityMainBinding
import com.example.diegoalvis.watchersexplorer.fragments.DetailFragment
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding

    private val showCloseView = ObservableBoolean(false)
    private val adapter = RepoAdapter(this::repoSelected)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.showCloseView = showCloseView

        switchView.setOnCheckedChangeListener { _, isChecked ->
            switchView.text = getString(if (isChecked) R.string.sort_asc else R.string.sort_desc)
        }

        list.adapter = adapter
        list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        viewModel.repos.observe(this, Observer { adapter.data = it })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = false
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchRepositories(query)
                return false
            }
        })

        setCloseDetailButton()
    }

    private fun repoSelected(pos: Int) {
        showCloseView.set(true)
        viewModel.select(adapter.data[pos])
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, DetailFragment.newInstance(), DetailFragment.TAG)
            .commitAllowingStateLoss()
    }

    private fun setCloseDetailButton() {
        closeDetails.setOnClickListener {
            showCloseView.set(false)
            supportFragmentManager.fragments.filter { it.tag == DetailFragment.TAG }.forEach { fragment ->
                supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
}