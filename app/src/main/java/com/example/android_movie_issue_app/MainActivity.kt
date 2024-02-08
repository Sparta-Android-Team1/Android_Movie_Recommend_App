package com.example.android_movie_issue_app

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.ItemData
import com.example.android_movie_issue_app.databinding.ActivityMainBinding
import com.example.android_movie_issue_app.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    var homeData=mutableListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Log.d("MainActivity","#csh First MainActivity")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //테스트 코드(민용) 지워도 됩니다.
        communicateNetWork()
        Log.d("MainActivity","#csh communicateNetWork()")

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_channel, R.id.navigation_search, R.id.navigation_my_page
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        Log.d("MainActivity","#csh navController")
    }

    private fun communicateNetWork() = lifecycleScope.launch {
        val apiData = RetrofitClient.youtubeApi?.searchVideo("mostPopular", 20)

        apiData!!.items.forEach { it ->
            val item= ItemData(it.snippet.thumbnails.default.url,it.snippet.title, it.snippet.channelTitle)
            homeData.add(item) }
        Log.d("MainActivity","#csh data=$homeData")

//        val searchData=mutableListOf<ItemData>()
//        apiData!!.items.forEach {}

        //apiData!!.items[0].snippet.channelTitle
        //apiData!!.items[0].snippet.publishedAt
        //apiData!!.items[0].statistics.viewCount
        //apiData!!.items[0].snippet.thumbnails.high.url

//        Log.i("Minyong", apiData!!.items[0].snippet.toString())
//        Log.i("Minyong", apiData!!.items[15].statistics.toString())
    }
}