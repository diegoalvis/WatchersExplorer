package com.example.diegoalvis.watchersexplorer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diegoalvis.watchersexplorer.databinding.ActivityMainBinding
import com.example.diegoalvis.watchersexplorer.fragments.DetailFragment
import com.example.diegoalvis.watchersexplorer.fragments.ListRepoFragment
import com.example.diegoalvis.watchersexplorer.utils.applyUISchedulers
import com.example.diegoalvis.watchersexplorer.utils.replace
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding

    private val showCloseView = ObservableBoolean(false)
    private val listRepoFragment = ListRepoFragment.newInstance()
    private val subscription = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        viewModel.selected.observe(this, Observer { it?.run { repoSelected() } })

        binding.showCloseView = showCloseView
        binding.isLoading = viewModel.isLoading

        switchView.setOnCheckedChangeListener { _, isChecked ->
            switchView.text = getString(if (isChecked) R.string.sort_asc else R.string.sort_desc)
        }
        info.setOnClickListener { startActivity<InfoActivity>() }
        ic_github.setOnClickListener { startActivity<InfoActivity>() }
        closeDetails.setOnClickListener { showSearchList() }


        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = false
            @SuppressLint("CheckResult")
            override fun onQueryTextSubmit(query: String): Boolean {
                showSearchList()
                val sortByStars = if (checkboxSortByStars.isChecked) "stars" else null
                val order = if (sortByStars != null) switchView.text.toString() else null
                subscription.add(
                    viewModel
                        .searchRepositories(query, sortByStars, order)
                        .applyUISchedulers()
                        .subscribe({
                            viewModel.repos.value = it.items
                        }, {
                            Snackbar.make(binding.root, "Host unreachable", Snackbar.LENGTH_LONG).show()
                            it.printStackTrace()
                        })
                )
                return false
            }
        })

        showSearchList()
    }

    override fun onResume() {
        super.onResume()
        val connectionState =
            Snackbar.make(binding.root, "No Internet connection.", Snackbar.LENGTH_INDEFINITE).setAction("CLOSE") {}
        subscription.add(
            Observable.interval(1, 10, TimeUnit.SECONDS)
                .flatMap { ReactiveNetwork.observeInternetConnectivity() }
                .applyUISchedulers()
                .subscribe({ isConnected ->
                    if (isConnected) {
                        connectionState.dismiss()
                    } else if (!connectionState.isShown) {
                        connectionState.show()
                        progress.visibility = GONE
                    }
                }, {
                    Toast.makeText(this, "Error when trying to get connection status", Toast.LENGTH_SHORT).show()
                })
        )
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    private fun repoSelected() {
        showCloseView.set(true)
        switchView.visibility = GONE
        checkboxSortByStars.visibility = GONE
        supportFragmentManager.replace(R.id.container, DetailFragment.newInstance(), DetailFragment.TAG)
    }

    private fun showSearchList() {
        showCloseView.set(false)
        switchView.visibility = VISIBLE
        checkboxSortByStars.visibility = VISIBLE
        supportFragmentManager.replace(R.id.container, listRepoFragment, ListRepoFragment.TAG)
    }
}