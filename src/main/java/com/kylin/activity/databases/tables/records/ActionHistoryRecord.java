/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.ActionHistory;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActionHistoryRecord extends UpdatableRecordImpl<ActionHistoryRecord> implements Record7<Integer, Integer, Integer, String, Timestamp, Integer, Integer> {

    private static final long serialVersionUID = -1163346523;

    /**
     * Setter for <code>action_history.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>action_history.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>action_history.axtenal_id</code>. 外部关联id
     */
    public void setAxtenalId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>action_history.axtenal_id</code>. 外部关联id
     */
    public Integer getAxtenalId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>action_history.action</code>. 操作名称
     */
    public void setAction(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>action_history.action</code>. 操作名称
     */
    public Integer getAction() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>action_history.memo</code>. 注释
     */
    public void setMemo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>action_history.memo</code>. 注释
     */
    public String getMemo() {
        return (String) get(3);
    }

    /**
     * Setter for <code>action_history.created</code>. 创建时间
     */
    public void setCreated(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>action_history.created</code>. 创建时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>action_history.created_by</code>. 创建人
     */
    public void setCreatedBy(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>action_history.created_by</code>. 创建人
     */
    public Integer getCreatedBy() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>action_history.community_id</code>. 团体编号
     */
    public void setCommunityId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>action_history.community_id</code>. 团体编号
     */
    public Integer getCommunityId() {
        return (Integer) get(6);
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
    public Row7<Integer, Integer, Integer, String, Timestamp, Integer, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, Timestamp, Integer, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ActionHistory.ACTION_HISTORY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ActionHistory.ACTION_HISTORY.AXTENAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ActionHistory.ACTION_HISTORY.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ActionHistory.ACTION_HISTORY.MEMO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return ActionHistory.ACTION_HISTORY.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return ActionHistory.ACTION_HISTORY.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return ActionHistory.ACTION_HISTORY.COMMUNITY_ID;
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
        return getAxtenalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMemo();
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
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
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
    public Integer value2() {
        return getAxtenalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMemo();
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
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value2(Integer value) {
        setAxtenalId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value3(Integer value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value4(String value) {
        setMemo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value5(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value6(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord value7(Integer value) {
        setCommunityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistoryRecord values(Integer value1, Integer value2, Integer value3, String value4, Timestamp value5, Integer value6, Integer value7) {
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
     * Create a detached ActionHistoryRecord
     */
    public ActionHistoryRecord() {
        super(ActionHistory.ACTION_HISTORY);
    }

    /**
     * Create a detached, initialised ActionHistoryRecord
     */
    public ActionHistoryRecord(Integer id, Integer axtenalId, Integer action, String memo, Timestamp created, Integer createdBy, Integer communityId) {
        super(ActionHistory.ACTION_HISTORY);

        set(0, id);
        set(1, axtenalId);
        set(2, action);
        set(3, memo);
        set(4, created);
        set(5, createdBy);
        set(6, communityId);
    }
}
