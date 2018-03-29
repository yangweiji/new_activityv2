/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Activity implements Serializable {

    private static final long serialVersionUID = -468386636;

    private Integer   id;
    private String    title;
    private String    avatar;
    private String    summary;
    private String    body;
    private String    unit;
    private String    tags;
    private Integer   status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp attendDueTime;
    private Timestamp created;
    private Integer   createdBy;
    private Timestamp modified;
    private Integer   modifiedBy;
    private String    attendInfos;
    private String    address;
    private String    coordinate;
    private Integer   activityType;
    private Boolean   public_;
    private String    scoreInfos;
    private Integer   communityId;

    public Activity() {}

    public Activity(Activity value) {
        this.id = value.id;
        this.title = value.title;
        this.avatar = value.avatar;
        this.summary = value.summary;
        this.body = value.body;
        this.unit = value.unit;
        this.tags = value.tags;
        this.status = value.status;
        this.startTime = value.startTime;
        this.endTime = value.endTime;
        this.attendDueTime = value.attendDueTime;
        this.created = value.created;
        this.createdBy = value.createdBy;
        this.modified = value.modified;
        this.modifiedBy = value.modifiedBy;
        this.attendInfos = value.attendInfos;
        this.address = value.address;
        this.coordinate = value.coordinate;
        this.activityType = value.activityType;
        this.public_ = value.public_;
        this.scoreInfos = value.scoreInfos;
        this.communityId = value.communityId;
    }

    public Activity(
        Integer   id,
        String    title,
        String    avatar,
        String    summary,
        String    body,
        String    unit,
        String    tags,
        Integer   status,
        Timestamp startTime,
        Timestamp endTime,
        Timestamp attendDueTime,
        Timestamp created,
        Integer   createdBy,
        Timestamp modified,
        Integer   modifiedBy,
        String    attendInfos,
        String    address,
        String    coordinate,
        Integer   activityType,
        Boolean   public_,
        String    scoreInfos,
        Integer   communityId
    ) {
        this.id = id;
        this.title = title;
        this.avatar = avatar;
        this.summary = summary;
        this.body = body;
        this.unit = unit;
        this.tags = tags;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendDueTime = attendDueTime;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.attendInfos = attendInfos;
        this.address = address;
        this.coordinate = coordinate;
        this.activityType = activityType;
        this.public_ = public_;
        this.scoreInfos = scoreInfos;
        this.communityId = communityId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getAttendDueTime() {
        return this.attendDueTime;
    }

    public void setAttendDueTime(Timestamp attendDueTime) {
        this.attendDueTime = attendDueTime;
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

    public Timestamp getModified() {
        return this.modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getAttendInfos() {
        return this.attendInfos;
    }

    public void setAttendInfos(String attendInfos) {
        this.attendInfos = attendInfos;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getActivityType() {
        return this.activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Boolean getPublic() {
        return this.public_;
    }

    public void setPublic(Boolean public_) {
        this.public_ = public_;
    }

    public String getScoreInfos() {
        return this.scoreInfos;
    }

    public void setScoreInfos(String scoreInfos) {
        this.scoreInfos = scoreInfos;
    }

    public Integer getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Activity (");

        sb.append(id);
        sb.append(", ").append(title);
        sb.append(", ").append(avatar);
        sb.append(", ").append(summary);
        sb.append(", ").append(body);
        sb.append(", ").append(unit);
        sb.append(", ").append(tags);
        sb.append(", ").append(status);
        sb.append(", ").append(startTime);
        sb.append(", ").append(endTime);
        sb.append(", ").append(attendDueTime);
        sb.append(", ").append(created);
        sb.append(", ").append(createdBy);
        sb.append(", ").append(modified);
        sb.append(", ").append(modifiedBy);
        sb.append(", ").append(attendInfos);
        sb.append(", ").append(address);
        sb.append(", ").append(coordinate);
        sb.append(", ").append(activityType);
        sb.append(", ").append(public_);
        sb.append(", ").append(scoreInfos);
        sb.append(", ").append(communityId);

        sb.append(")");
        return sb.toString();
    }
}
