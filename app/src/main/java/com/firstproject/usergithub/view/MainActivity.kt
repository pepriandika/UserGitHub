package com.firstproject.usergithub.view


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstproject.usergithub.R
import com.firstproject.usergithub.adapter.UserAdapter
import com.firstproject.usergithub.databinding.ActivityMainBinding
import com.firstproject.usergithub.viewmodel.MainViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)

        viewModel.loadingVisibility.observe(this) { visibility ->
            binding.progressBar.visibility = visibility
        }

        val userAdapter = UserAdapter()
        binding.rvUserList.adapter = userAdapter

        viewModel.userData.observe(this) { userList ->
            userAdapter.submitList(userList)
        }

        viewModel.fetchUserData("a")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.isDataFetched = false
                viewModel.fetchUserData(query = query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val userAdapter = UserAdapter()
        binding.rvUserList.adapter = userAdapter
        viewModel.userData.observe(this) { userList ->
            userAdapter.submitList(userList)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


