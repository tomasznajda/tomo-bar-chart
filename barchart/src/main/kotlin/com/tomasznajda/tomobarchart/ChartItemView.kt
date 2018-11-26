package com.tomasznajda.tomobarchart

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.tomasznajda.ktx.android.inflate
import kotlinx.android.synthetic.main.view_chart_item.view.*

internal class ChartItemView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate(R.layout.view_chart_item, attachToRoot = true)
    }

    var percentage: Float = 1f
    var visibleItems: Int = 4

    private val parentView: ViewGroup
        get() = parent as ViewGroup
    private val barWidth: Float
        get() = (parentView.width - 108f.dpToPx(context)) / (visibleItems + 0.5f)
    private val barHeight: Float
        get() = (parentView.height - 100f.dpToPx(context)) * percentage
    private var lastParentSize: Pair<Int, Int>? = null
    private var lastMeasuredPercentage: Float? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (hasParentSizeChanged() or hasPercentageChanged()) measureBarHeight()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun hasParentSizeChanged() =
            parentView.width != lastParentSize?.width() || parentView.height != lastParentSize?.height()

    private fun hasPercentageChanged() = percentage != lastMeasuredPercentage

    private fun measureBarHeight() {
        lastParentSize = parentView.width to parentView.height
        lastMeasuredPercentage = percentage
        progress.layoutParams = FrameLayout.LayoutParams(barWidth.toInt(), barHeight.toInt(), Gravity.BOTTOM)
    }

    private fun Float.dpToPx(context: Context) = this * context.resources.displayMetrics.density

    private fun Pair<Int, Int>.width() = first

    private fun Pair<Int, Int>.height() = second
}