/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activityv2;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.ActivitySmsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivitySms extends TableImpl<ActivitySmsRecord> {

    private static final long serialVersionUID = 1967423030;

    /**
     * The reference instance of <code>activityv2.activity_sms</code>
     */
    public static final ActivitySms ACTIVITY_SMS = new ActivitySms();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivitySmsRecord> getRecordType() {
        return ActivitySmsRecord.class;
    }

    /**
     * The column <code>activityv2.activity_sms.id</code>.
     */
    public final TableField<ActivitySmsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>activityv2.activity_sms.activity_id</code>.
     */
    public final TableField<ActivitySmsRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.activity_sms.template_code</code>.
     */
    public final TableField<ActivitySmsRecord, String> TEMPLATE_CODE = createField("template_code", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>activityv2.activity_sms.template_name</code>.
     */
    public final TableField<ActivitySmsRecord, String> TEMPLATE_NAME = createField("template_name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>activityv2.activity_sms.message_content</code>.
     */
    public final TableField<ActivitySmsRecord, String> MESSAGE_CONTENT = createField("message_content", org.jooq.impl.SQLDataType.VARCHAR(500), this, "");

    /**
     * The column <code>activityv2.activity_sms.send_time</code>.
     */
    public final TableField<ActivitySmsRecord, Timestamp> SEND_TIME = createField("send_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activityv2.activity_sms.send_user_id</code>.
     */
    public final TableField<ActivitySmsRecord, Integer> SEND_USER_ID = createField("send_user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.activity_sms.send_result_code</code>.
     */
    public final TableField<ActivitySmsRecord, String> SEND_RESULT_CODE = createField("send_result_code", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>activityv2.activity_sms.send_result_desc</code>.
     */
    public final TableField<ActivitySmsRecord, String> SEND_RESULT_DESC = createField("send_result_desc", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * Create a <code>activityv2.activity_sms</code> table reference
     */
    public ActivitySms() {
        this(DSL.name("activity_sms"), null);
    }

    /**
     * Create an aliased <code>activityv2.activity_sms</code> table reference
     */
    public ActivitySms(String alias) {
        this(DSL.name(alias), ACTIVITY_SMS);
    }

    /**
     * Create an aliased <code>activityv2.activity_sms</code> table reference
     */
    public ActivitySms(Name alias) {
        this(alias, ACTIVITY_SMS);
    }

    private ActivitySms(Name alias, Table<ActivitySmsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivitySms(Name alias, Table<ActivitySmsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Activityv2.ACTIVITYV2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ACTIVITY_SMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivitySmsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ACTIVITY_SMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActivitySmsRecord> getPrimaryKey() {
        return Keys.KEY_ACTIVITY_SMS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivitySmsRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivitySmsRecord>>asList(Keys.KEY_ACTIVITY_SMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySms as(String alias) {
        return new ActivitySms(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivitySms as(Name alias) {
        return new ActivitySms(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivitySms rename(String name) {
        return new ActivitySms(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivitySms rename(Name name) {
        return new ActivitySms(name, null);
    }
}