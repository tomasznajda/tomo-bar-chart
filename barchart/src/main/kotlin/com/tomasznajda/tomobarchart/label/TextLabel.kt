package com.tomasznajda.tomobarchart.label

import android.view.View
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class TextLabel(val text: String) : Label {

    override fun render(view: View) = with(view) {
        txtLabel.text = text
        txtLabel.visible()
        imgLabel.gone()
    }
}