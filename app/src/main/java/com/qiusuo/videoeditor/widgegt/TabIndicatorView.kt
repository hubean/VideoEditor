package com.qiusuo.videoeditor.widgegt

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout
import com.qiusuo.videoeditor.databinding.ViewTabIndicatorBinding

class TabIndicatorView @JvmOverloads constructor(context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs) {
    val viewBinding = ViewTabIndicatorBinding.inflate(LayoutInflater.from(context), this, true)
}
