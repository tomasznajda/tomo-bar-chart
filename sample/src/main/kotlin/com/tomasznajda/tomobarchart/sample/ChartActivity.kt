package com.tomasznajda.tomobarchart.sample

import android.os.Bundle
import android.support.annotation.FontRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.tomasznajda.tomobarchart.ChartItem
import com.tomasznajda.tomobarchart.label.ImageResLabel
import com.tomasznajda.tomobarchart.label.TextLabel
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*

class ChartActivity : AppCompatActivity() {

    private val colors = listOf(R.color.primary,
                                R.color.pink,
                                R.color.purple,
                                R.color.green,
                                R.color.amber,
                                R.color.orange,
                                R.color.brown)

    private val images = listOf(R.drawable.ic_airplane,
                                R.drawable.ic_person,
                                R.drawable.ic_time,
                                R.drawable.ic_tv)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)
        chart.itemClicks.subscribe { toast("${it.name} clicked") }
        chart.itemLongClicks.subscribe { toast("${it.name} long clicked") }


        boxSelectable.setOnCheckedChangeListener { _, isChecked ->
            chart.selectable = isChecked
        }
        boxCustomFonts.setOnCheckedChangeListener { _, isChecked ->
            chart.primaryTypeface = if (isChecked) getFont(R.font.poppins_medium) else null
            chart.secondaryTypeface = if (isChecked) getFont(R.font.poppins_bold) else null
        }
        boxImageLabels.setOnCheckedChangeListener { _, isChecked ->
            prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)
        }
        boxRandomizeColors.setOnCheckedChangeListener { _, isChecked ->
            prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)
        }
    }

    private fun prepareItems(imageLabels: Boolean, randomizeColors: Boolean) {
        chart.items = (10 downTo 0).map {
            ChartItem(name = "Item $it",
                      value = it / 10f,
                      additional = "${it * 100}",
                      label = if (imageLabels) ImageResLabel(randomImage()) else TextLabel("$it"),
                      textColor = if (randomizeColors) randomColor() else null,
                      labelColor = if (randomizeColors) randomColor() else null,
                      labelTextColor = if (randomizeColors) randomColor() else null,
                      barColor = if (randomizeColors) randomColor() else null,
                      selectedBarColor = if (randomizeColors) randomColor() else null,
                      unselectedBarColor = if (randomizeColors) randomColor() else null)
        }
    }

    private fun randomImage() = images[Random().nextInt(images.size)]

    private fun randomColor() = ContextCompat.getColor(this, colors[Random().nextInt(colors.size)])

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    private fun getFont(@FontRes id: Int) = ResourcesCompat.getFont(this, id)
}