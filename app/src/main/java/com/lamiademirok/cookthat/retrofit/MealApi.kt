package com.lamiademirok.cookthat.retrofit

import com.lamiademirok.cookthat.CategoryList
import com.lamiademirok.cookthat.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import com.lamiademirok.cookthat.MealList
import retrofit2.http.Query

interface MealApi {
     @GET("random.php")
    fun getMeal():Call<MealList>

    //to get the instruction information
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>
}