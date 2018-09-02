/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.daos;


import com.kylin.activity.databases.tables.Community;
import com.kylin.activity.databases.tables.records.CommunityRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
@Repository
public class CommunityDao extends DAOImpl<CommunityRecord, com.kylin.activity.databases.tables.pojos.Community, Integer> {

    /**
     * Create a new CommunityDao without any configuration
     */
    public CommunityDao() {
        super(Community.COMMUNITY, com.kylin.activity.databases.tables.pojos.Community.class);
    }

    /**
     * Create a new CommunityDao with an attached configuration
     */
    @Autowired
    public CommunityDao(Configuration configuration) {
        super(Community.COMMUNITY, com.kylin.activity.databases.tables.pojos.Community.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.kylin.activity.databases.tables.pojos.Community object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchById(Integer... values) {
        return fetch(Community.COMMUNITY.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.Community fetchOneById(Integer value) {
        return fetchOne(Community.COMMUNITY.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByName(String... values) {
        return fetch(Community.COMMUNITY.NAME, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByDescription(String... values) {
        return fetch(Community.COMMUNITY.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>background IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByBackground(String... values) {
        return fetch(Community.COMMUNITY.BACKGROUND, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByStatus(Integer... values) {
        return fetch(Community.COMMUNITY.STATUS, values);
    }

    /**
     * Fetch records that have <code>using_score IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByUsingScore(Boolean... values) {
        return fetch(Community.COMMUNITY.USING_SCORE, values);
    }

    /**
     * Fetch records that have <code>avatar IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByAvatar(String... values) {
        return fetch(Community.COMMUNITY.AVATAR, values);
    }

    /**
     * Fetch records that have <code>is_vip IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByIsVip(Boolean... values) {
        return fetch(Community.COMMUNITY.IS_VIP, values);
    }

    /**
     * Fetch records that have <code>vip_agreement IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByVipAgreement(String... values) {
        return fetch(Community.COMMUNITY.VIP_AGREEMENT, values);
    }

    /**
     * Fetch records that have <code>vip_amount IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByVipAmount(Double... values) {
        return fetch(Community.COMMUNITY.VIP_AMOUNT, values);
    }

    /**
     * Fetch records that have <code>created_by IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByCreatedBy(Integer... values) {
        return fetch(Community.COMMUNITY.CREATED_BY, values);
    }

    /**
     * Fetch records that have <code>created IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByCreated(Timestamp... values) {
        return fetch(Community.COMMUNITY.CREATED, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByAddress(String... values) {
        return fetch(Community.COMMUNITY.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>contact IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByContact(String... values) {
        return fetch(Community.COMMUNITY.CONTACT, values);
    }

    /**
     * Fetch records that have <code>company IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByCompany(String... values) {
        return fetch(Community.COMMUNITY.COMPANY, values);
    }

    /**
     * Fetch records that have <code>about IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByAbout(String... values) {
        return fetch(Community.COMMUNITY.ABOUT, values);
    }

    /**
     * Fetch records that have <code>control_name IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByControlName(String... values) {
        return fetch(Community.COMMUNITY.CONTROL_NAME, values);
    }

    /**
     * Fetch records that have <code>business_license IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByBusinessLicense(String... values) {
        return fetch(Community.COMMUNITY.BUSINESS_LICENSE, values);
    }

    /**
     * Fetch records that have <code>manager_phone_number IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByManagerPhoneNumber(String... values) {
        return fetch(Community.COMMUNITY.MANAGER_PHONE_NUMBER, values);
    }

    /**
     * Fetch records that have <code>count_people IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByCountPeople(Integer... values) {
        return fetch(Community.COMMUNITY.COUNT_PEOPLE, values);
    }

    /**
     * Fetch records that have <code>can_refund IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.Community> fetchByCanRefund(Boolean... values) {
        return fetch(Community.COMMUNITY.CAN_REFUND, values);
    }
}
