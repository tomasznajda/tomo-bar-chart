package com.tomasznajda.tomobarchart.label

import android.support.annotation.StringRes
import android.view.View
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class TextResLabel(@StringRes val textResId: Int) : Label {

    override fun render(view: View) = with(view) {
        txtLabel.setText(textResId)
        txtLabel.visible()
        imgLabel.gone()
    }
}