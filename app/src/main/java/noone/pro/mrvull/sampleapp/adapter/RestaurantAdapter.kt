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

class RestaurantAdapter(private val locations: ArrayList<Location>)
    : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = locations.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(location: Location) {
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
        }
    }
}