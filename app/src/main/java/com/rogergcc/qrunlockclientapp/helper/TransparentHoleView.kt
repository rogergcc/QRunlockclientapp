package com.rogergcc.qrunlockclientapp.helper

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created on junio.
 * year 2023 .
 */
class TransparentHoleView : View {

    private val holePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR).apply {
            color = 0x80000000.toInt()
        }
    }

    private val path = Path()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val holeSize = width.coerceAtMost(height) * 0.75f // Adjust the size as needed

        // Calculate the position of the hole
        val holeLeft = (width - holeSize) / 2
        val holeTop = (height - holeSize) / 2
        val holeRight = holeLeft + holeSize
        val holeBottom = holeTop + holeSize

        // Draw the transparent hole
        path.reset()
        path.fillType = Path.FillType.WINDING
        path.addRect(0f, 0f, width, height, Path.Direction.CW)
        path.addRect(holeLeft, holeTop, holeRight, holeBottom, Path.Direction.CCW)
        canvas.drawPath(path, holePaint)
    }
}