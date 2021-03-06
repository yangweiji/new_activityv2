/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.ActivityPhotoPicture;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ActivityPhotoPictureRecord extends UpdatableRecordImpl<ActivityPhotoPictureRecord> implements Record6<Integer, Integer, String, Timestamp, Integer, Integer> {

    private static final long serialVersionUID = 579009168;

    /**
     * Setter for <code>activity_photo_picture.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activity_photo_picture.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activity_photo_picture.activity_photo_id</code>. 关联活动相册编号
     */
    public void setActivityPhotoId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>activity_photo_picture.activity_photo_id</code>. 关联活动相册编号
     */
    public Integer getActivityPhotoId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>activity_photo_picture.picture</code>. 图片
     */
    public void setPicture(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>activity_photo_picture.picture</code>. 图片
     */
    public String getPicture() {
        return (String) get(2);
    }

    /**
     * Setter for <code>activity_photo_picture.created</code>. 创建时间
     */
    public void setCreated(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>activity_photo_picture.created</code>. 创建时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>activity_photo_picture.created_by</code>. 创建人
     */
    public void setCreatedBy(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>activity_photo_picture.created_by</code>. 创建人
     */
    public Integer getCreatedBy() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>activity_photo_picture.order</code>. 排序
     */
    public void setOrder(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>activity_photo_picture.order</code>. 排序
     */
    public Integer getOrder() {
        return (Integer) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Timestamp, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Timestamp, Integer, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ACTIVITY_PHOTO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.PICTURE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE.ORDER;
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
    public Integer component2() {
        return getActivityPhotoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPicture();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getOrder();
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
    public Integer value2() {
        return getActivityPhotoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPicture();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value2(Integer value) {
        setActivityPhotoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value3(String value) {
        setPicture(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value4(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value5(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord value6(Integer value) {
        setOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityPhotoPictureRecord values(Integer value1, Integer value2, String value3, Timestamp value4, Integer value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivityPhotoPictureRecord
     */
    public ActivityPhotoPictureRecord() {
        super(ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE);
    }

    /**
     * Create a detached, initialised ActivityPhotoPictureRecord
     */
    public ActivityPhotoPictureRecord(Integer id, Integer activityPhotoId, String picture, Timestamp created, Integer createdBy, Integer order) {
        super(ActivityPhotoPicture.ACTIVITY_PHOTO_PICTURE);

        set(0, id);
        set(1, activityPhotoId);
        set(2, picture);
        set(3, created);
        set(4, createdBy);
        set(5, order);
    }
}
