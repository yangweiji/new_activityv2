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
import com.kylin.activity.databases.tables.ActivityUserRecord;
import com.kylin.activity.databases.tables.Article;
import com.kylin.activity.databases.tables.Community;
import com.kylin.activity.databases.tables.CommunityUser;
import com.kylin.activity.databases.tables.MaterialLibrary;
import com.kylin.activity.databases.tables.PayOrder;
import com.kylin.activity.databases.tables.Poster;
import com.kylin.activity.databases.tables.ScoreHistory;
import com.kylin.activity.databases.tables.TrainingCamp;
import com.kylin.activity.databases.tables.User;
import com.kylin.activity.databases.tables.VTrainingcamppeople;
import com.kylin.activity.databases.tables.Vercode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1070102313;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * 操作记录表
     */
    public final ActionHistory ACTION_HISTORY = com.kylin.activity.databases.tables.ActionHistory.ACTION_HISTORY;

    /**
     * 活动
     */
    public final Activity ACTIVITY = com.kylin.activity.databases.tables.Activity.ACTIVITY;

    /**
     * 我喜欢的活动列表
     */
    public final ActivityFavorite ACTIVITY_FAVORITE = com.kylin.activity.databases.tables.ActivityFavorite.ACTIVITY_FAVORITE;

    /**
     * 活动相册
     */
    public final ActivityPhoto ACTIVITY_PHOTO = com.kylin.activity.databases.tables.ActivityPhoto.ACTIVITY_PHOTO;

    /**
     * 图片集
     */
    public final ActivityPhotoPicture ACTIVITY_PHOTO_PICTURE = com.kylin.activity.databases.tables.ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE;

    /**
     * 短信
     */
    public final ActivitySms ACTIVITY_SMS = com.kylin.activity.databases.tables.ActivitySms.ACTIVITY_SMS;

    /**
     * 活动门票
     */
    public final ActivityTicket ACTIVITY_TICKET = com.kylin.activity.databases.tables.ActivityTicket.ACTIVITY_TICKET;

    /**
     * 参与活动人
     */
    public final ActivityUser ACTIVITY_USER = com.kylin.activity.databases.tables.ActivityUser.ACTIVITY_USER;

    /**
     * 用户报名活动打卡记录表
     */
    public final ActivityUserRecord ACTIVITY_USER_RECORD = com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD;

    /**
     * 文章 内容发布
     */
    public final Article ARTICLE = com.kylin.activity.databases.tables.Article.ARTICLE;

    /**
     * 社团
     */
    public final Community COMMUNITY = com.kylin.activity.databases.tables.Community.COMMUNITY;

    /**
     * 社团成员
     */
    public final CommunityUser COMMUNITY_USER = com.kylin.activity.databases.tables.CommunityUser.COMMUNITY_USER;

    /**
     * 素材库
     */
    public final MaterialLibrary MATERIAL_LIBRARY = com.kylin.activity.databases.tables.MaterialLibrary.MATERIAL_LIBRARY;

    /**
     * 付款订单
     */
    public final PayOrder PAY_ORDER = com.kylin.activity.databases.tables.PayOrder.PAY_ORDER;

    /**
     * 海报表
     */
    public final Poster POSTER = com.kylin.activity.databases.tables.Poster.POSTER;

    /**
     * 积分历史
     */
    public final ScoreHistory SCORE_HISTORY = com.kylin.activity.databases.tables.ScoreHistory.SCORE_HISTORY;

    /**
     * 训练营
     */
    public final TrainingCamp TRAINING_CAMP = com.kylin.activity.databases.tables.TrainingCamp.TRAINING_CAMP;

    /**
     * 用户表
     */
    public final User USER = com.kylin.activity.databases.tables.User.USER;

    /**
     * 验证码
     */
    public final Vercode VERCODE = com.kylin.activity.databases.tables.Vercode.VERCODE;

    /**
     * VIEW
     */
    public final VTrainingcamppeople V_TRAININGCAMPPEOPLE = com.kylin.activity.databases.tables.VTrainingcamppeople.V_TRAININGCAMPPEOPLE;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            ActionHistory.ACTION_HISTORY,
            Activity.ACTIVITY,
            ActivityFavorite.ACTIVITY_FAVORITE,
            ActivityPhoto.ACTIVITY_PHOTO,
            ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE,
            ActivitySms.ACTIVITY_SMS,
            ActivityTicket.ACTIVITY_TICKET,
            ActivityUser.ACTIVITY_USER,
            ActivityUserRecord.ACTIVITY_USER_RECORD,
            Article.ARTICLE,
            Community.COMMUNITY,
            CommunityUser.COMMUNITY_USER,
            MaterialLibrary.MATERIAL_LIBRARY,
            PayOrder.PAY_ORDER,
            Poster.POSTER,
            ScoreHistory.SCORE_HISTORY,
            TrainingCamp.TRAINING_CAMP,
            User.USER,
            Vercode.VERCODE,
            VTrainingcamppeople.V_TRAININGCAMPPEOPLE);
    }
}
