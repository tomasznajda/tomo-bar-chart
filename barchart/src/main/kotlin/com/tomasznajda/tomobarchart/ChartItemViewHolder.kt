package com.tomasznajda.tomobarchart

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tomasznajda.simplerecyclerview.SrvViewHolder
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.view_chart_item.view.*

internal class ChartItemViewHolder(itemView: View,
                                   private val clicks: Subject<ChartItem>,
                                   private val longClicks: Subject<ChartItem>)
    : RecyclerView.ViewHolder(itemView), SrvViewHolder<ChartBundle> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "SetTextI18n")
    override fun bind(bundle: ChartBundle) {
        with(itemView as ChartItemView) {
            visibleItems = bundle.config.visibleItems
            percentage = bundle.item.value
            name.text = bundle.item.name
            percent.text = String.format("%.0f", bundle.item.value * 100) + "%"
            additional.text = bundle.item.additional
            bundle.item.label.render(label)

            name.setTextColor(bundle.item.textColor ?: bundle.config.textColor)
            percent.setTextColor(bundle.item.textColor ?: bundle.config.textColor)
            additional.setTextColor(bundle.item.textColor ?: bundle.config.textColor)
            txtLabel.setTextColor(bundle.item.labelTextColor ?: bundle.config.labelTextColor)
            progress.setBackgroundColor(bundle.getBarColor())
            label.setBackgroundTintColor(bundle.item.labelColor ?: bundle.config.labelColor)

            name.typeface = bundle.config.primaryTypeface
            percent.typeface = bundle.config.primaryTypeface
            additional.typeface = bundle.config.secondaryTypeface
            txtLabel.typeface = bundle.config.secondaryTypeface

            setOnClickListener { clicks.onNext(bundle.item) }
            setOnLongClickListener { longClicks.onNext(bundle.item); true }
        }
    }

    private fun ChartBundle.getBarColor() = when {
        config.selectable && isSelected -> item.selectedBarColor ?: config.selectedBarColor
        config.selectable && isSelected.not() -> item.unselectedBarColor ?: config.unselectedBarColor
        else -> item.barColor ?: config.barColor
    }

    private fun View.setBackgroundTintColor(@ColorInt color: Int) =
            ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))
}