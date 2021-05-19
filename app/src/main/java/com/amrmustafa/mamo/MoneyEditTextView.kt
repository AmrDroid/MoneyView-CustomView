package com.amrmustafa.mamo

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

class MoneyEditTextView : RelativeLayout {

    val df = DecimalFormat("#.##")
    var tvAmount: TextView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        df.isGroupingUsed = true
        df.groupingSize = 3

        var inflate = LayoutInflater.from(context)
        var viewGroup = inflate.inflate(R.layout.layout_edittext_money, this)
        tvAmount = viewGroup.findViewById(R.id.tv_money)
        tvAmount?.setHint("0.00")
    }
    fun addText(s: String) {
        var pattern = "^[0-9]*\$"
        if (!Pattern.matches(pattern, s)) {
            return
        }
        if (tvAmount?.text?.length!! > 11) {
            return
        }

        var temp = 0.0
        var sum = 0.0

        if (TextUtils.isEmpty(tvAmount?.text.toString())) {
            if (TextUtils.equals(s, "0")) return
            sum = s.toDouble() / 100

            val value = sum.toString().replace(",", "")+s
            tvAmount?.setText(df.format(value.toDouble()))
            return
        }
        temp = tvAmount?.text.toString().replace(",", "").toDouble() * 10
        sum = temp + s.toDouble() / 100

        val value = sum.toString().replace(",", "")+s
        tvAmount?.setText(df.format(value.toDouble()))

    }
    fun deleteText() {
        if (TextUtils.isEmpty(tvAmount?.text) or TextUtils.equals(tvAmount?.text, "0.00")) {
            return
        }

        var temp = tvAmount?.text.toString().replace(",","").replace(".", "")
        var sub = temp.substring(0, temp.length - 1)
        if (TextUtils.isEmpty(sub) || TextUtils.equals(sub, "00")) {
            tvAmount?.setText("")
        } else {
            tvAmount?.setText(df.format(sub.toDouble() / 100))
        }

    }

    fun clear() {
        tvAmount?.setText("")
    }

    fun getAmount(): String {
        return tvAmount?.text.toString().trim()
    }

}