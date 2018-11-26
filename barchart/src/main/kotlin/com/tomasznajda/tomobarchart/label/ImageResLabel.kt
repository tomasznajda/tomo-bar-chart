package com.tomasznajda.tomobarchart.label

import android.support.annotation.DrawableRes
import android.view.View
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageResLabel(@DrawableRes val imageResId: Int) : Label {

    override fun render(view: View) = with(view) {
        imgLabel.setImageResource(imageResId)
        imgLabel.visible()
        txtLabel.gone()
    }
}