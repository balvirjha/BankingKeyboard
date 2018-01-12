package com.example.balvirjha.bankingkeyboard.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.example.balvirjha.bankingkeyboard.constants.UTFTexts
import com.example.balvirjha.bankingkeyboard.view.IntroSinglePageView
import com.example.balvirjha.bankingkeyboard.view.RecyclerViewAdapter
import java.util.*

/**
 * Created by BalvirJha on 11-01-2018.
 */
class IntroPagerAdapter(context: Context, pager: ViewPager, keyboardHeight: Int) : PagerAdapter() {
    private val TITLES = arrayOf("firstscreen"/*,
            "secondscreen",
            "thirdscreen"*/)

    private val pager: ViewPager? = null
    private var pages: ArrayList<View>? = null
    private val keyboardHeight: Int = 0

    init {
        this.pages = ArrayList()
        var list1 = ArrayList(Arrays.asList(*UTFTexts.numberTexts))
        val objects = list1.toTypedArray()
        RecyclerViewAdapter(context, Arrays.copyOf<String, Any>(objects, objects.size, Array<String>::class.java))
        pages?.add(IntroSinglePageView(context).getView())

    }

    override fun instantiateItem(container: ViewGroup, position: Int): View? {
        pager?.addView(pages?.get(position), position, keyboardHeight)
        return pages?.get(position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        pager?.removeView(pages?.get(position))
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TITLES[position]
    }

    override fun getCount(): Int {
        return TITLES.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    fun setHeight(height: Int) {
        pager?.getLayoutParams()?.height = height
        pager?.invalidate()
        pager?.requestFocus()
    }
}