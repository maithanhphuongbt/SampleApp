package noone.pro.mrvull.sampleapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_location.view.*
import noone.pro.mrvull.sampleapp.R
import noone.pro.mrvull.sampleapp.model.Location
import noone.pro.mrvull.sampleapp.util.function.getWidthDevice
import noone.pro.mrvull.sampleapp.util.function.setDimension

class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(location: Location) {
        itemView.txtRestaurantName.text = location.name
        itemView.txtRestaurantAddress.text = location.vicinity
        setDimension(itemView.itemLayout, getWidthDevice(itemView.context),
                (getWidthDevice(itemView.context) / RATE_HEIGHT_LAYOUT).toFloat())
        setDimension(itemView.imgThumbnail, (getWidthDevice(itemView.context) / RATE_THUMBNAIL),
                (getWidthDevice(itemView.context) / RATE_THUMBNAIL))
        Picasso.get().load(location.icon).into(itemView.imgThumbnail)
    }

    companion object {

        const val RATE_THUMBNAIL = 8
        const val RATE_HEIGHT_LAYOUT = 3.5

        fun create(parent: ViewGroup): LocationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_location, parent, false)
            return LocationViewHolder(view)
        }
    }

}