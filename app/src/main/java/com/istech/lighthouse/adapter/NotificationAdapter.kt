package com.istech.lighthouse.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istech.lighthouse.R
import com.istech.lighthouse.adapter.NotificationAdapter.NotificationHolder
import com.istech.lighthouse.databinding.NotificationLayoutBinding
import com.istech.lighthouse.model.notification.NotificationModel

class NotificationAdapter(mClickOn: ClickedOnCard) : RecyclerView.Adapter<NotificationHolder>() {
    private lateinit var context: Context
    private val clickOn = mClickOn
    public var mList = ArrayList<NotificationModel>()

    interface ClickedOnCard {
        fun cardViewSelected(notificationId: Int)
    }

    class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: NotificationLayoutBinding? = DataBindingUtil.bind(itemView)

        init {
            binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false)
        return NotificationHolder(view)
    }

    fun updateData(list: List<NotificationModel>) {
        mList = list as ArrayList<NotificationModel>
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.binding!!.tvMessage.text = mList[position].message
        holder.binding!!.tvTitle.text = mList[position].title
        holder.binding!!.tvDate.text = mList[position].date

        holder.binding!!.cutNotification.setOnClickListener {
            clickOn.cardViewSelected(mList[position].notificationID)
            holder.binding!!.cardView.visibility = View.GONE
        }
    }
}