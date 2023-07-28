package com.example.simpledictionary.ui.main_list

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.navigation.fragment.FragmentNavigator
import com.example.simpledictionary.R
import com.example.simpledictionary.databinding.ActivityMainBinding
import com.example.simpledictionary.databinding.ActivityMainLoadingBinding
import com.example.simpledictionary.db.DictionaryDBHelper
import com.example.simpledictionary.db.utils.DBPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingLoading : ActivityMainLoadingBinding
    private val navController by lazy { findNavController(R.id.nav_host_fragment_content_main) }
    @Inject
    lateinit var dbPrefs: DBPrefs
    @Inject
    lateinit var dictionaryDBHelper: DictionaryDBHelper

    private val viewModel: MainViewModel by viewModels()
//    @Inject
//    lateinit var viewModelFactory: MainViewModelFactory

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        //Test Builder Pattern
//        Dictionary.Builder()
//            .word(true)
//            .type(true)
//            .meaning(true)
//            .build()

        //setupMainUi()
        if(!dbPrefs.getDBCreatedStatus(DictionaryDBHelper.DB_CREATED)){
            setupLoadingUi()
            //Copying DB:
            LoadDictTask(this).execute()
        }else{
            setupMainUi()
        }
    }

    private fun setupLoadingUi() {
        bindingLoading = ActivityMainLoadingBinding.inflate(layoutInflater)
        setContentView(bindingLoading.root)
    }

    private fun setupMainUi() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.fab.setOnClickListener { view ->
            if (navController.currentDestination?.id == R.id.SecondFragment)
                navController.navigate(R.id.action_SecondFragment_to_FirstFragment)
            else
                Snackbar.make(view, "You are on your desired page.", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.fab)
                    .setAction("Action", null).show()
        }
    }

    fun isDBLoaded():Boolean{
        return dbPrefs.getDBCreatedStatus(DictionaryDBHelper.DB_CREATED)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem : MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search your Name"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("MainActivity", "QuerySubmit: $p0")
                performSearch(p0?:"")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("MainActivity", "QueryChange: $p0")
                performSearch(p0?:"")
               return true
            }

        })
        return true
    }

    fun performSearch(prefix: String){
        //secondFragment.searchedList(prefix)
        val fragment = navController.currentDestination?.id?.let { navController.graph.findNode(it) }
        if (fragment is FragmentNavigator.Destination) {
            if (fragment.className == SecondFragment::class.java.name) {
                val secondFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                    ?.childFragmentManager
                    ?.fragments
                    ?.firstOrNull { it is SecondFragment } as? SecondFragment
                secondFragment?.searchedList(prefix)
            }else{
                navController.navigate(R.id.SecondFragment)
//                val secondFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
//                    ?.childFragmentManager
//                    ?.fragments
//                    ?.firstOrNull { it is SecondFragment } as? SecondFragment
//                secondFragment?.searchedList(prefix)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->{
                Log.d("MainActivity", "Clicke b d on : Settings")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    class LoadDictTask(activity: MainActivity){
        private var mActivity = WeakReference(activity)
//        fun execute(){
//            Thread(Runnable {
//                if(getActivityInstance()?.dictionaryDBHelper?.readableDatabase?.isOpen == true){
//                    Log.d(TAG, "Database Loaded.")
//                }
//                getActivityInstance()?.runOnUiThread {
//                    getActivityInstance()?.setupMainUi()
//                }
//            }).start()
//        }

        @OptIn(DelicateCoroutinesApi::class)
        fun execute(){
            GlobalScope.launch(Dispatchers.IO) {
                if(getActivityInstance()?.dictionaryDBHelper?.readableDatabase?.isOpen == true){
                    Log.d(TAG, "Database Loaded.")
                }
                withContext(Dispatchers.Main){
                    getActivityInstance()?.setupMainUi()
                }
            }
        }

        private fun getActivityInstance() = mActivity.get()
    }
}