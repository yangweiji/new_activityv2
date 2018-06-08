/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases;


import com.kylin.activity.databases.tables.ActionHistory;
import com.kylin.activity.databases.tables.Activity;
import com.kylin.activity.databases.tables.ActivityFavorite;
import com.kylin.activity.databases.tables.ActivityPhoto;
import com.kylin.activity.databases.tables.ActivityPhotoPicture;
import com.kylin.activity.databases.tables.ActivitySms;
import com.kylin.activity.databases.tables.ActivityTicket;
import com.kylin.activity.databases.tables.ActivityUser;
import com.kylin.activity.databases.tables.Article;
import com.kylin.activity.databases.tables.Community;
import com.kylin.activity.databases.tables.CommunityUser;
import com.kylin.activity.databases.tables.PayOrder;
import com.kylin.activity.databases.tables.ScoreHistory;
import com.kylin.activity.databases.tables.User;
import com.kylin.activity.databases.tables.Vercode;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in activityv2
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>activityv2.action_history</code>.
     */
    public static final ActionHistory ACTION_HISTORY = com.kylin.activity.databases.tables.ActionHistory.ACTION_HISTORY;

    /**
     * 活动
     */
    public static final Activity ACTIVITY = com.kylin.activity.databases.tables.Activity.ACTIVITY;

    /**
     * 我喜欢的活动列表
     */
    public static final ActivityFavorite ACTIVITY_FAVORITE = com.kylin.activity.databases.tables.ActivityFavorite.ACTIVITY_FAVORITE;

    /**
     * 活动相册
     */
    public static final ActivityPhoto ACTIVITY_PHOTO = com.kylin.activity.databases.tables.ActivityPhoto.ACTIVITY_PHOTO;

    /**
     * 图片集
     */
    public static final ActivityPhotoPicture ACTIVITY_PHOTO_PICTURE = com.kylin.activity.databases.tables.ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE;

    /**
     * 短信
     */
    public static final ActivitySms ACTIVITY_SMS = com.kylin.activity.databases.tables.ActivitySms.ACTIVITY_SMS;

    /**
     * 活动门票
     */
    public static final ActivityTicket ACTIVITY_TICKET = com.kylin.activity.databases.tables.ActivityTicket.ACTIVITY_TICKET;

    /**
     * 参与活动人
     */
    public static final ActivityUser ACTIVITY_USER = com.kylin.activity.databases.tables.ActivityUser.ACTIVITY_USER;

    /**
     * 文章 内容发布
     */
    public static final Article ARTICLE = com.kylin.activity.databases.tables.Article.ARTICLE;

    /**
     * 社团
     */
    public static final Community COMMUNITY = com.kylin.activity.databases.tables.Community.COMMUNITY;

    /**
     * 社团成员
     */
    public static final CommunityUser COMMUNITY_USER = com.kylin.activity.databases.tables.CommunityUser.COMMUNITY_USER;

    /**
     * 付款订单
     */
    public static final PayOrder PAY_ORDER = com.kylin.activity.databases.tables.PayOrder.PAY_ORDER;

    /**
     * 积分历史
     */
    public static final ScoreHistory SCORE_HISTORY = com.kylin.activity.databases.tables.ScoreHistory.SCORE_HISTORY;

    /**
     * 用户表
     */
    public static final User USER = com.kylin.activity.databases.tables.User.USER;

    /**
     * 验证码
     */
    public static final Vercode VERCODE = com.kylin.activity.databases.tables.Vercode.VERCODE;
}
