/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases;


import com.kylin.activity.databases.tables.*;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.AbstractKeys;

import javax.annotation.Generated;


/**
 * A class modelling indexes of tables of the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACTION_HISTORY_PRIMARY = Indexes0.ACTION_HISTORY_PRIMARY;
    public static final Index ACTIVITY_PRIMARY = Indexes0.ACTIVITY_PRIMARY;
    public static final Index ACTIVITY_FAVORITE_PRIMARY = Indexes0.ACTIVITY_FAVORITE_PRIMARY;
    public static final Index ACTIVITY_PHOTO_PRIMARY = Indexes0.ACTIVITY_PHOTO_PRIMARY;
    public static final Index ACTIVITY_PHOTO_PICTURE_PRIMARY = Indexes0.ACTIVITY_PHOTO_PICTURE_PRIMARY;
    public static final Index ACTIVITY_SMS_PRIMARY = Indexes0.ACTIVITY_SMS_PRIMARY;
    public static final Index ACTIVITY_TICKET_PRIMARY = Indexes0.ACTIVITY_TICKET_PRIMARY;
    public static final Index ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID = Indexes0.ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID;
    public static final Index ACTIVITY_USER_PRIMARY = Indexes0.ACTIVITY_USER_PRIMARY;
    public static final Index ACTIVITY_USER_RECORD_PRIMARY = Indexes0.ACTIVITY_USER_RECORD_PRIMARY;
    public static final Index ARTICLE_PRIMARY = Indexes0.ARTICLE_PRIMARY;
    public static final Index COMMUNITY_PRIMARY = Indexes0.COMMUNITY_PRIMARY;
    public static final Index COMMUNITY_USER_PRIMARY = Indexes0.COMMUNITY_USER_PRIMARY;
    public static final Index PAY_ORDER_PRIMARY = Indexes0.PAY_ORDER_PRIMARY;
    public static final Index POSTER_PRIMARY = Indexes0.POSTER_PRIMARY;
    public static final Index SCORE_HISTORY_PRIMARY = Indexes0.SCORE_HISTORY_PRIMARY;
    public static final Index USER_PRIMARY = Indexes0.USER_PRIMARY;
    public static final Index USER_UK_USERNAME = Indexes0.USER_UK_USERNAME;
    public static final Index VERCODE_PRIMARY = Indexes0.VERCODE_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 extends AbstractKeys {
        public static Index ACTION_HISTORY_PRIMARY = createIndex("PRIMARY", ActionHistory.ACTION_HISTORY, new OrderField[] { ActionHistory.ACTION_HISTORY.ID }, true);
        public static Index ACTIVITY_PRIMARY = createIndex("PRIMARY", Activity.ACTIVITY, new OrderField[] { Activity.ACTIVITY.ID }, true);
        public static Index ACTIVITY_FAVORITE_PRIMARY = createIndex("PRIMARY", ActivityFavorite.ACTIVITY_FAVORITE, new OrderField[] { ActivityFavorite.ACTIVITY_FAVORITE.ID }, true);
        public static Index ACTIVITY_PHOTO_PRIMARY = createIndex("PRIMARY", ActivityPhoto.ACTIVITY_PHOTO, new OrderField[] { ActivityPhoto.ACTIVITY_PHOTO.ID }, true);
        public static Index ACTIVITY_PHOTO_PICTURE_PRIMARY = createIndex("PRIMARY", ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE, new OrderField[] { ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ID }, true);
        public static Index ACTIVITY_SMS_PRIMARY = createIndex("PRIMARY", ActivitySms.ACTIVITY_SMS, new OrderField[] { ActivitySms.ACTIVITY_SMS.ID }, true);
        public static Index ACTIVITY_TICKET_PRIMARY = createIndex("PRIMARY", ActivityTicket.ACTIVITY_TICKET, new OrderField[] { ActivityTicket.ACTIVITY_TICKET.ID }, true);
        public static Index ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID = createIndex("idx_activity_user_user_id_activity_id", ActivityUser.ACTIVITY_USER, new OrderField[] { ActivityUser.ACTIVITY_USER.USER_ID, ActivityUser.ACTIVITY_USER.ACTIVITY_ID }, true);
        public static Index ACTIVITY_USER_PRIMARY = createIndex("PRIMARY", ActivityUser.ACTIVITY_USER, new OrderField[] { ActivityUser.ACTIVITY_USER.ID }, true);
        public static Index ACTIVITY_USER_RECORD_PRIMARY = createIndex("PRIMARY", ActivityUserRecord.ACTIVITY_USER_RECORD, new OrderField[] { ActivityUserRecord.ACTIVITY_USER_RECORD.ID }, true);
        public static Index ARTICLE_PRIMARY = createIndex("PRIMARY", Article.ARTICLE, new OrderField[] { Article.ARTICLE.ID }, true);
        public static Index COMMUNITY_PRIMARY = createIndex("PRIMARY", Community.COMMUNITY, new OrderField[] { Community.COMMUNITY.ID }, true);
        public static Index COMMUNITY_USER_PRIMARY = createIndex("PRIMARY", CommunityUser.COMMUNITY_USER, new OrderField[] { CommunityUser.COMMUNITY_USER.ID }, true);
        public static Index PAY_ORDER_PRIMARY = createIndex("PRIMARY", PayOrder.PAY_ORDER, new OrderField[] { PayOrder.PAY_ORDER.ID }, true);
        public static Index POSTER_PRIMARY = createIndex("PRIMARY", Poster.POSTER, new OrderField[] { Poster.POSTER.ID }, true);
        public static Index SCORE_HISTORY_PRIMARY = createIndex("PRIMARY", ScoreHistory.SCORE_HISTORY, new OrderField[] { ScoreHistory.SCORE_HISTORY.ID }, true);
        public static Index USER_PRIMARY = createIndex("PRIMARY", User.USER, new OrderField[] { User.USER.ID }, true);
        public static Index USER_UK_USERNAME = createIndex("uk_username", User.USER, new OrderField[] { User.USER.USERNAME }, true);
        public static Index VERCODE_PRIMARY = createIndex("PRIMARY", Vercode.VERCODE, new OrderField[] { Vercode.VERCODE.ID }, true);
    }
}
