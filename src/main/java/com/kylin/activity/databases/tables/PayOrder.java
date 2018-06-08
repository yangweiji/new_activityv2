/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables;


import com.kylin.activity.databases.Activityv2;
import com.kylin.activity.databases.Indexes;
import com.kylin.activity.databases.Keys;
import com.kylin.activity.databases.tables.records.PayOrderRecord;

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
 * 付款订单
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PayOrder extends TableImpl<PayOrderRecord> {

    private static final long serialVersionUID = 2009290072;

    /**
     * The reference instance of <code>activityv2.pay_order</code>
     */
    public static final PayOrder PAY_ORDER = new PayOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PayOrderRecord> getRecordType() {
        return PayOrderRecord.class;
    }

    /**
     * The column <code>activityv2.pay_order.id</code>. 主键编号
     */
    public final TableField<PayOrderRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "主键编号");

    /**
     * The column <code>activityv2.pay_order.user_id</code>. 付款用户id
     */
    public final TableField<PayOrderRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "付款用户id");

    /**
     * The column <code>activityv2.pay_order.activity_id</code>. 付款活动id
     */
    public final TableField<PayOrderRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "付款活动id");

    /**
     * The column <code>activityv2.pay_order.activity_ticket_id</code>. 付款活动票id
     */
    public final TableField<PayOrderRecord, Integer> ACTIVITY_TICKET_ID = createField("activity_ticket_id", org.jooq.impl.SQLDataType.INTEGER, this, "付款活动票id");

    /**
     * The column <code>activityv2.pay_order.title</code>. 付款商品标题
     */
    public final TableField<PayOrderRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255), this, "付款商品标题");

    /**
     * The column <code>activityv2.pay_order.body</code>. 付款说明
     */
    public final TableField<PayOrderRecord, String> BODY = createField("body", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "付款说明");

    /**
     * The column <code>activityv2.pay_order.price</code>. 付款金额
     */
    public final TableField<PayOrderRecord, BigDecimal> PRICE = createField("price", org.jooq.impl.SQLDataType.DECIMAL(18, 2), this, "付款金额");

    /**
     * The column <code>activityv2.pay_order.pay_time</code>.
     */
    public final TableField<PayOrderRecord, Timestamp> PAY_TIME = createField("pay_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activityv2.pay_order.cancel_time</code>.
     */
    public final TableField<PayOrderRecord, Timestamp> CANCEL_TIME = createField("cancel_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activityv2.pay_order.status</code>. 订单状态 1 ：创建完成未付款， 2 ： 完成付款， -1： 订单取消
     */
    public final TableField<PayOrderRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "订单状态 1 ：创建完成未付款， 2 ： 完成付款， -1： 订单取消");

    /**
     * The column <code>activityv2.pay_order.extenal_id</code>. 微信统一订单id
     */
    public final TableField<PayOrderRecord, String> EXTENAL_ID = createField("extenal_id", org.jooq.impl.SQLDataType.VARCHAR(255), this, "微信统一订单id");

    /**
     * The column <code>activityv2.pay_order.created</code>.
     */
    public final TableField<PayOrderRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activityv2.pay_order.other_info</code>. 活动报名时的报名json信息
     */
    public final TableField<PayOrderRecord, String> OTHER_INFO = createField("other_info", org.jooq.impl.SQLDataType.VARCHAR(4000), this, "活动报名时的报名json信息");

    /**
     * The column <code>activityv2.pay_order.refund_trade_no</code>.
     */
    public final TableField<PayOrderRecord, String> REFUND_TRADE_NO = createField("refund_trade_no", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>activityv2.pay_order.refund_time</code>.
     */
    public final TableField<PayOrderRecord, Timestamp> REFUND_TIME = createField("refund_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activityv2.pay_order.refund_status</code>.
     */
    public final TableField<PayOrderRecord, Integer> REFUND_STATUS = createField("refund_status", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>activityv2.pay_order.community_id</code>.
     */
    public final TableField<PayOrderRecord, Integer> COMMUNITY_ID = createField("community_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>activityv2.pay_order</code> table reference
     */
    public PayOrder() {
        this(DSL.name("pay_order"), null);
    }

    /**
     * Create an aliased <code>activityv2.pay_order</code> table reference
     */
    public PayOrder(String alias) {
        this(DSL.name(alias), PAY_ORDER);
    }

    /**
     * Create an aliased <code>activityv2.pay_order</code> table reference
     */
    public PayOrder(Name alias) {
        this(alias, PAY_ORDER);
    }

    private PayOrder(Name alias, Table<PayOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private PayOrder(Name alias, Table<PayOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "付款订单");
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
        return Arrays.<Index>asList(Indexes.PAY_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PayOrderRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PAY_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PayOrderRecord> getPrimaryKey() {
        return Keys.KEY_PAY_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PayOrderRecord>> getKeys() {
        return Arrays.<UniqueKey<PayOrderRecord>>asList(Keys.KEY_PAY_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayOrder as(String alias) {
        return new PayOrder(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayOrder as(Name alias) {
        return new PayOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PayOrder rename(String name) {
        return new PayOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PayOrder rename(Name name) {
        return new PayOrder(name, null);
    }
}
