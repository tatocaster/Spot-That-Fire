package me.tatocaster.nasaappchallenge.features.home.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_activity.view.*
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.entity.WildFireActivity


class WildfiresAdapter(private val context: Context,
                       private val onClick: (WildFireActivity) -> Unit) : RecyclerView.Adapter<WildfiresAdapter.ActivityHolder>() {
    private var activityList: List<WildFireActivity> = arrayListOf()
    override fun getItemCount(): Int = activityList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivityHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        val item = activityList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }


    fun setActivities(activities: List<WildFireActivity>) {
        activityList = activities
        notifyDataSetChanged()
    }

    inner class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(activity: WildFireActivity) {
            val imageUrls = arrayOf(
                    "https://maps.googleapis.com/maps/api/staticmap?center=${activity.lat},${activity.lng}&zoom=13&size=600x300&markers=${activity.lat},${activity.lng}" +
                            "&key=AIzaSyCvTITAJSKdDr-rKJk5grdu4WpFcdzNy90",
                    activity.imageUrl)

            itemView.title.text = activity.name

            itemView.date.text = activity.createdAt

            itemView.carouselView.pageCount = 2
            itemView.carouselView.setImageListener { position, imageView ->
                Glide.with(context)
                        .load(imageUrls[position])
                        .into(imageView)
            }
            itemView.distance.text = activity.weather
        }
    }
}
