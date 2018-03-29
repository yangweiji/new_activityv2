/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.daos;


import com.kylin.activity.databases.tables.Activity;
import com.kylin.activity.databases.tables.records.ActivityRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 活动
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class ActivityDao extends DAOImpl<ActivityRecord, com.kylin.activity.databases.tables.pojos.Activity, Integer> {

    /**
     * Create a new ActivityDao without any configuration
     */
    public ActivityDao() {
        super(Activity.ACTIVITY_, com.kylin.activity.databases.tables.pojos.Activity.class);
    }

    /**
     * Create a new ActivityDao with an attached configuration
     */
    @Autowired
    public ActivityDao(Configuration configuration) {
        super(Activity.ACTIVITY_, com.kylin.activity.databases.tables.pojos.Activity.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.kylin.activity.databases.tables.pojos.Activity object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchById(Integer... values) {
        return fetch(Activity.ACTIVITY_.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.Activity fetchOneById(Integer value) {
        return fetchOne(Activity.ACTIVITY_.ID, value);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByTitle(String... values) {
        return fetch(Activity.ACTIVITY_.TITLE, values);
    }

    /**
     * Fetch records that have <code>avatar IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByAvatar(String... values) {
        return fetch(Activity.ACTIVITY_.AVATAR, values);
    }

    /**
     * Fetch records that have <code>summary IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchBySummary(String... values) {
        return fetch(Activity.ACTIVITY_.SUMMARY, values);
    }

    /**
     * Fetch records that have <code>body IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByBody(String... values) {
        return fetch(Activity.ACTIVITY_.BODY, values);
    }

    /**
     * Fetch records that have <code>unit IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByUnit(String... values) {
        return fetch(Activity.ACTIVITY_.UNIT, values);
    }

    /**
     * Fetch records that have <code>tags IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByTags(String... values) {
        return fetch(Activity.ACTIVITY_.TAGS, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByStatus(Integer... values) {
        return fetch(Activity.ACTIVITY_.STATUS, values);
    }

    /**
     * Fetch records that have <code>start_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByStartTime(Timestamp... values) {
        return fetch(Activity.ACTIVITY_.START_TIME, values);
    }

    /**
     * Fetch records that have <code>end_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByEndTime(Timestamp... values) {
        return fetch(Activity.ACTIVITY_.END_TIME, values);
    }

    /**
     * Fetch records that have <code>attend_due_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByAttendDueTime(Timestamp... values) {
        return fetch(Activity.ACTIVITY_.ATTEND_DUE_TIME, values);
    }

    /**
     * Fetch records that have <code>created IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByCreated(Timestamp... values) {
        return fetch(Activity.ACTIVITY_.CREATED, values);
    }

    /**
     * Fetch records that have <code>created_by IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByCreatedBy(Integer... values) {
        return fetch(Activity.ACTIVITY_.CREATED_BY, values);
    }

    /**
     * Fetch records that have <code>modified IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByModified(Timestamp... values) {
        return fetch(Activity.ACTIVITY_.MODIFIED, values);
    }

    /**
     * Fetch records that have <code>modified_by IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByModifiedBy(Integer... values) {
        return fetch(Activity.ACTIVITY_.MODIFIED_BY, values);
    }

    /**
     * Fetch records that have <code>attend_infos IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByAttendInfos(String... values) {
        return fetch(Activity.ACTIVITY_.ATTEND_INFOS, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByAddress(String... values) {
        return fetch(Activity.ACTIVITY_.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>coordinate IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByCoordinate(String... values) {
        return fetch(Activity.ACTIVITY_.COORDINATE, values);
    }

    /**
     * Fetch records that have <code>activity_type IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByActivityType(Integer... values) {
        return fetch(Activity.ACTIVITY_.ACTIVITY_TYPE, values);
    }

    /**
     * Fetch records that have <code>public IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByPublic(Boolean... values) {
        return fetch(Activity.ACTIVITY_.PUBLIC, values);
    }

    /**
     * Fetch records that have <code>score_infos IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByScoreInfos(String... values) {
        return fetch(Activity.ACTIVITY_.SCORE_INFOS, values);
    }

    /**
     * Fetch records that have <code>community_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Activity> fetchByCommunityId(Integer... values) {
        return fetch(Activity.ACTIVITY_.COMMUNITY_ID, values);
    }
}
