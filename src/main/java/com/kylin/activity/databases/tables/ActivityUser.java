/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.ActivityUserRecord;

import java.math.BigDecimal;
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
 * 参与活动人
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityUser extends TableImpl<ActivityUserRecord> {

    private static final long serialVersionUID = 1202081663;

    /**
     * The reference instance of <code>activity_user</code>
     */
    public static final ActivityUser ACTIVITY_USER = new ActivityUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityUserRecord> getRecordType() {
        return ActivityUserRecord.class;
    }

    /**
     * The column <code>activity_user.id</code>. 编号
     */
    public final TableField<ActivityUserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>activity_user.user_id</code>. 用户编号
     */
    public final TableField<ActivityUserRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "用户编号");

    /**
     * The column <code>activity_user.activity_id</code>. 活动编号
     */
    public final TableField<ActivityUserRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "活动编号");

    /**
     * The column <code>activity_user.activity_ticket_id</code>. 活动门票编号
     */
    public final TableField<ActivityUserRecord, Integer> ACTIVITY_TICKET_ID = createField("activity_ticket_id", org.jooq.impl.SQLDataType.INTEGER, this, "活动门票编号");

    /**
     * The column <code>activity_user.created</code>. 创建时间
     */
    public final TableField<ActivityUserRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * The column <code>activity_user.created_by</code>. 创建人
     */
    public final TableField<ActivityUserRecord, Integer> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.INTEGER, this, "创建人");

    /**
     * The column <code>activity_user.attend_time</code>. 报名时间
     */
    public final TableField<ActivityUserRecord, Timestamp> ATTEND_TIME = createField("attend_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "报名时间");

    /**
     * The column <code>activity_user.check_in_time</code>. 签到时间
     */
    public final TableField<ActivityUserRecord, Timestamp> CHECK_IN_TIME = createField("check_in_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "签到时间");

    /**
     * The column <code>activity_user.real_name</code>. 名称
     */
    public final TableField<ActivityUserRecord, String> REAL_NAME = createField("real_name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "名称");

    /**
     * The column <code>activity_user.mobile</code>. 联系方式
     */
    public final TableField<ActivityUserRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(255), this, "联系方式");

    /**
     * The column <code>activity_user.other_info</code>. 其他信息
     */
    public final TableField<ActivityUserRecord, String> OTHER_INFO = createField("other_info", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "其他信息");

    /**
     * The column <code>activity_user.price</code>. 报名时的缴费金额
     */
    public final TableField<ActivityUserRecord, BigDecimal> PRICE = createField("price", org.jooq.impl.SQLDataType.DECIMAL(18, 2), this, "报名时的缴费金额");

    /**
     * The column <code>activity_user.score</code>. 报名时的积分抵扣值
     */
    public final TableField<ActivityUserRecord, Integer> SCORE = createField("score", org.jooq.impl.SQLDataType.INTEGER, this, "报名时的积分抵扣值");

    /**
     * The column <code>activity_user.modified</code>. 修改时间
     */
    public final TableField<ActivityUserRecord, Timestamp> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP, this, "修改时间");

    /**
     * The column <code>activity_user.modified_by</code>. 修改人
     */
    public final TableField<ActivityUserRecord, Integer> MODIFIED_BY = createField("modified_by", org.jooq.impl.SQLDataType.INTEGER, this, "修改人");

    /**
     * The column <code>activity_user.status</code>. 中签状态（0-不抽签；1-待抽签，2-中签，3-已申请退费，4-已完成退费）
     */
    public final TableField<ActivityUserRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "中签状态（0-不抽签；1-待抽签，2-中签，3-已申请退费，4-已完成退费）");

    /**
     * The column <code>activity_user.after_files</code>. 活动后，上传图片
     */
    public final TableField<ActivityUserRecord, String> AFTER_FILES = createField("after_files", org.jooq.impl.SQLDataType.VARCHAR(1000).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "活动后，上传图片");

    /**
     * The column <code>activity_user.after_time</code>. 活动图片上传时间
     */
    public final TableField<ActivityUserRecord, Timestamp> AFTER_TIME = createField("after_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "活动图片上传时间");

    /**
     * Create a <code>activity_user</code> table reference
     */
    public ActivityUser() {
        this(DSL.name("activity_user"), null);
    }

    /**
     * Create an aliased <code>activity_user</code> table reference
     */
    public ActivityUser(String alias) {
        this(DSL.name(alias), ACTIVITY_USER);
    }

    /**
     * Create an aliased <code>activity_user</code> table reference
     */
    public ActivityUser(Name alias) {
        this(alias, ACTIVITY_USER);
    }

    private ActivityUser(Name alias, Table<ActivityUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivityUser(Name alias, Table<ActivityUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "参与活动人");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID, Indexes.ACTIVITY_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivityUserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ACTIVITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActivityUserRecord> getPrimaryKey() {
        return Keys.KEY_ACTIVITY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivityUserRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivityUserRecord>>asList(Keys.KEY_ACTIVITY_USER_PRIMARY, Keys.KEY_ACTIVITY_USER_IDX_ACTIVITY_USER_USER_ID_ACTIVITY_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityUser as(String alias) {
        return new ActivityUser(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityUser as(Name alias) {
        return new ActivityUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityUser rename(String name) {
        return new ActivityUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityUser rename(Name name) {
        return new ActivityUser(name, null);
    }
}
