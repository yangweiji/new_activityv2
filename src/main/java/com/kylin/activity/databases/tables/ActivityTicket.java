/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.DefaultSchema;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.ActivityTicketRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


/**
 * 活动门票
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityTicket extends TableImpl<ActivityTicketRecord> {

    private static final long serialVersionUID = -112578649;

    /**
     * The reference instance of <code>activity_ticket</code>
     */
    public static final ActivityTicket ACTIVITY_TICKET = new ActivityTicket();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityTicketRecord> getRecordType() {
        return ActivityTicketRecord.class;
    }

    /**
     * The column <code>activity_ticket.id</code>. 编号
     */
    public final TableField<ActivityTicketRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "编号");

    /**
     * The column <code>activity_ticket.activity_id</code>. 活动编号
     */
    public final TableField<ActivityTicketRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "活动编号");

    /**
     * The column <code>activity_ticket.title</code>. 活动票标题
     */
    public final TableField<ActivityTicketRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255), this, "活动票标题");

    /**
     * The column <code>activity_ticket.memo</code>. 活动票说明
     */
    public final TableField<ActivityTicketRecord, String> MEMO = createField("memo", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "活动票说明");

    /**
     * The column <code>activity_ticket.price</code>. 门票价格
     */
    public final TableField<ActivityTicketRecord, BigDecimal> PRICE = createField("price", org.jooq.impl.SQLDataType.DECIMAL(18, 2), this, "门票价格");

    /**
     * The column <code>activity_ticket.count</code>. 票张数 0表示不限制票的张数
     */
    public final TableField<ActivityTicketRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER, this, "票张数 0表示不限制票的张数");

    /**
     * The column <code>activity_ticket.user_level</code>. 要求会员级别，0表示无限制
     */
    public final TableField<ActivityTicketRecord, Integer> USER_LEVEL = createField("user_level", org.jooq.impl.SQLDataType.INTEGER, this, "要求会员级别，0表示无限制");

    /**
     * The column <code>activity_ticket.score</code>. 可用积分抵扣，0不能抵扣， &gt;0 可抵扣
     */
    public final TableField<ActivityTicketRecord, Integer> SCORE = createField("score", org.jooq.impl.SQLDataType.INTEGER, this, "可用积分抵扣，0不能抵扣， >0 可抵扣");

    /**
     * Create a <code>activity_ticket</code> table reference
     */
    public ActivityTicket() {
        this(DSL.name("activity_ticket"), null);
    }

    /**
     * Create an aliased <code>activity_ticket</code> table reference
     */
    public ActivityTicket(String alias) {
        this(DSL.name(alias), ACTIVITY_TICKET);
    }

    /**
     * Create an aliased <code>activity_ticket</code> table reference
     */
    public ActivityTicket(Name alias) {
        this(alias, ACTIVITY_TICKET);
    }

    private ActivityTicket(Name alias, Table<ActivityTicketRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivityTicket(Name alias, Table<ActivityTicketRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "活动门票");
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
        return Arrays.<Index>asList(Indexes.ACTIVITY_TICKET_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivityTicketRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ACTIVITY_TICKET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ActivityTicketRecord> getPrimaryKey() {
        return Keys.KEY_ACTIVITY_TICKET_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivityTicketRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivityTicketRecord>>asList(Keys.KEY_ACTIVITY_TICKET_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityTicket as(String alias) {
        return new ActivityTicket(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityTicket as(Name alias) {
        return new ActivityTicket(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTicket rename(String name) {
        return new ActivityTicket(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTicket rename(Name name) {
        return new ActivityTicket(name, null);
    }
}
