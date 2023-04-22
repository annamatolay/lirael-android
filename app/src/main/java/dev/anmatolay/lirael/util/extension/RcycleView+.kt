package dev.anmatolay.lirael.util.extension

import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.anmatolay.lirael.R

fun RecyclerView.setLayoutManagerAndItemDecoration(orientation: Int) {
    layoutManager = LinearLayoutManager(this.context, orientation, false)
    addItemDecoration(
        DividerItemDecoration(
            this.context,
            orientation
        ).apply {
            setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.item_divider, null)!!)
        }
    )
}
