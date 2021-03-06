/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 图片集
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityPhotoPicture implements Serializable {

    private static final long serialVersionUID = 1085870446;

    private Integer   id;
    private Integer   activityPhotoId;
    private String    picture;
    private Timestamp created;
    private Integer   createdBy;
    private Integer   order;

    public ActivityPhotoPicture() {}

    public ActivityPhotoPicture(ActivityPhotoPicture value) {
        this.id = value.id;
        this.activityPhotoId = value.activityPhotoId;
        this.picture = value.picture;
        this.created = value.created;
        this.createdBy = value.createdBy;
        this.order = value.order;
    }

    public ActivityPhotoPicture(
        Integer   id,
        Integer   activityPhotoId,
        String    picture,
        Timestamp created,
        Integer   createdBy,
        Integer   order
    ) {
        this.id = id;
        this.activityPhotoId = activityPhotoId;
        this.picture = picture;
        this.created = created;
        this.createdBy = createdBy;
        this.order = order;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityPhotoId() {
        return this.activityPhotoId;
    }

    public void setActivityPhotoId(Integer activityPhotoId) {
        this.activityPhotoId = activityPhotoId;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ActivityPhotoPicture (");

        sb.append(id);
        sb.append(", ").append(activityPhotoId);
        sb.append(", ").append(picture);
        sb.append(", ").append(created);
        sb.append(", ").append(createdBy);
        sb.append(", ").append(order);

        sb.append(")");
        return sb.toString();
    }
}
