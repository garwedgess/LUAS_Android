package eu.wedgess.luas.features.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.wedgess.luas.R
import eu.wedgess.luas.domain.model.TramEntity
import kotlinx.android.synthetic.main.row_tram.view.*

class TramForecastAdapter :
    ListAdapter<TramEntity, TramForecastAdapter.ForecastViewHolder>(TramDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_tram, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindTramEntity(getItem(position))
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindTramEntity(tramEntity: TramEntity) {
            with(tramEntity) {
                itemView.rowTramDestinationTV.text = destination
                itemView.rowTramDueAtTV.text = dueMins
                when {
                    dueMins == "DUE" -> {
                        itemView.rowTramDueAtValTV.text = itemView.context.getString(
                            R.string.time_unit_now
                        )
                    }
                    dueMins.isBlank() -> {
                        itemView.rowTramDueAtTV.visibility = View.GONE
                        itemView.rowTramDueAtValTV.visibility = View.GONE
                    }
                    dueMins.toIntOrNull() == 1 -> {
                        itemView.rowTramDueAtValTV.text = itemView.context.getString(
                            R.string.time_unit_min
                        )
                    }
                    else -> {
                        itemView.rowTramDueAtValTV.text = itemView.context.getString(
                            R.string.time_unit_mins
                        )
                    }
                }
            }
        }
    }

    class TramDiffCallback : DiffUtil.ItemCallback<TramEntity>() {

        override fun areItemsTheSame(oldItem: TramEntity, newItem: TramEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: TramEntity, newItem: TramEntity): Boolean {
            return oldItem == newItem
        }

    }
}