package com.tomasznajda.tomobarchart.label

import android.graphics.Bitmap
import android.view.View
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageBitmapLabel(val bitmap: Bitmap) : Label {

    override fun render(view: View) = with(view) {
        imgLabel.setImageBitmap(bitmap)
        imgLabel.visible()
        txtLabel.gone()
    }
}