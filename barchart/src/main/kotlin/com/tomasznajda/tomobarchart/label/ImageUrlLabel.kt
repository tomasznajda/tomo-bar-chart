package com.tomasznajda.tomobarchart.label

import android.view.View
import com.squareup.picasso.Picasso
import com.tomasznajda.ktx.android.gone
import com.tomasznajda.ktx.android.visible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageUrlLabel(val url: String) : Label {

    override fun render(view: View) = with(view) {
        Picasso.get().load(url).into(imgLabel)
        imgLabel.visible()
        txtLabel.gone()
    }
}