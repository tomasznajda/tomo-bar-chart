package com.tomasznajda.tomobarchart.label

import android.graphics.drawable.Drawable
import android.view.View
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageDrawableLabel(val drawable: Drawable) {

    fun render(view: View) = with(view) {
        imgLabel.setImageDrawable(drawable)
        imgLabel.visible()
        txtLabel.gone()
    }
}