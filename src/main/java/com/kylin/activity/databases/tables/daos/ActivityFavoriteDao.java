/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.daos;


import com.kylin.activity.databases.tables.ActivityFavorite;
import com.kylin.activity.databases.tables.records.ActivityFavoriteRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 我喜欢的活动列表
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
public class ActivityFavoriteDao extends DAOImpl<ActivityFavoriteRecord, com.kylin.activity.databases.tables.pojos.ActivityFavorite, Integer> {

    /**
     * Create a new ActivityFavoriteDao without any configuration
     */
    public ActivityFavoriteDao() {
        super(ActivityFavorite.ACTIVITY_FAVORITE, com.kylin.activity.databases.tables.pojos.ActivityFavorite.class);
    }

    /**
     * Create a new ActivityFavoriteDao with an attached configuration
     */
    @Autowired
    public ActivityFavoriteDao(Configuration configuration) {
        super(ActivityFavorite.ACTIVITY_FAVORITE, com.kylin.activity.databases.tables.pojos.ActivityFavorite.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.kylin.activity.databases.tables.pojos.ActivityFavorite object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.ActivityFavorite> fetchById(Integer... values) {
        return fetch(ActivityFavorite.ACTIVITY_FAVORITE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.ActivityFavorite fetchOneById(Integer value) {
        return fetchOne(ActivityFavorite.ACTIVITY_FAVORITE.ID, value);
    }

    /**
     * Fetch records that have <code>activity_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.ActivityFavorite> fetchByActivityId(Integer... values) {
        return fetch(ActivityFavorite.ACTIVITY_FAVORITE.ACTIVITY_ID, values);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.ActivityFavorite> fetchByUserId(Integer... values) {
        return fetch(ActivityFavorite.ACTIVITY_FAVORITE.USER_ID, values);
    }

    /**
     * Fetch records that have <code>created IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.ActivityFavorite> fetchByCreated(Timestamp... values) {
        return fetch(ActivityFavorite.ACTIVITY_FAVORITE.CREATED, values);
    }
}
