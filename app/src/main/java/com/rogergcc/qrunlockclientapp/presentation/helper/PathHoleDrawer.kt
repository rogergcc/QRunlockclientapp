package com.rogergcc.qrunlockclientapp.presentation.helper


/**
 * Created on junio.
 * year 2023 .
 */

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.ViewGroup

class PathHoleDrawer(backgroundColor: Int, holeRadius: Int) :
    HoleDrawer(backgroundColor, holeRadius) {
    private val mPath: Path = Path()
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mWidth = 0
    private var mHeight = 0
    private var mX = 0
    private var mY = 0

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = backgroundColor
    }

    private fun preparePath(width: Int, height: Int, x: Int, y: Int) {
        if (mWidth != width || mHeight != height || mX != x || mY != y) {
            mWidth = width
            mHeight = height
            mX = x
            mY = y
            mPath.reset()
            mPath.fillType = Path.FillType.WINDING
            mPath.addRect(
                0F,
                0F,
                ViewGroup.LayoutParams.MATCH_PARENT.toFloat(),
                ViewGroup.LayoutParams.MATCH_PARENT.toFloat(),
                Path.Direction.CW
            )
            mPath.addCircle(mX.toFloat(), mY.toFloat(), mHoleRadius.toFloat(), Path.Direction.CCW)
        }
    }

    override fun draw(c: Canvas?, width: Int, height: Int, x: Int, y: Int) {
        preparePath(width, height, x, y)
        c!!.drawPath(mPath, mPaint)
    }
}