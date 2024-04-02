package com.istech.lighthouse.adapter
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istech.lighthouse.R
import com.istech.lighthouse.adapter.RecentPaymentAdapter.*
import com.istech.lighthouse.databinding.RecentsPaymentsLayoutBinding
import com.istech.lighthouse.model.recharge.RecentPaymentModel
class RecentPaymentAdapter : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var context: Context
    public var mList = ArrayList<RecentPaymentModel>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RecentsPaymentsLayoutBinding? = DataBindingUtil.bind(itemView)

        init {
            binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recents_payments_layout, parent, false)
        return ViewHolder(view)
    }

    fun updateData(list: List<RecentPaymentModel>) {
        mList = list as ArrayList<RecentPaymentModel>
        notifyDataSetChanged()
    }

    fun loadMore(data: List<RecentPaymentModel>) {
        mList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      /*  when (position) {
            1 -> {*/
                holder.binding!!.tvPaymentStatus.text = mList[position].status
                holder.binding!!.tvPrice.text = "₹"+mList[position].amount
                holder.binding!!.tvDate.text = mList[position].rechargeDate
                holder.binding!!.tvTransactionId.text = mList[position].transactionID
//                holder.binding!!.tvCustId.text = mList[position].consumerID
            if (mList[position].status.equals("Success")){
                holder.binding!!.mainCard.setCardBackgroundColor(context.resources.getColor(R.color.white))
                holder.binding!!.tvPaymentStatus.setTextColor(context.resources.getColor(R.color.green))
            }else {
                holder.binding!!.tvPaymentStatus.setTextColor(context.resources.getColor(R.color.red))
                holder.binding!!.mainCard.setCardBackgroundColor(Color.parseColor("#fcc1c1"))
            }
           // }

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}