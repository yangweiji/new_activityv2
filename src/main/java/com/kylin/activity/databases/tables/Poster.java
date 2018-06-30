/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activityv2;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.PosterRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class Poster extends TableImpl<PosterRecord> {

    private static final long serialVersionUID = 1867249937;

    /**
     * The reference instance of <code>activityv2.poster</code>
     */
    public static final Poster POSTER = new Poster();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PosterRecord> getRecordType() {
        return PosterRecord.class;
    }

    /**
     * The column <code>activityv2.poster.id</code>. 主键
     */
    public final TableField<PosterRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>activityv2.poster.title</code>. 海报标题，当海报链接活动时，自动使用活动的标题
     */
    public final TableField<PosterRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(10244), this, "海报标题，当海报链接活动时，自动使用活动的标题");

    /**
     * The column <code>activityv2.poster.avatar</code>. 默认海报背景图
     */
    public final TableField<PosterRecord, String> AVATAR = createField("avatar", org.jooq.impl.SQLDataType.VARCHAR(255), this, "默认海报背景图");

    /**
     * The column <code>activityv2.poster.mobile_avatar</code>. 收集显示海报图，为空时使用avatar的值
     */
    public final TableField<PosterRecord, String> MOBILE_AVATAR = createField("mobile_avatar", org.jooq.impl.SQLDataType.VARCHAR(255), this, "收集显示海报图，为空时使用avatar的值");

    /**
     * The column <code>activityv2.poster.link</code>. 海报外部链接，当关联活动时可以为空，初次实现时可以不考虑该字段，只做活动海报
     */
    public final TableField<PosterRecord, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR(255), this, "海报外部链接，当关联活动时可以为空，初次实现时可以不考虑该字段，只做活动海报");

    /**
     * The column <code>activityv2.poster.activity_id</code>. 海报关联的活动Id
     */
    public final TableField<PosterRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "海报关联的活动Id");

    /**
     * The column <code>activityv2.poster.created</code>. 创建时间
     */
    public final TableField<PosterRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * The column <code>activityv2.poster.poster_type</code>. 海报类型， 与活动关联时，自动使用活动的类型
     */
    public final TableField<PosterRecord, String> POSTER_TYPE = createField("poster_type", org.jooq.impl.SQLDataType.VARCHAR(255), this, "海报类型， 与活动关联时，自动使用活动的类型");

    /**
     * The column <code>activityv2.poster.show</code>. 是否显示在首页，用户先设置是否展示在首页，然后设置展示的顺序
     */
    public final TableField<PosterRecord, Boolean> SHOW = createField("show", org.jooq.impl.SQLDataType.BIT, this, "是否显示在首页，用户先设置是否展示在首页，然后设置展示的顺序");

    /**
     * The column <code>activityv2.poster.sequence</code>. 海报排序，排序相同时按created倒序排
     */
    public final TableField<PosterRecord, Integer> SEQUENCE = createField("sequence", org.jooq.impl.SQLDataType.INTEGER, this, "海报排序，排序相同时按created倒序排");

    /**
     * Create a <code>activityv2.poster</code> table reference
     */
    public Poster() {
        this(DSL.name("poster"), null);
    }

    /**
     * Create an aliased <code>activityv2.poster</code> table reference
     */
    public Poster(String alias) {
        this(DSL.name(alias), POSTER);
    }

    /**
     * Create an aliased <code>activityv2.poster</code> table reference
     */
    public Poster(Name alias) {
        this(alias, POSTER);
    }

    private Poster(Name alias, Table<PosterRecord> aliased) {
        this(alias, aliased, null);
    }

    private Poster(Name alias, Table<PosterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "海报表");
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
        return Arrays.<Index>asList(Indexes.POSTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PosterRecord, Integer> getIdentity() {
        return Keys.IDENTITY_POSTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PosterRecord> getPrimaryKey() {
        return Keys.KEY_POSTER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PosterRecord>> getKeys() {
        return Arrays.<UniqueKey<PosterRecord>>asList(Keys.KEY_POSTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poster as(String alias) {
        return new Poster(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poster as(Name alias) {
        return new Poster(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Poster rename(String name) {
        return new Poster(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Poster rename(Name name) {
        return new Poster(name, null);
    }
}
