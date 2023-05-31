package com.lamiademirok.cookthat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.lamiademirok.cookthat.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.RecyclerView
import com.lamiademirok.cookthat.CategoriesAdapter


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm: HomeView
    private lateinit var randomMeals:Meal
    private lateinit var categoriesAdapter: CategoriesAdapter
    companion object{
        const val MEAL_ID = "com.lamiademirok.cookthat.idMeal"
        const val MEAL_NAME= "com.lamiademirok.cookthat.mealName"
        const val MEAL_THUMB = "com.lamiademirok.cookthat.mealThumb"
        const val CATEGORY_NAME ="com.lamiademirok.cookthat.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
    }
    //to view random meal images:
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observerRandomGenMeal()
        onRandomMealClick()

        prepareCategoriesRecyclerView()

        homeMvvm.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java) //defined where to move when clicekd on item
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.cuisineList.apply{
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun onRandomMealClick(){
        binding.mealBanner.setOnClickListener(){
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeals.idMeal)
            intent.putExtra(MEAL_NAME,randomMeals.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeals.strMealThumb)


            startActivity(intent)
        }
    }
    //Found out that using Mvvm is much more efficient way to reduce long and complicated codes
    //so I also implemented HomeView

        private fun observerRandomGenMeal(){
            homeMvvm.observeRandomGeneratedMealData().observe(viewLifecycleOwner
            , { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgMealBanner)

                this.randomMeals = meal
            })
        }

    private fun observeCategoriesLiveData(){
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer
             { categories ->
                categories.forEach { category ->
                    categoriesAdapter.setCategoryList(categories)
                }
            })
    }
}
