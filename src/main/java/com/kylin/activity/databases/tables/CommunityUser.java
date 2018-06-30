/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activityv2;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.CommunityUserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class CommunityUser extends TableImpl<CommunityUserRecord> {

    private static final long serialVersionUID = -1058592487;

    /**
     * The reference instance of <code>activityv2.community_user</code>
     */
    public static final CommunityUser COMMUNITY_USER = new CommunityUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommunityUserRecord> getRecordType() {
        return CommunityUserRecord.class;
    }

    /**
     * The column <code>activityv2.community_user.id</code>.
     */
    public final TableField<CommunityUserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>activityv2.community_user.community_id</code>.
     */
    public final TableField<CommunityUserRecord, Integer> COMMUNITY_ID = createField("community_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.community_user.user_id</code>.
     */
    public final TableField<CommunityUserRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.community_user.role</code>. 成员在社团中的角色 admin、publish
     */
    public final TableField<CommunityUserRecord, String> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR(255), this, "成员在社团中的角色 admin、publish");

    /**
     * The column <code>activityv2.community_user.created</code>. 加入时间
     */
    public final TableField<CommunityUserRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "加入时间");

    /**
     * The column <code>activityv2.community_user.level</code>.
     */
    public final TableField<CommunityUserRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.community_user.is_black</code>. 是否黑名单用户
     */
    public final TableField<CommunityUserRecord, Boolean> IS_BLACK = createField("is_black", org.jooq.impl.SQLDataType.BIT.defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "是否黑名单用户");

    /**
     * Create a <code>activityv2.community_user</code> table reference
     */
    public CommunityUser() {
        this(DSL.name("community_user"), null);
    }

    /**
     * Create an aliased <code>activityv2.community_user</code> table reference
     */
    public CommunityUser(String alias) {
        this(DSL.name(alias), COMMUNITY_USER);
    }

    /**
     * Create an aliased <code>activityv2.community_user</code> table reference
     */
    public CommunityUser(Name alias) {
        this(alias, COMMUNITY_USER);
    }

    private CommunityUser(Name alias, Table<CommunityUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommunityUser(Name alias, Table<CommunityUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "社团成员");
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
        return Arrays.<Index>asList(Indexes.COMMUNITY_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CommunityUserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COMMUNITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CommunityUserRecord> getPrimaryKey() {
        return Keys.KEY_COMMUNITY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommunityUserRecord>> getKeys() {
        return Arrays.<UniqueKey<CommunityUserRecord>>asList(Keys.KEY_COMMUNITY_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUser as(String alias) {
        return new CommunityUser(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityUser as(Name alias) {
        return new CommunityUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CommunityUser rename(String name) {
        return new CommunityUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CommunityUser rename(Name name) {
        return new CommunityUser(name, null);
    }
}
