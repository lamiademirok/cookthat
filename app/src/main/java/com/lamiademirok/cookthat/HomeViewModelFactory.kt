package com.lamiademirok.cookthat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lamiademirok.cookthat.database.MealDatabase

class HomeViewModelFactory
    (private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return HomeView(mealDatabase) as T
    }
}