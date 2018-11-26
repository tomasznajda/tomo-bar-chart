package com.tomasznajda.tomobarchart

import android.graphics.Typeface
import android.support.annotation.ColorInt

internal data class ChartConfig(val visibleItems: Int,
                                val selectable: Boolean,
                                @ColorInt val dividerColor: Int,
                                @ColorInt val textColor: Int,
                                @ColorInt val labelColor: Int,
                                @ColorInt val labelTextColor: Int,
                                @ColorInt val barColor: Int,
                                @ColorInt val selectedBarColor: Int,
                                @ColorInt val unselectedBarColor: Int,
                                val primaryTypeface: Typeface?,
                                val secondaryTypeface: Typeface?)