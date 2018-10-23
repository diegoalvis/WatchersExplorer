package com.example.diegoalvis.watchersexplorer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diegoalvis.watchersexplorer.adapters.RepoAdapter
import com.example.diegoalvis.watchersexplorer.fragments.DetailFragment
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val adapter = RepoAdapter {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, DetailFragment.newInstance(), "details")
            .commit()
    }

    private lateinit var  viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        list.adapter = adapter
        list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        viewModel.repos.observe(this, Observer {
            // update UI
            adapter.data = it
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchRepositories()
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
