/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.ActivityFavoriteRecord;

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
 * 我喜欢的活动列表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityFavorite extends TableImpl<ActivityFavoriteRecord> {

    private static final long serialVersionUID = 84004874;

    /**
     * The reference instance of <code>activity_favorite</code>
     */
    public static final ActivityFavorite ACTIVITY_FAVORITE = new ActivityFavorite();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityFavoriteRecord> getRecordType() {
        return ActivityFavoriteRecord.class;
    }

    /**
     * The column <code>activity_favorite.id</code>. 编号
     */
    public final TableField<ActivityFavoriteRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>activity_favorite.activity_id</code>. 活动id
     */
    public final TableField<ActivityFavoriteRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "活动id");

    /**
     * The column <code>activity_favorite.user_id</code>. 用户id
     */
    public final TableField<ActivityFavoriteRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "用户id");

    /**
     * The column <code>activity_favorite.created</code>. 创建时间
     */
    public final TableField<ActivityFavoriteRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * Create a <code>activity_favorite</code> table reference
     */
    public ActivityFavorite() {
        this(DSL.name("activity_favorite"), null);
    }

    /**
     * Create an aliased <code>activity_favorite</code> table reference
     */
    public ActivityFavorite(String alias) {
        this(DSL.name(alias), ACTIVITY_FAVORITE);
    }

    /**
     * Create an aliased <code>activity_favorite</code> table reference
     */
    public ActivityFavorite(Name alias) {
        this(alias, ACTIVITY_FAVORITE);
    }

    private ActivityFavorite(Name alias, Table<ActivityFavoriteRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivityFavorite(Name alias, Table<ActivityFavoriteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "我喜欢的活动列表");
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
        return Arrays.<Index>asList(Indexes.ACTIVITY_FAVORITE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivityFavoriteRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ACTIVITY_FAVORITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActivityFavoriteRecord> getPrimaryKey() {
        return Keys.KEY_ACTIVITY_FAVORITE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivityFavoriteRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivityFavoriteRecord>>asList(Keys.KEY_ACTIVITY_FAVORITE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityFavorite as(String alias) {
        return new ActivityFavorite(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityFavorite as(Name alias) {
        return new ActivityFavorite(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityFavorite rename(String name) {
        return new ActivityFavorite(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityFavorite rename(Name name) {
        return new ActivityFavorite(name, null);
    }
}
