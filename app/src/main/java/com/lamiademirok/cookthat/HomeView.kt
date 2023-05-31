package com.lamiademirok.cookthat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamiademirok.cookthat.database.MealDatabase
import com.lamiademirok.cookthat.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeView(
    private val mealDatabase: MealDatabase
):ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealsLiveData = mealDatabase.mealDao().getAllMeals()
    fun getRandomMeal() {
        RetrofitInstance.api.getMeal().enqueue(object : retrofit2.Callback<MealList> {

            //if connected to API and there are results:
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomGeneratedMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomGeneratedMeal
                } else {
                    return
                }
            }

            //if the connection is not successful:
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        }
        )
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

                override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.e("HomeView", t.message.toString())
                }
            })
        }

    fun deleteMeal(meal:Meal){
        viewModelScope.launch{
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch{
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun observeRandomGeneratedMealData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }

}