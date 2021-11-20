package com.nigamar.airquality.live_monitoring.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigamar.airquality.R
import com.nigamar.airquality.live_monitoring.domain.model.AirQualityMatcher
import com.nigamar.airquality.live_monitoring.domain.model.CityListItem
import kotlinx.android.synthetic.main.city_item.view.*

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.CityItemViewHolder>(){

    private var onItemClickListener : ((CityListItem) -> Unit)? = null

    fun setOnItemClickListener(listener : (CityListItem)-> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<CityListItem>() {
        override fun areItemsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
            return oldItem.cityName == newItem.cityName
        }

        override fun areContentsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    inner class CityItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(cityItem: CityListItem) {
            itemView.apply {
                air_quality_container.setCardBackgroundColor(
                    itemView.context.resources.getColor(AirQualityMatcher.getAirQualityColor(cityItem.airQuality))
                )
                air_quality_image.setImageResource(AirQualityMatcher.getAirQualityImage(cityItem.airQuality))
                city_name.text = cityItem.cityName
                city_aqi.text = cityItem.cityAqi
                city_last_updated.text = cityItem.modifiedTime
                setOnClickListener {
                    onItemClickListener?.let {
                        it(cityItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        return CityItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_item , parent , false)
        )
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val cityView = differ.currentList[position]
        holder.bindData(cityView)
    }

    override fun getItemCount() = differ.currentList.size
}