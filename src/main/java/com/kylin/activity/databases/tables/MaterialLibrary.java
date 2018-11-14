/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.MaterialLibraryRecord;

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
 * 素材库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MaterialLibrary extends TableImpl<MaterialLibraryRecord> {

    private static final long serialVersionUID = 853931347;

    /**
     * The reference instance of <code>material_library</code>
     */
    public static final MaterialLibrary MATERIAL_LIBRARY = new MaterialLibrary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MaterialLibraryRecord> getRecordType() {
        return MaterialLibraryRecord.class;
    }

    /**
     * The column <code>material_library.id</code>.
     */
    public final TableField<MaterialLibraryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>material_library.name</code>.
     */
    public final TableField<MaterialLibraryRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>material_library.created</code>.
     */
    public final TableField<MaterialLibraryRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>material_library.sequence</code>.
     */
    public final TableField<MaterialLibraryRecord, Integer> SEQUENCE = createField("sequence", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>material_library.category</code>.
     */
    public final TableField<MaterialLibraryRecord, String> CATEGORY = createField("category", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * Create a <code>material_library</code> table reference
     */
    public MaterialLibrary() {
        this(DSL.name("material_library"), null);
    }

    /**
     * Create an aliased <code>material_library</code> table reference
     */
    public MaterialLibrary(String alias) {
        this(DSL.name(alias), MATERIAL_LIBRARY);
    }

    /**
     * Create an aliased <code>material_library</code> table reference
     */
    public MaterialLibrary(Name alias) {
        this(alias, MATERIAL_LIBRARY);
    }

    private MaterialLibrary(Name alias, Table<MaterialLibraryRecord> aliased) {
        this(alias, aliased, null);
    }

    private MaterialLibrary(Name alias, Table<MaterialLibraryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "素材库");
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
        return Arrays.<Index>asList(Indexes.MATERIAL_LIBRARY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MaterialLibraryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MATERIAL_LIBRARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MaterialLibraryRecord> getPrimaryKey() {
        return Keys.KEY_MATERIAL_LIBRARY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MaterialLibraryRecord>> getKeys() {
        return Arrays.<UniqueKey<MaterialLibraryRecord>>asList(Keys.KEY_MATERIAL_LIBRARY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterialLibrary as(String alias) {
        return new MaterialLibrary(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterialLibrary as(Name alias) {
        return new MaterialLibrary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MaterialLibrary rename(String name) {
        return new MaterialLibrary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MaterialLibrary rename(Name name) {
        return new MaterialLibrary(name, null);
    }
}
