package com.rogergcc.qrunlockclientapp.presentation.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.rogergcc.qrunlockclientapp.R
import java.util.concurrent.atomic.AtomicBoolean

fun View.onThrottledClick(
    throttleDelay: Long = 500L,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}

fun Activity.snackbar(msg: String, action: (() -> Unit)? = null) {
    Snackbar.make(
        findViewById(android.R.id.content),
        msg,
        Snackbar.LENGTH_LONG
    ).also {
        it.setAction(
            getString(R.string.ok)
        ) { action?.invoke() }
    }.show()
}


fun View.slideUp(duration: Long = 250L) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.interpolator = AccelerateInterpolator()
    animate.duration = duration
    animate.fillAfter = true


    this.startAnimation(animate)
}

fun View.slideDown(duration: Long = 250L) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.interpolator = AccelerateInterpolator()
    animate.duration = duration
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}