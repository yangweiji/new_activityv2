/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.TrainingCampRecord;

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
public class TrainingCamp extends TableImpl<TrainingCampRecord> {

    private static final long serialVersionUID = 1384330343;

    /**
     * The reference instance of <code>training_camp</code>
     */
    public static final TrainingCamp TRAINING_CAMP = new TrainingCamp();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TrainingCampRecord> getRecordType() {
        return TrainingCampRecord.class;
    }

    /**
     * The column <code>training_camp.id</code>. 编号
     */
    public final TableField<TrainingCampRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>training_camp.camp_name</code>. 训练营名称
     */
    public final TableField<TrainingCampRecord, String> CAMP_NAME = createField("camp_name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "训练营名称");

    /**
     * The column <code>training_camp.camp_note</code>. 训练营备注
     */
    public final TableField<TrainingCampRecord, String> CAMP_NOTE = createField("camp_note", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "训练营备注");

    /**
     * The column <code>training_camp.modified</code>. 编辑时间
     */
    public final TableField<TrainingCampRecord, Timestamp> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP, this, "编辑时间");

    /**
     * The column <code>training_camp.modified_by</code>. 编辑用户编号
     */
    public final TableField<TrainingCampRecord, Integer> MODIFIED_BY = createField("modified_by", org.jooq.impl.SQLDataType.INTEGER, this, "编辑用户编号");

    /**
     * The column <code>training_camp.community_id</code>. 团体组织编号
     */
    public final TableField<TrainingCampRecord, Integer> COMMUNITY_ID = createField("community_id", org.jooq.impl.SQLDataType.INTEGER, this, "团体组织编号");

    /**
     * Create a <code>training_camp</code> table reference
     */
    public TrainingCamp() {
        this(DSL.name("training_camp"), null);
    }

    /**
     * Create an aliased <code>training_camp</code> table reference
     */
    public TrainingCamp(String alias) {
        this(DSL.name(alias), TRAINING_CAMP);
    }

    /**
     * Create an aliased <code>training_camp</code> table reference
     */
    public TrainingCamp(Name alias) {
        this(alias, TRAINING_CAMP);
    }

    private TrainingCamp(Name alias, Table<TrainingCampRecord> aliased) {
        this(alias, aliased, null);
    }

    private TrainingCamp(Name alias, Table<TrainingCampRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
        return Arrays.<Index>asList(Indexes.TRAINING_CAMP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TrainingCampRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TRAINING_CAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TrainingCampRecord> getPrimaryKey() {
        return Keys.KEY_TRAINING_CAMP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TrainingCampRecord>> getKeys() {
        return Arrays.<UniqueKey<TrainingCampRecord>>asList(Keys.KEY_TRAINING_CAMP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrainingCamp as(String alias) {
        return new TrainingCamp(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrainingCamp as(Name alias) {
        return new TrainingCamp(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TrainingCamp rename(String name) {
        return new TrainingCamp(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TrainingCamp rename(Name name) {
        return new TrainingCamp(name, null);
    }
}