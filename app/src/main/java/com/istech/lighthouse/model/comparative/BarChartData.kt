package com.istech.lighthouse.model.comparative

import com.istech.lighthouse.model.month_wise.MonthWiseData


data class BarChartDataForComparitive(
    val fromData:ArrayList<MonthWiseData>,
    val toData:ArrayList<MonthWiseData>
)