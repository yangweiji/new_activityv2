/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.daos;


import com.kylin.activity.databases.tables.PayOrder;
import com.kylin.activity.databases.tables.records.PayOrderRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
@Repository
public class PayOrderDao extends DAOImpl<PayOrderRecord, com.kylin.activity.databases.tables.pojos.PayOrder, Integer> {

    /**
     * Create a new PayOrderDao without any configuration
     */
    public PayOrderDao() {
        super(PayOrder.PAY_ORDER, com.kylin.activity.databases.tables.pojos.PayOrder.class);
    }

    /**
     * Create a new PayOrderDao with an attached configuration
     */
    @Autowired
    public PayOrderDao(Configuration configuration) {
        super(PayOrder.PAY_ORDER, com.kylin.activity.databases.tables.pojos.PayOrder.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.kylin.activity.databases.tables.pojos.PayOrder object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchById(Integer... values) {
        return fetch(PayOrder.PAY_ORDER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.PayOrder fetchOneById(Integer value) {
        return fetchOne(PayOrder.PAY_ORDER.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByUserId(Integer... values) {
        return fetch(PayOrder.PAY_ORDER.USER_ID, values);
    }

    /**
     * Fetch records that have <code>activity_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByActivityId(Integer... values) {
        return fetch(PayOrder.PAY_ORDER.ACTIVITY_ID, values);
    }

    /**
     * Fetch records that have <code>activity_ticket_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByActivityTicketId(Integer... values) {
        return fetch(PayOrder.PAY_ORDER.ACTIVITY_TICKET_ID, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByTitle(String... values) {
        return fetch(PayOrder.PAY_ORDER.TITLE, values);
    }

    /**
     * Fetch records that have <code>body IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByBody(String... values) {
        return fetch(PayOrder.PAY_ORDER.BODY, values);
    }

    /**
     * Fetch records that have <code>price IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByPrice(BigDecimal... values) {
        return fetch(PayOrder.PAY_ORDER.PRICE, values);
    }

    /**
     * Fetch records that have <code>pay_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByPayTime(Timestamp... values) {
        return fetch(PayOrder.PAY_ORDER.PAY_TIME, values);
    }

    /**
     * Fetch records that have <code>cancel_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByCancelTime(Timestamp... values) {
        return fetch(PayOrder.PAY_ORDER.CANCEL_TIME, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByStatus(Integer... values) {
        return fetch(PayOrder.PAY_ORDER.STATUS, values);
    }

    /**
     * Fetch records that have <code>extenal_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByExtenalId(String... values) {
        return fetch(PayOrder.PAY_ORDER.EXTENAL_ID, values);
    }

    /**
     * Fetch records that have <code>created IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByCreated(Timestamp... values) {
        return fetch(PayOrder.PAY_ORDER.CREATED, values);
    }

    /**
     * Fetch records that have <code>other_info IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.PayOrder> fetchByOtherInfo(String... values) {
        return fetch(PayOrder.PAY_ORDER.OTHER_INFO, values);
    }
}
