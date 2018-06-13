package com.kylin.activity.model

data class ActivityAttendInfo (var title: String? = null,
                               var type: String? = null,
                               var required: Boolean = false,
                               var multiple: Boolean = false,
                               var options: List<ActivityAttendInfoOption>? = null, var value: Any? = null)

data class ActivityAttendInfoOption(var title: String? = null)

data class ActivityScoreInfo(var generalUserScore:Int = 0, var vipUserScore:Int = 0)