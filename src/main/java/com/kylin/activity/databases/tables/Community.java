/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activity;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.CommunityRecord;

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
 * 社团
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Community extends TableImpl<CommunityRecord> {

    private static final long serialVersionUID = -1208465008;

    /**
     * The reference instance of <code>activity.community</code>
     */
    public static final Community COMMUNITY = new Community();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommunityRecord> getRecordType() {
        return CommunityRecord.class;
    }

    /**
     * The column <code>activity.community.id</code>. 编号
     */
    public final TableField<CommunityRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>activity.community.name</code>. 社团名称
     */
    public final TableField<CommunityRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "社团名称");

    /**
     * The column <code>activity.community.description</code>. 社团标语
     */
    public final TableField<CommunityRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "社团标语");

    /**
     * The column <code>activity.community.background</code>. 社团背景图片
     */
    public final TableField<CommunityRecord, String> BACKGROUND = createField("background", org.jooq.impl.SQLDataType.VARCHAR(255), this, "社团背景图片");

    /**
     * The column <code>activity.community.status</code>. 社团状态 0 ： 草稿、1 待审核、2、拒绝、 9审核通过
     */
    public final TableField<CommunityRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "社团状态 0 ： 草稿、1 待审核、2、拒绝、 9审核通过");

    /**
     * The column <code>activity.community.using_score</code>. 是否开启积分功能
     */
    public final TableField<CommunityRecord, Boolean> USING_SCORE = createField("using_score", org.jooq.impl.SQLDataType.BIT, this, "是否开启积分功能");

    /**
     * The column <code>activity.community.avatar</code>. 社团小图标
     */
    public final TableField<CommunityRecord, String> AVATAR = createField("avatar", org.jooq.impl.SQLDataType.VARCHAR(255), this, "社团小图标");

    /**
     * The column <code>activity.community.vip_amount</code>. 会费金额 0：不开启会员功能， &gt;0 :开启会员功能
     */
    public final TableField<CommunityRecord, Double> VIP_AMOUNT = createField("vip_amount", org.jooq.impl.SQLDataType.DOUBLE, this, "会费金额 0：不开启会员功能， >0 :开启会员功能");

    /**
     * The column <code>activity.community.created_by</code>. 社团创建人
     */
    public final TableField<CommunityRecord, Integer> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.INTEGER, this, "社团创建人");

    /**
     * The column <code>activity.community.created</code>. 创建时间
     */
    public final TableField<CommunityRecord, Integer> CREATED = createField("created", org.jooq.impl.SQLDataType.INTEGER, this, "创建时间");

    /**
     * The column <code>activity.community.address</code>. 社团地址
     */
    public final TableField<CommunityRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "社团地址");

    /**
     * The column <code>activity.community.contact</code>. 社团联系电话
     */
    public final TableField<CommunityRecord, String> CONTACT = createField("contact", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "社团联系电话");

    /**
     * The column <code>activity.community.company</code>. 社团公司名称
     */
    public final TableField<CommunityRecord, String> COMPANY = createField("company", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "社团公司名称");

    /**
     * The column <code>activity.community.about</code>. 关于社团富文本
     */
    public final TableField<CommunityRecord, String> ABOUT = createField("about", org.jooq.impl.SQLDataType.CLOB, this, "关于社团富文本");

    /**
     * Create a <code>activity.community</code> table reference
     */
    public Community() {
        this(DSL.name("community"), null);
    }

    /**
     * Create an aliased <code>activity.community</code> table reference
     */
    public Community(String alias) {
        this(DSL.name(alias), COMMUNITY);
    }

    /**
     * Create an aliased <code>activity.community</code> table reference
     */
    public Community(Name alias) {
        this(alias, COMMUNITY);
    }

    private Community(Name alias, Table<CommunityRecord> aliased) {
        this(alias, aliased, null);
    }

    private Community(Name alias, Table<CommunityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "社团");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Activity.ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.COMMUNITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CommunityRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COMMUNITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CommunityRecord> getPrimaryKey() {
        return Keys.KEY_COMMUNITY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommunityRecord>> getKeys() {
        return Arrays.<UniqueKey<CommunityRecord>>asList(Keys.KEY_COMMUNITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Community as(String alias) {
        return new Community(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Community as(Name alias) {
        return new Community(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Community rename(String name) {
        return new Community(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Community rename(Name name) {
        return new Community(name, null);
    }
}
