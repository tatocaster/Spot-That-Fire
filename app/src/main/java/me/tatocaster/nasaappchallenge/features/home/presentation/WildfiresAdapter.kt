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
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder
import java.text.SimpleDateFormat
import java.util.*

class WildfiresAdapter(private val context: Context,
                       private val onClick: (WildFireActivity) -> Unit) : RecyclerView.Adapter<WildfiresAdapter.ActivityHolder>() {
    private var activityList: List<WildFireActivity> = arrayListOf()
    override fun getItemCount(): Int = activityList.size
    private val periodFormatter: PeriodFormatter = PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(":")
            .appendMinutes()
            .printZeroAlways()
            .minimumPrintedDigits(2)
            .appendSeparator(":")
            .appendSeconds()
            .minimumPrintedDigits(2)
            .toFormatter()
    private val dateFormatter = SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())

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
            itemView.title.text = activity.name
            Glide.with(context)
                    .load(activity.imageUrl)
                    .into(itemView.imageView)
            itemView.date.text = activity.createdAt
            itemView.distance.text = activity.latLng
        }
    }
}
