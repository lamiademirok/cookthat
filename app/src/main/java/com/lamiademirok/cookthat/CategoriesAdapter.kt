package com.lamiademirok.cookthat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamiademirok.cookthat.databinding.CategoryItemBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoryList:List<Category> = ArrayList()
    var onItemClick: ((Category) -> Unit)? = null
    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }


    class CategoryViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {

            Glide.with(holder.itemView)
                .load(categoryList[position].strCategoryThumb) //get pictures
                .into(holder.binding.imgCategory)
            holder.binding.textCategoryName.text = categoryList[position].strCategory
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }


    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}