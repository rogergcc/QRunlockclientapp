package com.rogergcc.qrunlockclientapp.ui.helper


/**
 * Created on junio.
 * year 2023 .
 */

import android.graphics.Canvas

abstract class HoleDrawer(backgroundColor: Int, holeRadius: Int) {
    protected var mBackgroundColor: Int
    protected var mHoleRadius: Int

    init {
        mBackgroundColor = backgroundColor
        mHoleRadius = holeRadius
    }

    abstract fun draw(c: Canvas?, width: Int, height: Int, x: Int, y: Int)
}