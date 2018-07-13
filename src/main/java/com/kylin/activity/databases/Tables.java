/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases;


import com.kylin.activity.databases.tables.*;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in 
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
     * 操作记录表
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
     * 用户报名活动打卡记录表
     */
    public static final ActivityUserRecord ACTIVITY_USER_RECORD = com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD;

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
     * 海报表
     */
    public static final Poster POSTER = com.kylin.activity.databases.tables.Poster.POSTER;

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
