package com.istech.lighthouse.notification

interface Notification_Const {
    companion object {
        const val title = "title"
        const val message = "message"
        const val type = "type"
        const val ID = "ID"

        //condition types...
        const val JobOrder = "JobOrder"
        const val PaymentSettlement = "PaymentSettlement"
        const val Wallet = "Wallet"
        var TYPE = "TYPE"
        const val topic_base_url = "/topics/"
        const val global_notification_name = "global_notification_topic"
        const val global_notification_topic = topic_base_url + global_notification_name
    }
}
