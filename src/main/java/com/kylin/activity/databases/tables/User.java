/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = -383962870;

    /**
     * The reference instance of <code>user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>user.id</code>. 编号
     */
    public final TableField<UserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>user.username</code>. 登录名
     */
    public final TableField<UserRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(255), this, "登录名");

    /**
     * The column <code>user.displayname</code>. 显示名
     */
    public final TableField<UserRecord, String> DISPLAYNAME = createField("displayname", org.jooq.impl.SQLDataType.VARCHAR(255), this, "显示名");

    /**
     * The column <code>user.password</code>. 密码
     */
    public final TableField<UserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(255), this, "密码");

    /**
     * The column <code>user.role</code>. 角色
     */
    public final TableField<UserRecord, String> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR(255), this, "角色");

    /**
     * The column <code>user.enabled</code>. 是否启用
     */
    public final TableField<UserRecord, Boolean> ENABLED = createField("enabled", org.jooq.impl.SQLDataType.BIT, this, "是否启用");

    /**
     * The column <code>user.gender</code>. 性别 1 男 2 女
     */
    public final TableField<UserRecord, Integer> GENDER = createField("gender", org.jooq.impl.SQLDataType.INTEGER, this, "性别 1 男 2 女");

    /**
     * The column <code>user.created</code>.
     */
    public final TableField<UserRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>user.level</code>. 会员级别 0 普通用户 年份： 值所确定的年份的会员
     */
    public final TableField<UserRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER, this, "会员级别 0 普通用户 年份： 值所确定的年份的会员");

    /**
     * The column <code>user.id_card</code>. 身份证号
     */
    public final TableField<UserRecord, String> ID_CARD = createField("id_card", org.jooq.impl.SQLDataType.VARCHAR(255), this, "身份证号");

    /**
     * The column <code>user.real_name</code>. 真实姓名
     */
    public final TableField<UserRecord, String> REAL_NAME = createField("real_name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "真实姓名");

    /**
     * The column <code>user.real_time</code>.
     */
    public final TableField<UserRecord, Timestamp> REAL_TIME = createField("real_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>user.is_real</code>. 是否通过认证
     */
    public final TableField<UserRecord, Boolean> IS_REAL = createField("is_real", org.jooq.impl.SQLDataType.BIT, this, "是否通过认证");

    /**
     * The column <code>user.open_id</code>. 微信openId
     */
    public final TableField<UserRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR(255), this, "微信openId");

    /**
     * The column <code>user.avatar</code>. 头像
     */
    public final TableField<UserRecord, String> AVATAR = createField("avatar", org.jooq.impl.SQLDataType.VARCHAR(255), this, "头像");

    /**
     * The column <code>user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>user.work_company</code>. 工作单位
     */
    public final TableField<UserRecord, String> WORK_COMPANY = createField("work_company", org.jooq.impl.SQLDataType.VARCHAR(255), this, "工作单位");

    /**
     * The column <code>user.is_party</code>. 是否党员
     */
    public final TableField<UserRecord, Boolean> IS_PARTY = createField("is_party", org.jooq.impl.SQLDataType.BIT, this, "是否党员");

    /**
     * The column <code>user.address</code>. 家庭地址
     */
    public final TableField<UserRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(255), this, "家庭地址");

    /**
     * The column <code>user.blood_type</code>. 血型
     */
    public final TableField<UserRecord, String> BLOOD_TYPE = createField("blood_type", org.jooq.impl.SQLDataType.VARCHAR(45), this, "血型");

    /**
     * The column <code>user.clothing_size</code>. T恤尺寸
     */
    public final TableField<UserRecord, String> CLOTHING_SIZE = createField("clothing_size", org.jooq.impl.SQLDataType.VARCHAR(45), this, "T恤尺寸");

    /**
     * The column <code>user.occupation</code>. 职业
     */
    public final TableField<UserRecord, String> OCCUPATION = createField("occupation", org.jooq.impl.SQLDataType.VARCHAR(255), this, "职业");

    /**
     * The column <code>user.emergency_contact_name</code>. 紧急联系人姓名
     */
    public final TableField<UserRecord, String> EMERGENCY_CONTACT_NAME = createField("emergency_contact_name", org.jooq.impl.SQLDataType.VARCHAR(45), this, "紧急联系人姓名");

    /**
     * The column <code>user.emergency_contact_mobile</code>. 紧急联系人电话
     */
    public final TableField<UserRecord, String> EMERGENCY_CONTACT_MOBILE = createField("emergency_contact_mobile", org.jooq.impl.SQLDataType.VARCHAR(45), this, "紧急联系人电话");

    /**
     * The column <code>user.wechat_id</code>. 微信号
     */
    public final TableField<UserRecord, String> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.VARCHAR(45), this, "微信号");

    /**
     * The column <code>user.union_id</code>. 微信开放平台，联合身份ID
     */
    public final TableField<UserRecord, String> UNION_ID = createField("union_id", org.jooq.impl.SQLDataType.VARCHAR(225), this, "微信开放平台，联合身份ID");

    /**
     * The column <code>user.nick_name</code>. 微信昵称
     */
    public final TableField<UserRecord, String> NICK_NAME = createField("nick_name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "微信昵称");

    /**
     * The column <code>user.mobile</code>. 双向绑定时，使用的手机号码
     */
    public final TableField<UserRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(20), this, "双向绑定时，使用的手机号码");

    /**
     * Create a <code>user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户表");
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
        return Arrays.<Index>asList(Indexes.USER_PRIMARY, Indexes.USER_UK_USERNAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY, Keys.KEY_USER_UK_USERNAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }
}
