package com.tomasznajda.tomobarchart

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.widget.FrameLayout
import com.tomasznajda.ktx.android.inflate
import com.tomasznajda.simplerecyclerview.adapter.AdvancedSrvAdapter
import com.tomasznajda.tomobarchart.exception.SelectingDisabledException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_chart_background.view.*
import kotlinx.android.synthetic.main.view_chart_foreground.view.*

class TomoBarChart : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        inflate(R.layout.view_chart_background, attachToRoot = true)
        inflate(R.layout.view_chart_foreground, attachToRoot = true)
        attrs?.let { obtainAttrs(it) }
        chartRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        chartRecyclerView.adapter = adapter
        adapter.addViewHolder(ChartBundle::class, R.layout.view_chart_item) {
            ChartItemViewHolder(ChartItemView(context), clicks, longClicks)
        }
    }

    @Suppress("UNCHECKED_CAST")
    var items: List<ChartItem> = emptyList()
        set(value) {
            field = value
            adapter.set(value.toBundleList())
        }
    var visibleItems = 4
        set(value) {
            field = value
            config = config.copy(visibleItems = value)
            reload()
        }
    @ColorInt
    var dividerColor = 0
        set(value) {
            field = value
            config = config.copy(dividerColor = value)
            changeDividerColor(value)
        }
    @ColorInt
    var textColor = 0
        set(value) {
            field = value
            config = config.copy(textColor = value)
            reload()
        }
    @ColorInt
    var labelColor = 0
        set(value) {
            field = value
            config = config.copy(labelColor = value)
            reload()
        }
    @ColorInt
    var labelTextColor = 0
        set(value) {
            field = value
            config = config.copy(labelTextColor = value)
            reload()
        }
    @ColorInt
    var barColor = 0
        set(value) {
            field = value
            config = config.copy(barColor = value)
            reload()
        }
    @ColorInt
    var selectedBarColor = 0
        set(value) {
            field = value
            config = config.copy(selectedBarColor = value)
            reload()
        }
    @ColorInt
    var unselectedBarColor = 0
        set(value) {
            field = value
            config = config.copy(unselectedBarColor = value)
            reload()
        }
    var selectable = false
        set(value) {
            field = value
            config = config.copy(selectable = value)
            reload()
        }
    var primaryTypeface: Typeface? = null
        set(value) {
            field = value
            config = config.copy(primaryTypeface = value)
            refreshBackgroundTypeface(value)
            reload()
        }
    var secondaryTypeface: Typeface? = null
        set(value) {
            field = value
            config = config.copy(secondaryTypeface = value)
            reload()
        }
    val itemClicks: Observable<ChartItem>
        get() = clicks
    val itemLongClicks: Observable<ChartItem>
        get() = longClicks
    var selectedItem: ChartItem
        get() {
            if(selectable.not()) throw SelectingDisabledException()
            return (adapter.items.find { (it as ChartBundle).isSelected } as ChartBundle).item
        }
        set(value) = selectItem(value)

    private val adapter = AdvancedSrvAdapter()
    private val clicks = PublishSubject.create<ChartItem>()
    private val longClicks = PublishSubject.create<ChartItem>()
    private var config = ChartConfig(visibleItems,
                                     selectable,
                                     dividerColor,
                                     textColor,
                                     labelColor,
                                     labelTextColor,
                                     barColor,
                                     selectedBarColor,
                                     unselectedBarColor,
                                     primaryTypeface,
                                     secondaryTypeface)
    private var selectionDisposable: Disposable? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        selectionDisposable = clicks
                .doOnNext { selectItem(it) }
                .subscribe()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        selectionDisposable?.dispose()
        selectionDisposable = null
    }

    private fun reload() = adapter.set(items.toBundleList())

    private fun changeDividerColor(@ColorInt color: Int) {
        highestValue.setTextColor(color)
        lowestValue.setTextColor(color)
        (2 until lines.childCount)
                .forEach { index -> lines.getChildAt(index).setBackgroundColor(color) }
    }

    private fun refreshBackgroundTypeface(typeface: Typeface?) {
        highestValue.typeface = typeface
        lowestValue.typeface = typeface
    }

    private fun obtainAttrs(attrs: AttributeSet) {
        visibleItems = 4
        dividerColor = context.getColorCompat(R.color.divider)
        textColor = context.getColorCompat(R.color.text)
        labelColor = context.getColorCompat(R.color.label)
        labelTextColor = context.getColorCompat(R.color.text)
        barColor = context.getColorCompat(R.color.bar)
        selectedBarColor = barColor
        unselectedBarColor = context.getColorCompat(R.color.divider)
        selectable = false
        context.theme.obtainStyledAttributes(attrs, R.styleable.BarChart, 0, 0).apply {
            try {
                visibleItems = getInt(R.styleable.BarChart_visibleItems, visibleItems)
                dividerColor = getColor(R.styleable.BarChart_dividerColor, dividerColor)
                textColor = getColor(R.styleable.BarChart_textColor, textColor)
                labelColor = getColor(R.styleable.BarChart_labelColor, labelColor)
                labelTextColor = getColor(R.styleable.BarChart_labelTextColor, labelTextColor)
                barColor = getColor(R.styleable.BarChart_barColor, barColor)
                selectedBarColor = getColor(R.styleable.BarChart_selectedBarColor, selectedBarColor)
                unselectedBarColor = getColor(R.styleable.BarChart_unselectedBarColor, unselectedBarColor)
                selectable = getBoolean(R.styleable.BarChart_selectable, selectable)
                primaryTypeface = getResourceId(R.styleable.BarChart_primaryFontFamily, -1)
                        .takeIf { it > 0 }
                        ?.let { ResourcesCompat.getFont(context, it) }
                secondaryTypeface = getResourceId(R.styleable.BarChart_secondaryFontFamily, -1)
                        .takeIf { it > 0 }
                        ?.let { ResourcesCompat.getFont(context, it) }
                        ?: primaryTypeface
            } finally {
                recycle()
            }
        }
    }

    private fun selectItem(chartItem: ChartItem) {
        if (selectable.not()) return
        val oldIndex = adapter.items.indexOfFirst { (it as ChartBundle).isSelected }
        val newIndex = adapter.items.indexOfFirst { (it as ChartBundle).item == chartItem }
        if (oldIndex > -1) changeItemSelection(oldIndex, false)
        changeItemSelection(newIndex, true)
    }

    private fun changeItemSelection(index: Int, selected: Boolean) =
            adapter.replace((adapter.items[index] as ChartBundle).copy(isSelected = selected), index)

    private fun List<ChartItem>.toBundleList() =
            mapIndexed { index, chartItem ->  ChartBundle(chartItem, config, index == 0) }

    private fun Context.getColorCompat(@ColorRes colorResId: Int) =
            ContextCompat.getColor(this, colorResId)
}