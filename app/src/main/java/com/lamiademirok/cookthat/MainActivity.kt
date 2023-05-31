package com.lamiademirok.cookthat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lamiademirok.cookthat.database.MealDatabase

class MainActivity : AppCompatActivity() {
    val viewModel: HomeView by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeView::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Added code to make the menu navigation possible.
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigaton)
        val navController = Navigation.findNavController(this,R.id.fragment_host)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}