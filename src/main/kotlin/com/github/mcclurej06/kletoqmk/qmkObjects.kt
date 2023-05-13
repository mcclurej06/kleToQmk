package com.github.mcclurej06.kletoqmk

import kotlinx.serialization.Serializable

fun Key.toLayoutItem(): LayoutItem {
    return LayoutItem(
        labels[0]!!.split(",").map { it.toInt() },
        x,
        y
    )
}

@Serializable
data class LayoutItem(
    val matrix:List<Int>,
    val x:Double,
    val y:Double,
)