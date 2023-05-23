package com.alexp.financialsticker.util

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo
import com.alexp.financialsticker.R
import com.alexp.financialsticker.ui.stockgrid.adapters.CompanyStockGridRVAdapter


class RVCompanyStockItemAnimator: DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        val holder = newHolder as CompanyStockGridRVAdapter.Holder
        val myResources = holder.itemView.context.resources

        if (preInfo is CompanyStockItemHolderInfo){
            var initialColor = 0
            initialColor = if (preInfo.isIncreased){
                R.color.postiveGreen
            } else {
                R.color.red
            }

            val changeColorFade: ObjectAnimator = ObjectAnimator.ofObject(
                holder.binding.tvChange,
                "textColor" /*view attribute name*/,
                ArgbEvaluator(),
                myResources.getColor(initialColor, null) /*from color*/,
                myResources.getColor(R.color.white, null) /*to color*/
            )
            changeColorFade.duration = 2000
            changeColorFade.start()

            val changePercentColorFade: ObjectAnimator = ObjectAnimator.ofObject(
                holder.binding.tvChangePercent,
                "textColor" /*view attribute name*/,
                ArgbEvaluator(),
                myResources.getColor(initialColor, null) /*from color*/,
                myResources.getColor(R.color.white, null) /*to color*/
            )
            changePercentColorFade.duration = 2000
            changePercentColorFade.start()

            val priceColorFade: ObjectAnimator = ObjectAnimator.ofObject(
                holder.binding.tvPrice,
                "textColor" /*view attribute name*/,
                ArgbEvaluator(),
                myResources.getColor(initialColor, null) /*from color*/,
                myResources.getColor(R.color.white, null) /*to color*/
            )
            priceColorFade.duration = 2000
            priceColorFade.start()
            return true
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ): Boolean {
        return true
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        if (changeFlags == FLAG_CHANGED){
            for (payload in payloads){
                return if(payload as? Boolean == true){
                    CompanyStockItemHolderInfo(true)
                } else{
                    CompanyStockItemHolderInfo(false)
                }
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }
}

class CompanyStockItemHolderInfo(val isIncreased: Boolean): ItemHolderInfo()