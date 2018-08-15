/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.ActivitySms;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 短信
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivitySmsRecord extends UpdatableRecordImpl<ActivitySmsRecord> implements Record9<Integer, Integer, String, String, String, Timestamp, Integer, String, String> {

    private static final long serialVersionUID = 750484903;

    /**
     * Setter for <code>activity_sms.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activity_sms.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activity_sms.activity_id</code>. 活动编号
     */
    public void setActivityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>activity_sms.activity_id</code>. 活动编号
     */
    public Integer getActivityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>activity_sms.template_code</code>. 模板编码
     */
    public void setTemplateCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>activity_sms.template_code</code>. 模板编码
     */
    public String getTemplateCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>activity_sms.template_name</code>. 模板名称
     */
    public void setTemplateName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>activity_sms.template_name</code>. 模板名称
     */
    public String getTemplateName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>activity_sms.message_content</code>. 消息内容
     */
    public void setMessageContent(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>activity_sms.message_content</code>. 消息内容
     */
    public String getMessageContent() {
        return (String) get(4);
    }

    /**
     * Setter for <code>activity_sms.send_time</code>. 发送时间
     */
    public void setSendTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>activity_sms.send_time</code>. 发送时间
     */
    public Timestamp getSendTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>activity_sms.send_user_id</code>. 发送人编号
     */
    public void setSendUserId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>activity_sms.send_user_id</code>. 发送人编号
     */
    public Integer getSendUserId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>activity_sms.send_result_code</code>. 发送结果
     */
    public void setSendResultCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>activity_sms.send_result_code</code>. 发送结果
     */
    public String getSendResultCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>activity_sms.send_result_desc</code>. 发送结果描述
     */
    public void setSendResultDesc(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>activity_sms.send_result_desc</code>. 发送结果描述
     */
    public String getSendResultDesc() {
        return (String) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, String, String, Timestamp, Integer, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, String, String, Timestamp, Integer, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ActivitySms.ACTIVITY_SMS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ActivitySms.ACTIVITY_SMS.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ActivitySms.ACTIVITY_SMS.TEMPLATE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ActivitySms.ACTIVITY_SMS.TEMPLATE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ActivitySms.ACTIVITY_SMS.MESSAGE_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return ActivitySms.ACTIVITY_SMS.SEND_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return ActivitySms.ACTIVITY_SMS.SEND_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return ActivitySms.ACTIVITY_SMS.SEND_RESULT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return ActivitySms.ACTIVITY_SMS.SEND_RESULT_DESC;
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
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getTemplateCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getTemplateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getMessageContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getSendTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getSendUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getSendResultCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getSendResultDesc();
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
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTemplateCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTemplateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMessageContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getSendTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getSendUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getSendResultCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getSendResultDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value2(Integer value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value3(String value) {
        setTemplateCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value4(String value) {
        setTemplateName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value5(String value) {
        setMessageContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value6(Timestamp value) {
        setSendTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value7(Integer value) {
        setSendUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value8(String value) {
        setSendResultCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord value9(String value) {
        setSendResultDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySmsRecord values(Integer value1, Integer value2, String value3, String value4, String value5, Timestamp value6, Integer value7, String value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivitySmsRecord
     */
    public ActivitySmsRecord() {
        super(ActivitySms.ACTIVITY_SMS);
    }

    /**
     * Create a detached, initialised ActivitySmsRecord
     */
    public ActivitySmsRecord(Integer id, Integer activityId, String templateCode, String templateName, String messageContent, Timestamp sendTime, Integer sendUserId, String sendResultCode, String sendResultDesc) {
        super(ActivitySms.ACTIVITY_SMS);

        set(0, id);
        set(1, activityId);
        set(2, templateCode);
        set(3, templateName);
        set(4, messageContent);
        set(5, sendTime);
        set(6, sendUserId);
        set(7, sendResultCode);
        set(8, sendResultDesc);
    }
}
