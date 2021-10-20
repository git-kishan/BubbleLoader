package com.kk.bubbleloader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator

class BubbleLoader(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val FIRST_BUBBLE = 0
    private val SECOND_BUBBLE = 1
    private val THIRD_BUBBLE = 2
    private val DELAY = 200
    private val NO_DELAY = 0

    private val VIEW_PADDING = 8F
    private val DEFAULT_ANIMATION_DURATION = 800
    private val FROM = 1F
    private val TO = 0.1F

    private var bubblePaint: Paint
    private var bubbleColor: Int = 0
    private var viewWidth = 0
    private var viewHeight = 0
    private var animatorList: List<ValueAnimator>
    private var animationDuration = 0L

    private var radius = 0F
    private var firstBubbleAnimatedRadius = 0F
    private var secondBubbleAnimatedRadius = 0F
    private var thirdBubbleAnimatedRadius = 0F


    init {

        context?.theme?.obtainStyledAttributes(
            attrs, R.styleable.BubbleLoader, 0, 0
        )?.apply {
            try {
                bubbleColor = getColor(R.styleable.BubbleLoader_color, Color.BLACK)
                animationDuration =
                    getInteger(
                        R.styleable.BubbleLoader_duration_cycle,
                        DEFAULT_ANIMATION_DURATION
                    ).toLong()
            } finally {
                recycle()
            }
        }

        bubblePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = bubbleColor!!
            setAlpha(1f)
            style = Paint.Style.FILL_AND_STROKE
        }


        animatorList = listOf(
            ValueAnimator.ofFloat(FROM, TO),
            ValueAnimator.ofFloat(FROM, TO),
            ValueAnimator.ofFloat(FROM, TO)
        )

        animatorList.forEachIndexed { index, valueAnimator ->
            valueAnimator.apply {
                addUpdateListener {
                    when (index) {
                        FIRST_BUBBLE -> firstBubbleAnimatedRadius = it.animatedValue as Float
                        SECOND_BUBBLE -> secondBubbleAnimatedRadius = it.animatedValue as Float
                        THIRD_BUBBLE -> thirdBubbleAnimatedRadius = it.animatedValue as Float
                    }
                    invalidate()
                }
                duration = animationDuration
                interpolator = AccelerateInterpolator()
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                startDelay = when (index) {
                    FIRST_BUBBLE -> NO_DELAY
                    SECOND_BUBBLE -> DELAY
                    THIRD_BUBBLE -> DELAY.times(2)
                    else -> DELAY
                }.toLong()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animatorList.forEach { it.start() }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorList.forEach {
            it.removeAllUpdateListeners()
            it.removeAllListeners()
            it.cancel()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        radius = (viewWidth - VIEW_PADDING.times(4)).div(6)
        setMeasuredDimension(viewWidth, viewHeight)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawCircle(it)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(
            VIEW_PADDING + radius,
            viewHeight.div(2).toFloat(),
            radius * firstBubbleAnimatedRadius, bubblePaint
        )

        canvas.drawCircle(
            VIEW_PADDING.times(2) + radius.times(3),
            viewHeight.div(2).toFloat(),
            radius * secondBubbleAnimatedRadius,
            bubblePaint
        )

        canvas.drawCircle(
            VIEW_PADDING.times(3) + radius.times(5),
            viewHeight.div(2).toFloat(),
            radius * thirdBubbleAnimatedRadius,
            bubblePaint
        )
    }
}