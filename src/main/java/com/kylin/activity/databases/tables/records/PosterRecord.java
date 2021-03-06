/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.Poster;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 海报表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PosterRecord extends UpdatableRecordImpl<PosterRecord> implements Record10<Integer, String, String, String, String, Integer, Timestamp, String, Boolean, Integer> {

    private static final long serialVersionUID = 1062382432;

    /**
     * Setter for <code>poster.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>poster.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>poster.title</code>. 海报标题，当海报链接活动时，自动使用活动的标题
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>poster.title</code>. 海报标题，当海报链接活动时，自动使用活动的标题
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>poster.avatar</code>. 默认海报背景图
     */
    public void setAvatar(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>poster.avatar</code>. 默认海报背景图
     */
    public String getAvatar() {
        return (String) get(2);
    }

    /**
     * Setter for <code>poster.mobile_avatar</code>. 收集显示海报图，为空时使用avatar的值
     */
    public void setMobileAvatar(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>poster.mobile_avatar</code>. 收集显示海报图，为空时使用avatar的值
     */
    public String getMobileAvatar() {
        return (String) get(3);
    }

    /**
     * Setter for <code>poster.link</code>. 海报外部链接，当关联活动时可以为空，初次实现时可以不考虑该字段，只做活动海报
     */
    public void setLink(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>poster.link</code>. 海报外部链接，当关联活动时可以为空，初次实现时可以不考虑该字段，只做活动海报
     */
    public String getLink() {
        return (String) get(4);
    }

    /**
     * Setter for <code>poster.activity_id</code>. 海报关联的活动Id
     */
    public void setActivityId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>poster.activity_id</code>. 海报关联的活动Id
     */
    public Integer getActivityId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>poster.created</code>. 创建时间
     */
    public void setCreated(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>poster.created</code>. 创建时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>poster.poster_type</code>. 海报类型， 与活动关联时，自动使用活动的类型
     */
    public void setPosterType(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>poster.poster_type</code>. 海报类型， 与活动关联时，自动使用活动的类型
     */
    public String getPosterType() {
        return (String) get(7);
    }

    /**
     * Setter for <code>poster.show</code>. 是否显示在首页，用户先设置是否展示在首页，然后设置展示的顺序
     */
    public void setShow(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>poster.show</code>. 是否显示在首页，用户先设置是否展示在首页，然后设置展示的顺序
     */
    public Boolean getShow() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>poster.sequence</code>. 海报排序，排序相同时按created倒序排
     */
    public void setSequence(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>poster.sequence</code>. 海报排序，排序相同时按created倒序排
     */
    public Integer getSequence() {
        return (Integer) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, String, String, Integer, Timestamp, String, Boolean, Integer> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, String, String, Integer, Timestamp, String, Boolean, Integer> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Poster.POSTER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Poster.POSTER.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Poster.POSTER.AVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Poster.POSTER.MOBILE_AVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Poster.POSTER.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Poster.POSTER.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return Poster.POSTER.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Poster.POSTER.POSTER_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field9() {
        return Poster.POSTER.SHOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return Poster.POSTER.SEQUENCE;
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
        return getMobileAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getPosterType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component9() {
        return getShow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getSequence();
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
        return getMobileAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getPosterType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value9() {
        return getShow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getSequence();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value3(String value) {
        setAvatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value4(String value) {
        setMobileAvatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value5(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value6(Integer value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value7(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value8(String value) {
        setPosterType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value9(Boolean value) {
        setShow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord value10(Integer value) {
        setSequence(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosterRecord values(Integer value1, String value2, String value3, String value4, String value5, Integer value6, Timestamp value7, String value8, Boolean value9, Integer value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PosterRecord
     */
    public PosterRecord() {
        super(Poster.POSTER);
    }

    /**
     * Create a detached, initialised PosterRecord
     */
    public PosterRecord(Integer id, String title, String avatar, String mobileAvatar, String link, Integer activityId, Timestamp created, String posterType, Boolean show, Integer sequence) {
        super(Poster.POSTER);

        set(0, id);
        set(1, title);
        set(2, avatar);
        set(3, mobileAvatar);
        set(4, link);
        set(5, activityId);
        set(6, created);
        set(7, posterType);
        set(8, show);
        set(9, sequence);
    }
}
