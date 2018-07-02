/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.CommunityUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 社团成员
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommunityUserRecord extends UpdatableRecordImpl<CommunityUserRecord> implements Record7<Integer, Integer, Integer, String, Timestamp, Integer, Boolean> {

    private static final long serialVersionUID = -1399930109;

    /**
     * Setter for <code>activityv2.community_user.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activityv2.community_user.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activityv2.community_user.community_id</code>.
     */
    public void setCommunityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>activityv2.community_user.community_id</code>.
     */
    public Integer getCommunityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>activityv2.community_user.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>activityv2.community_user.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>activityv2.community_user.role</code>. 成员在社团中的角色 admin、publish
     */
    public void setRole(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>activityv2.community_user.role</code>. 成员在社团中的角色 admin、publish
     */
    public String getRole() {
        return (String) get(3);
    }

    /**
     * Setter for <code>activityv2.community_user.created</code>. 加入时间
     */
    public void setCreated(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>activityv2.community_user.created</code>. 加入时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>activityv2.community_user.level</code>.
     */
    public void setLevel(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>activityv2.community_user.level</code>.
     */
    public Integer getLevel() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>activityv2.community_user.is_black</code>. 是否黑名单用户
     */
    public void setIsBlack(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>activityv2.community_user.is_black</code>. 是否黑名单用户
     */
    public Boolean getIsBlack() {
        return (Boolean) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, Timestamp, Integer, Boolean> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, Timestamp, Integer, Boolean> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CommunityUser.COMMUNITY_USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return CommunityUser.COMMUNITY_USER.COMMUNITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CommunityUser.COMMUNITY_USER.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return CommunityUser.COMMUNITY_USER.ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return CommunityUser.COMMUNITY_USER.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return CommunityUser.COMMUNITY_USER.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field7() {
        return CommunityUser.COMMUNITY_USER.IS_BLACK;
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
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component7() {
        return getIsBlack();
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
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value7() {
        return getIsBlack();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value2(Integer value) {
        setCommunityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value4(String value) {
        setRole(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value5(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value6(Integer value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord value7(Boolean value) {
        setIsBlack(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUserRecord values(Integer value1, Integer value2, Integer value3, String value4, Timestamp value5, Integer value6, Boolean value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CommunityUserRecord
     */
    public CommunityUserRecord() {
        super(CommunityUser.COMMUNITY_USER);
    }

    /**
     * Create a detached, initialised CommunityUserRecord
     */
    public CommunityUserRecord(Integer id, Integer communityId, Integer userId, String role, Timestamp created, Integer level, Boolean isBlack) {
        super(CommunityUser.COMMUNITY_USER);

        set(0, id);
        set(1, communityId);
        set(2, userId);
        set(3, role);
        set(4, created);
        set(5, level);
        set(6, isBlack);
    }
}
