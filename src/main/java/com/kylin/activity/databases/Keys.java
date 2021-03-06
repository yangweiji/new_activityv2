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
import com.kylin.activity.databases.tables.MaterialLibrary;
import com.kylin.activity.databases.tables.PayOrder;
import com.kylin.activity.databases.tables.Poster;
import com.kylin.activity.databases.tables.ScoreHistory;
import com.kylin.activity.databases.tables.TrainingCamp;
import com.kylin.activity.databases.tables.User;
import com.kylin.activity.databases.tables.Vercode;
import com.kylin.activity.databases.tables.records.ActionHistoryRecord;
import com.kylin.activity.databases.tables.records.ActivityFavoriteRecord;
import com.kylin.activity.databases.tables.records.ActivityPhotoPictureRecord;
import com.kylin.activity.databases.tables.records.ActivityPhotoRecord;
import com.kylin.activity.databases.tables.records.ActivityRecord;
import com.kylin.activity.databases.tables.records.ActivitySmsRecord;
import com.kylin.activity.databases.tables.records.ActivityTicketRecord;
import com.kylin.activity.databases.tables.records.ActivityUserRecord;
import com.kylin.activity.databases.tables.records.ActivityUserRecordRecord;
import com.kylin.activity.databases.tables.records.ArticleRecord;
import com.kylin.activity.databases.tables.records.CommunityRecord;
import com.kylin.activity.databases.tables.records.CommunityUserRecord;
import com.kylin.activity.databases.tables.records.MaterialLibraryRecord;
import com.kylin.activity.databases.tables.records.PayOrderRecord;
import com.kylin.activity.databases.tables.records.PosterRecord;
import com.kylin.activity.databases.tables.records.ScoreHistoryRecord;
import com.kylin.activity.databases.tables.records.TrainingCampRecord;
import com.kylin.activity.databases.tables.records.UserRecord;
import com.kylin.activity.databases.tables.records.VercodeRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ActionHistoryRecord, Integer> IDENTITY_ACTION_HISTORY = Identities0.IDENTITY_ACTION_HISTORY;
    public static final Identity<ActivityRecord, Integer> IDENTITY_ACTIVITY = Identities0.IDENTITY_ACTIVITY;
    public static final Identity<ActivityFavoriteRecord, Integer> IDENTITY_ACTIVITY_FAVORITE = Identities0.IDENTITY_ACTIVITY_FAVORITE;
    public static final Identity<ActivityPhotoRecord, Integer> IDENTITY_ACTIVITY_PHOTO = Identities0.IDENTITY_ACTIVITY_PHOTO;
    public static final Identity<ActivityPhotoPictureRecord, Integer> IDENTITY_ACTIVITY_PHOTO_PICTURE = Identities0.IDENTITY_ACTIVITY_PHOTO_PICTURE;
    public static final Identity<ActivitySmsRecord, Integer> IDENTITY_ACTIVITY_SMS = Identities0.IDENTITY_ACTIVITY_SMS;
    public static final Identity<ActivityTicketRecord, Integer> IDENTITY_ACTIVITY_TICKET = Identities0.IDENTITY_ACTIVITY_TICKET;
    public static final Identity<ActivityUserRecord, Integer> IDENTITY_ACTIVITY_USER = Identities0.IDENTITY_ACTIVITY_USER;
    public static final Identity<ActivityUserRecordRecord, Integer> IDENTITY_ACTIVITY_USER_RECORD = Identities0.IDENTITY_ACTIVITY_USER_RECORD;
    public static final Identity<ArticleRecord, Integer> IDENTITY_ARTICLE = Identities0.IDENTITY_ARTICLE;
    public static final Identity<CommunityRecord, Integer> IDENTITY_COMMUNITY = Identities0.IDENTITY_COMMUNITY;
    public static final Identity<CommunityUserRecord, Integer> IDENTITY_COMMUNITY_USER = Identities0.IDENTITY_COMMUNITY_USER;
    public static final Identity<MaterialLibraryRecord, Integer> IDENTITY_MATERIAL_LIBRARY = Identities0.IDENTITY_MATERIAL_LIBRARY;
    public static final Identity<PayOrderRecord, Integer> IDENTITY_PAY_ORDER = Identities0.IDENTITY_PAY_ORDER;
    public static final Identity<PosterRecord, Integer> IDENTITY_POSTER = Identities0.IDENTITY_POSTER;
    public static final Identity<ScoreHistoryRecord, Integer> IDENTITY_SCORE_HISTORY = Identities0.IDENTITY_SCORE_HISTORY;
    public static final Identity<TrainingCampRecord, Integer> IDENTITY_TRAINING_CAMP = Identities0.IDENTITY_TRAINING_CAMP;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<VercodeRecord, Integer> IDENTITY_VERCODE = Identities0.IDENTITY_VERCODE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ActionHistoryRecord> KEY_ACTION_HISTORY_PRIMARY = UniqueKeys0.KEY_ACTION_HISTORY_PRIMARY;
    public static final UniqueKey<ActivityRecord> KEY_ACTIVITY_PRIMARY = UniqueKeys0.KEY_ACTIVITY_PRIMARY;
    public static final UniqueKey<ActivityFavoriteRecord> KEY_ACTIVITY_FAVORITE_PRIMARY = UniqueKeys0.KEY_ACTIVITY_FAVORITE_PRIMARY;
    public static final UniqueKey<ActivityPhotoRecord> KEY_ACTIVITY_PHOTO_PRIMARY = UniqueKeys0.KEY_ACTIVITY_PHOTO_PRIMARY;
    public static final UniqueKey<ActivityPhotoPictureRecord> KEY_ACTIVITY_PHOTO_PICTURE_PRIMARY = UniqueKeys0.KEY_ACTIVITY_PHOTO_PICTURE_PRIMARY;
    public static final UniqueKey<ActivitySmsRecord> KEY_ACTIVITY_SMS_PRIMARY = UniqueKeys0.KEY_ACTIVITY_SMS_PRIMARY;
    public static final UniqueKey<ActivityTicketRecord> KEY_ACTIVITY_TICKET_PRIMARY = UniqueKeys0.KEY_ACTIVITY_TICKET_PRIMARY;
    public static final UniqueKey<ActivityUserRecord> KEY_ACTIVITY_USER_PRIMARY = UniqueKeys0.KEY_ACTIVITY_USER_PRIMARY;
    public static final UniqueKey<ActivityUserRecord> KEY_ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID = UniqueKeys0.KEY_ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID;
    public static final UniqueKey<ActivityUserRecordRecord> KEY_ACTIVITY_USER_RECORD_PRIMARY = UniqueKeys0.KEY_ACTIVITY_USER_RECORD_PRIMARY;
    public static final UniqueKey<ActivityUserRecordRecord> KEY_ACTIVITY_USER_RECORD_ACTIVITY_USER_RECORD_UNIQUE_IDX = UniqueKeys0.KEY_ACTIVITY_USER_RECORD_ACTIVITY_USER_RECORD_UNIQUE_IDX;
    public static final UniqueKey<ArticleRecord> KEY_ARTICLE_PRIMARY = UniqueKeys0.KEY_ARTICLE_PRIMARY;
    public static final UniqueKey<CommunityRecord> KEY_COMMUNITY_PRIMARY = UniqueKeys0.KEY_COMMUNITY_PRIMARY;
    public static final UniqueKey<CommunityUserRecord> KEY_COMMUNITY_USER_PRIMARY = UniqueKeys0.KEY_COMMUNITY_USER_PRIMARY;
    public static final UniqueKey<CommunityUserRecord> KEY_COMMUNITY_USER_IDX_COMMUNITY_USER_COMMUNITY_ID_USER_ID = UniqueKeys0.KEY_COMMUNITY_USER_IDX_COMMUNITY_USER_COMMUNITY_ID_USER_ID;
    public static final UniqueKey<MaterialLibraryRecord> KEY_MATERIAL_LIBRARY_PRIMARY = UniqueKeys0.KEY_MATERIAL_LIBRARY_PRIMARY;
    public static final UniqueKey<PayOrderRecord> KEY_PAY_ORDER_PRIMARY = UniqueKeys0.KEY_PAY_ORDER_PRIMARY;
    public static final UniqueKey<PosterRecord> KEY_POSTER_PRIMARY = UniqueKeys0.KEY_POSTER_PRIMARY;
    public static final UniqueKey<ScoreHistoryRecord> KEY_SCORE_HISTORY_PRIMARY = UniqueKeys0.KEY_SCORE_HISTORY_PRIMARY;
    public static final UniqueKey<TrainingCampRecord> KEY_TRAINING_CAMP_PRIMARY = UniqueKeys0.KEY_TRAINING_CAMP_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_UK_USERNAME = UniqueKeys0.KEY_USER_UK_USERNAME;
    public static final UniqueKey<VercodeRecord> KEY_VERCODE_PRIMARY = UniqueKeys0.KEY_VERCODE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<ActionHistoryRecord, Integer> IDENTITY_ACTION_HISTORY = createIdentity(ActionHistory.ACTION_HISTORY, ActionHistory.ACTION_HISTORY.ID);
        public static Identity<ActivityRecord, Integer> IDENTITY_ACTIVITY = createIdentity(Activity.ACTIVITY, Activity.ACTIVITY.ID);
        public static Identity<ActivityFavoriteRecord, Integer> IDENTITY_ACTIVITY_FAVORITE = createIdentity(ActivityFavorite.ACTIVITY_FAVORITE, ActivityFavorite.ACTIVITY_FAVORITE.ID);
        public static Identity<ActivityPhotoRecord, Integer> IDENTITY_ACTIVITY_PHOTO = createIdentity(ActivityPhoto.ACTIVITY_PHOTO, ActivityPhoto.ACTIVITY_PHOTO.ID);
        public static Identity<ActivityPhotoPictureRecord, Integer> IDENTITY_ACTIVITY_PHOTO_PICTURE = createIdentity(ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE, ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ID);
        public static Identity<ActivitySmsRecord, Integer> IDENTITY_ACTIVITY_SMS = createIdentity(ActivitySms.ACTIVITY_SMS, ActivitySms.ACTIVITY_SMS.ID);
        public static Identity<ActivityTicketRecord, Integer> IDENTITY_ACTIVITY_TICKET = createIdentity(ActivityTicket.ACTIVITY_TICKET, ActivityTicket.ACTIVITY_TICKET.ID);
        public static Identity<ActivityUserRecord, Integer> IDENTITY_ACTIVITY_USER = createIdentity(ActivityUser.ACTIVITY_USER, ActivityUser.ACTIVITY_USER.ID);
        public static Identity<ActivityUserRecordRecord, Integer> IDENTITY_ACTIVITY_USER_RECORD = createIdentity(com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD, com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD.ID);
        public static Identity<ArticleRecord, Integer> IDENTITY_ARTICLE = createIdentity(Article.ARTICLE, Article.ARTICLE.ID);
        public static Identity<CommunityRecord, Integer> IDENTITY_COMMUNITY = createIdentity(Community.COMMUNITY, Community.COMMUNITY.ID);
        public static Identity<CommunityUserRecord, Integer> IDENTITY_COMMUNITY_USER = createIdentity(CommunityUser.COMMUNITY_USER, CommunityUser.COMMUNITY_USER.ID);
        public static Identity<MaterialLibraryRecord, Integer> IDENTITY_MATERIAL_LIBRARY = createIdentity(MaterialLibrary.MATERIAL_LIBRARY, MaterialLibrary.MATERIAL_LIBRARY.ID);
        public static Identity<PayOrderRecord, Integer> IDENTITY_PAY_ORDER = createIdentity(PayOrder.PAY_ORDER, PayOrder.PAY_ORDER.ID);
        public static Identity<PosterRecord, Integer> IDENTITY_POSTER = createIdentity(Poster.POSTER, Poster.POSTER.ID);
        public static Identity<ScoreHistoryRecord, Integer> IDENTITY_SCORE_HISTORY = createIdentity(ScoreHistory.SCORE_HISTORY, ScoreHistory.SCORE_HISTORY.ID);
        public static Identity<TrainingCampRecord, Integer> IDENTITY_TRAINING_CAMP = createIdentity(TrainingCamp.TRAINING_CAMP, TrainingCamp.TRAINING_CAMP.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
        public static Identity<VercodeRecord, Integer> IDENTITY_VERCODE = createIdentity(Vercode.VERCODE, Vercode.VERCODE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ActionHistoryRecord> KEY_ACTION_HISTORY_PRIMARY = createUniqueKey(ActionHistory.ACTION_HISTORY, "KEY_action_history_PRIMARY", ActionHistory.ACTION_HISTORY.ID);
        public static final UniqueKey<ActivityRecord> KEY_ACTIVITY_PRIMARY = createUniqueKey(Activity.ACTIVITY, "KEY_activity_PRIMARY", Activity.ACTIVITY.ID);
        public static final UniqueKey<ActivityFavoriteRecord> KEY_ACTIVITY_FAVORITE_PRIMARY = createUniqueKey(ActivityFavorite.ACTIVITY_FAVORITE, "KEY_activity_favorite_PRIMARY", ActivityFavorite.ACTIVITY_FAVORITE.ID);
        public static final UniqueKey<ActivityPhotoRecord> KEY_ACTIVITY_PHOTO_PRIMARY = createUniqueKey(ActivityPhoto.ACTIVITY_PHOTO, "KEY_activity_photo_PRIMARY", ActivityPhoto.ACTIVITY_PHOTO.ID);
        public static final UniqueKey<ActivityPhotoPictureRecord> KEY_ACTIVITY_PHOTO_PICTURE_PRIMARY = createUniqueKey(ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE, "KEY_activity_photo_picture_PRIMARY", ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ID);
        public static final UniqueKey<ActivitySmsRecord> KEY_ACTIVITY_SMS_PRIMARY = createUniqueKey(ActivitySms.ACTIVITY_SMS, "KEY_activity_sms_PRIMARY", ActivitySms.ACTIVITY_SMS.ID);
        public static final UniqueKey<ActivityTicketRecord> KEY_ACTIVITY_TICKET_PRIMARY = createUniqueKey(ActivityTicket.ACTIVITY_TICKET, "KEY_activity_ticket_PRIMARY", ActivityTicket.ACTIVITY_TICKET.ID);
        public static final UniqueKey<ActivityUserRecord> KEY_ACTIVITY_USER_PRIMARY = createUniqueKey(ActivityUser.ACTIVITY_USER, "KEY_activity_user_PRIMARY", ActivityUser.ACTIVITY_USER.ID);
        public static final UniqueKey<ActivityUserRecord> KEY_ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID = createUniqueKey(ActivityUser.ACTIVITY_USER, "KEY_activity_user_idx_activity_user_user_id_activity_id", ActivityUser.ACTIVITY_USER.USER_ID, ActivityUser.ACTIVITY_USER.ACTIVITY_ID);
        public static final UniqueKey<ActivityUserRecordRecord> KEY_ACTIVITY_USER_RECORD_PRIMARY = createUniqueKey(com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD, "KEY_activity_user_record_PRIMARY", com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD.ID);
        public static final UniqueKey<ActivityUserRecordRecord> KEY_ACTIVITY_USER_RECORD_ACTIVITY_USER_RECORD_UNIQUE_IDX = createUniqueKey(com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD, "KEY_activity_user_record_activity_user_record_unique_idx", com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD.ACTIVITY_USER_ID, com.kylin.activity.databases.tables.ActivityUserRecord.ACTIVITY_USER_RECORD.RECORD_TIME);
        public static final UniqueKey<ArticleRecord> KEY_ARTICLE_PRIMARY = createUniqueKey(Article.ARTICLE, "KEY_article_PRIMARY", Article.ARTICLE.ID);
        public static final UniqueKey<CommunityRecord> KEY_COMMUNITY_PRIMARY = createUniqueKey(Community.COMMUNITY, "KEY_community_PRIMARY", Community.COMMUNITY.ID);
        public static final UniqueKey<CommunityUserRecord> KEY_COMMUNITY_USER_PRIMARY = createUniqueKey(CommunityUser.COMMUNITY_USER, "KEY_community_user_PRIMARY", CommunityUser.COMMUNITY_USER.ID);
        public static final UniqueKey<CommunityUserRecord> KEY_COMMUNITY_USER_IDX_COMMUNITY_USER_COMMUNITY_ID_USER_ID = createUniqueKey(CommunityUser.COMMUNITY_USER, "KEY_community_user_idx_community_user_community_id_user_id", CommunityUser.COMMUNITY_USER.COMMUNITY_ID, CommunityUser.COMMUNITY_USER.USER_ID);
        public static final UniqueKey<MaterialLibraryRecord> KEY_MATERIAL_LIBRARY_PRIMARY = createUniqueKey(MaterialLibrary.MATERIAL_LIBRARY, "KEY_material_library_PRIMARY", MaterialLibrary.MATERIAL_LIBRARY.ID);
        public static final UniqueKey<PayOrderRecord> KEY_PAY_ORDER_PRIMARY = createUniqueKey(PayOrder.PAY_ORDER, "KEY_pay_order_PRIMARY", PayOrder.PAY_ORDER.ID);
        public static final UniqueKey<PosterRecord> KEY_POSTER_PRIMARY = createUniqueKey(Poster.POSTER, "KEY_poster_PRIMARY", Poster.POSTER.ID);
        public static final UniqueKey<ScoreHistoryRecord> KEY_SCORE_HISTORY_PRIMARY = createUniqueKey(ScoreHistory.SCORE_HISTORY, "KEY_score_history_PRIMARY", ScoreHistory.SCORE_HISTORY.ID);
        public static final UniqueKey<TrainingCampRecord> KEY_TRAINING_CAMP_PRIMARY = createUniqueKey(TrainingCamp.TRAINING_CAMP, "KEY_training_camp_PRIMARY", TrainingCamp.TRAINING_CAMP.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserRecord> KEY_USER_UK_USERNAME = createUniqueKey(User.USER, "KEY_user_uk_username", User.USER.USERNAME);
        public static final UniqueKey<VercodeRecord> KEY_VERCODE_PRIMARY = createUniqueKey(Vercode.VERCODE, "KEY_vercode_PRIMARY", Vercode.VERCODE.ID);
    }
}
