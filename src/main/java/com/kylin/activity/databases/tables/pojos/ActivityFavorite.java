/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class ActivityFavorite implements Serializable {

    private static final long serialVersionUID = 1343161089;

    private Integer   id;
    private Integer   activityId;
    private Integer   userId;
    private Timestamp created;

    public ActivityFavorite() {}

    public ActivityFavorite(ActivityFavorite value) {
        this.id = value.id;
        this.activityId = value.activityId;
        this.userId = value.userId;
        this.created = value.created;
    }

    public ActivityFavorite(
        Integer   id,
        Integer   activityId,
        Integer   userId,
        Timestamp created
    ) {
        this.id = id;
        this.activityId = activityId;
        this.userId = userId;
        this.created = created;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ActivityFavorite (");

        sb.append(id);
        sb.append(", ").append(activityId);
        sb.append(", ").append(userId);
        sb.append(", ").append(created);

        sb.append(")");
        return sb.toString();
    }
}
