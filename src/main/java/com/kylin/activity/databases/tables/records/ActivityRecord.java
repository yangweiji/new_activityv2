/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.Activity;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ActivityRecord extends UpdatableRecordImpl<ActivityRecord> implements Record22<Integer, String, String, String, String, String, String, Integer, Timestamp, Timestamp, Timestamp, Timestamp, Integer, Timestamp, Integer, String, String, String, Integer, Boolean, String, Integer> {

    private static final long serialVersionUID = 2033080469;

    /**
     * Setter for <code>activityv2.activity.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activityv2.activity.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activityv2.activity.title</code>. 标题
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>activityv2.activity.title</code>. 标题
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>activityv2.activity.avatar</code>. 标题图片
     */
    public void setAvatar(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>activityv2.activity.avatar</code>. 标题图片
     */
    public String getAvatar() {
        return (String) get(2);
    }

    /**
     * Setter for <code>activityv2.activity.summary</code>. 摘要
     */
    public void setSummary(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>activityv2.activity.summary</code>. 摘要
     */
    public String getSummary() {
        return (String) get(3);
    }

    /**
     * Setter for <code>activityv2.activity.body</code>. 正文
     */
    public void setBody(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>activityv2.activity.body</code>. 正文
     */
    public String getBody() {
        return (String) get(4);
    }

    /**
     * Setter for <code>activityv2.activity.unit</code>. 发布单位
     */
    public void setUnit(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>activityv2.activity.unit</code>. 发布单位
     */
    public String getUnit() {
        return (String) get(5);
    }

    /**
     * Setter for <code>activityv2.activity.tags</code>. 标签
     */
    public void setTags(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>activityv2.activity.tags</code>. 标签
     */
    public String getTags() {
        return (String) get(6);
    }

    /**
     * Setter for <code>activityv2.activity.status</code>. 状态 0 草稿 1 发布 -1 禁用
     */
    public void setStatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>activityv2.activity.status</code>. 状态 0 草稿 1 发布 -1 禁用
     */
    public Integer getStatus() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>activityv2.activity.start_time</code>.
     */
    public void setStartTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>activityv2.activity.start_time</code>.
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>activityv2.activity.end_time</code>.
     */
    public void setEndTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>activityv2.activity.end_time</code>.
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>activityv2.activity.attend_due_time</code>.
     */
    public void setAttendDueTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>activityv2.activity.attend_due_time</code>.
     */
    public Timestamp getAttendDueTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>activityv2.activity.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>activityv2.activity.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>activityv2.activity.created_by</code>. 创建人
     */
    public void setCreatedBy(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>activityv2.activity.created_by</code>. 创建人
     */
    public Integer getCreatedBy() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>activityv2.activity.modified</code>.
     */
    public void setModified(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>activityv2.activity.modified</code>.
     */
    public Timestamp getModified() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>activityv2.activity.modified_by</code>. 修改人
     */
    public void setModifiedBy(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>activityv2.activity.modified_by</code>. 修改人
     */
    public Integer getModifiedBy() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>activityv2.activity.attend_infos</code>. 报名需要提交的表单信息 报名信息json
     */
    public void setAttendInfos(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>activityv2.activity.attend_infos</code>. 报名需要提交的表单信息 报名信息json
     */
    public String getAttendInfos() {
        return (String) get(15);
    }

    /**
     * Setter for <code>activityv2.activity.address</code>. 活动地址
     */
    public void setAddress(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>activityv2.activity.address</code>. 活动地址
     */
    public String getAddress() {
        return (String) get(16);
    }

    /**
     * Setter for <code>activityv2.activity.coordinate</code>. 活动坐标
     */
    public void setCoordinate(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>activityv2.activity.coordinate</code>. 活动坐标
     */
    public String getCoordinate() {
        return (String) get(17);
    }

    /**
     * Setter for <code>activityv2.activity.activity_type</code>.
     */
    public void setActivityType(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>activityv2.activity.activity_type</code>.
     */
    public Integer getActivityType() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>activityv2.activity.public</code>.
     */
    public void setPublic(Boolean value) {
        set(19, value);
    }

    /**
     * Getter for <code>activityv2.activity.public</code>.
     */
    public Boolean getPublic() {
        return (Boolean) get(19);
    }

    /**
     * Setter for <code>activityv2.activity.score_infos</code>. 活动奖励积分信息
     */
    public void setScoreInfos(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>activityv2.activity.score_infos</code>. 活动奖励积分信息
     */
    public String getScoreInfos() {
        return (String) get(20);
    }

    /**
     * Setter for <code>activityv2.activity.community_id</code>.
     */
    public void setCommunityId(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>activityv2.activity.community_id</code>.
     */
    public Integer getCommunityId() {
        return (Integer) get(21);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record22 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, String, String, String, String, String, String, Integer, Timestamp, Timestamp, Timestamp, Timestamp, Integer, Timestamp, Integer, String, String, String, Integer, Boolean, String, Integer> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, String, String, String, String, String, String, Integer, Timestamp, Timestamp, Timestamp, Timestamp, Integer, Timestamp, Integer, String, String, String, Integer, Boolean, String, Integer> valuesRow() {
        return (Row22) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Activity.ACTIVITY_.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Activity.ACTIVITY_.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Activity.ACTIVITY_.AVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Activity.ACTIVITY_.SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Activity.ACTIVITY_.BODY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Activity.ACTIVITY_.UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Activity.ACTIVITY_.TAGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Activity.ACTIVITY_.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return Activity.ACTIVITY_.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return Activity.ACTIVITY_.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return Activity.ACTIVITY_.ATTEND_DUE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return Activity.ACTIVITY_.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return Activity.ACTIVITY_.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return Activity.ACTIVITY_.MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return Activity.ACTIVITY_.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return Activity.ACTIVITY_.ATTEND_INFOS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return Activity.ACTIVITY_.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return Activity.ACTIVITY_.COORDINATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return Activity.ACTIVITY_.ACTIVITY_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field20() {
        return Activity.ACTIVITY_.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field21() {
        return Activity.ACTIVITY_.SCORE_INFOS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field22() {
        return Activity.ACTIVITY_.COMMUNITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getAttendDueTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component13() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component15() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getAttendInfos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getCoordinate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component19() {
        return getActivityType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component20() {
        return getPublic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component21() {
        return getScoreInfos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component22() {
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getAttendDueTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getAttendInfos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getCoordinate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getActivityType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value20() {
        return getPublic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value21() {
        return getScoreInfos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value22() {
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value3(String value) {
        setAvatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value4(String value) {
        setSummary(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value5(String value) {
        setBody(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value6(String value) {
        setUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value7(String value) {
        setTags(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value8(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value9(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value10(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value11(Timestamp value) {
        setAttendDueTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value12(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value13(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value14(Timestamp value) {
        setModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value15(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value16(String value) {
        setAttendInfos(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value17(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value18(String value) {
        setCoordinate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value19(Integer value) {
        setActivityType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value20(Boolean value) {
        setPublic(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value21(String value) {
        setScoreInfos(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value22(Integer value) {
        setCommunityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, Integer value8, Timestamp value9, Timestamp value10, Timestamp value11, Timestamp value12, Integer value13, Timestamp value14, Integer value15, String value16, String value17, String value18, Integer value19, Boolean value20, String value21, Integer value22) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivityRecord
     */
    public ActivityRecord() {
        super(Activity.ACTIVITY_);
    }

    /**
     * Create a detached, initialised ActivityRecord
     */
    public ActivityRecord(Integer id, String title, String avatar, String summary, String body, String unit, String tags, Integer status, Timestamp startTime, Timestamp endTime, Timestamp attendDueTime, Timestamp created, Integer createdBy, Timestamp modified, Integer modifiedBy, String attendInfos, String address, String coordinate, Integer activityType, Boolean public_, String scoreInfos, Integer communityId) {
        super(Activity.ACTIVITY_);

        set(0, id);
        set(1, title);
        set(2, avatar);
        set(3, summary);
        set(4, body);
        set(5, unit);
        set(6, tags);
        set(7, status);
        set(8, startTime);
        set(9, endTime);
        set(10, attendDueTime);
        set(11, created);
        set(12, createdBy);
        set(13, modified);
        set(14, modifiedBy);
        set(15, attendInfos);
        set(16, address);
        set(17, coordinate);
        set(18, activityType);
        set(19, public_);
        set(20, scoreInfos);
        set(21, communityId);
    }
}
