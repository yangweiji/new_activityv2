/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activity;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.ActionHistoryRecord;

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
public class ActionHistory extends TableImpl<ActionHistoryRecord> {

    private static final long serialVersionUID = 1323668493;

    /**
     * The reference instance of <code>activity.action_history</code>
     */
    public static final ActionHistory ACTION_HISTORY = new ActionHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActionHistoryRecord> getRecordType() {
        return ActionHistoryRecord.class;
    }

    /**
     * The column <code>activity.action_history.id</code>.
     */
    public final TableField<ActionHistoryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>activity.action_history.axtenal_id</code>.
     */
    public final TableField<ActionHistoryRecord, Integer> AXTENAL_ID = createField("axtenal_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activity.action_history.action</code>.
     */
    public final TableField<ActionHistoryRecord, Integer> ACTION = createField("action", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activity.action_history.memo</code>.
     */
    public final TableField<ActionHistoryRecord, String> MEMO = createField("memo", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "");

    /**
     * The column <code>activity.action_history.created</code>.
     */
    public final TableField<ActionHistoryRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activity.action_history.created_by</code>.
     */
    public final TableField<ActionHistoryRecord, Integer> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>activity.action_history</code> table reference
     */
    public ActionHistory() {
        this(DSL.name("action_history"), null);
    }

    /**
     * Create an aliased <code>activity.action_history</code> table reference
     */
    public ActionHistory(String alias) {
        this(DSL.name(alias), ACTION_HISTORY);
    }

    /**
     * Create an aliased <code>activity.action_history</code> table reference
     */
    public ActionHistory(Name alias) {
        this(alias, ACTION_HISTORY);
    }

    private ActionHistory(Name alias, Table<ActionHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActionHistory(Name alias, Table<ActionHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
        return Arrays.<Index>asList(Indexes.ACTION_HISTORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActionHistoryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ACTION_HISTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActionHistoryRecord> getPrimaryKey() {
        return Keys.KEY_ACTION_HISTORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActionHistoryRecord>> getKeys() {
        return Arrays.<UniqueKey<ActionHistoryRecord>>asList(Keys.KEY_ACTION_HISTORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistory as(String alias) {
        return new ActionHistory(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHistory as(Name alias) {
        return new ActionHistory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActionHistory rename(String name) {
        return new ActionHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActionHistory rename(Name name) {
        return new ActionHistory(name, null);
    }
}
