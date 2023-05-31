package com.lamiademirok.cookthat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lamiademirok.cookthat.database.MealDatabase

class MealViewModelFactory
    (private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) : T {
            return MealView(mealDatabase) as T
        }
}