package com.tomasznajda.tomobarchart

import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import com.tomasznajda.tomobarchart.label.Label

data class ChartItem(val name: String,
                     @FloatRange(from = 0.0, to = 1.0) val value: Float,
                     val additional: String,
                     val label: Label,
                     @ColorInt val textColor: Int? = null,
                     @ColorInt val labelColor: Int? = null,
                     @ColorInt val labelTextColor: Int? = null,
                     @ColorInt val barColor: Int? = null,
                     @ColorInt val selectedBarColor: Int? = null,
                     @ColorInt val unselectedBarColor: Int? = null)