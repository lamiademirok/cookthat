package com.lamiademirok.cookthat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.lamiademirok.cookthat.database.MealDatabase
import com.lamiademirok.cookthat.databinding.ActivityMealBinding

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm: MealView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealView::class.java]
        getMealInformationIntent()
        setInformationViews()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener{
            mealToSave?.let{
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var mealToSave:Meal?=null
    private fun observerMealDetailsLiveData(){
        mealMvvm.observerMailDetailsLiveData().observe(this,object: Observer<Meal> {
            override fun onChanged(t: Meal?) {
                val meal = t
                mealToSave = meal
                binding.textCategories.text = "Category : ${meal!!.strCategory}"
                binding.textLocation.text = "Area : ${meal.strArea}"
                binding.mealInstructions.text= meal.strInstructions
            }
        })
        }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    //Function to get the info and this way the information will be displayed on the
    //recipe page.
    private fun getMealInformationIntent() {

        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

}