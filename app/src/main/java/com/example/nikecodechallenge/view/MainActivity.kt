package com.example.nikecodechallenge.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nikecodechallenge.R
import com.example.nikecodechallenge.model.DescriptionResponse
import com.example.nikecodechallenge.viewmodel.UrbanDictionaryViewModel
import com.example.nikecodechallenge.viewmodel.UrbanDictionaryViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewModel: UrbanDictionaryViewModel by lazy {
        ViewModelProvider(
            this,
            UrbanDictionaryViewModelFactory()
        )
            .get(UrbanDictionaryViewModel::class.java)
    }
    val definitionAdapter: DefinitionAdapter by lazy {
        DefinitionAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        viewModel.getUdDescription().observe(this,
            Observer { t ->
                t?.let {
                    if (it.list.isEmpty()) {
                        Toast.makeText(this@MainActivity, "No Results Found", Toast.LENGTH_LONG)
                            .show()
                    }
                    initAdapter((it))
                }
            })

        viewModel.getUdDescriptionError().observe(this,
            Observer { t ->
                t?.let {
                    recycler_view.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, t, Toast.LENGTH_LONG).show()
                }
            })


    }

    private fun initViews() {
        setSupportActionBar(ud_toolbar)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = definitionAdapter
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sort_up -> {
                    sortUp()
                    drawer_layout.closeDrawers()
                    true
                }
                R.id.sort_down -> {
                    sortDown()
                    drawer_layout.closeDrawers()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun sortUp() {
        viewModel.sortDataUp()
    }

    private fun sortDown() {
        viewModel.sortDataDown()
    }

    fun initAdapter(dataSet: DescriptionResponse) {
        recycler_view.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        definitionAdapter.dataSet = dataSet
    }

    fun searchForDefinition(userInput: String) {
        viewModel.getDescription(userInput)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search_action)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    recycler_view.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    searchForDefinition(it)
                }
                menu.findItem(R.id.search_action).collapseActionView()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        val drawerAction = menu.findItem(R.id.drawer_menu)
        drawerAction.setOnMenuItemClickListener {
            drawer_layout.openDrawer(navigation_view)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}
