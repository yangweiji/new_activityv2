/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.daos;


import com.kylin.activity.databases.tables.User;
import com.kylin.activity.databases.tables.records.UserRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 用户表
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
public class UserDao extends DAOImpl<UserRecord, com.kylin.activity.databases.tables.pojos.User, Integer> {

    /**
     * Create a new UserDao without any configuration
     */
    public UserDao() {
        super(User.USER, com.kylin.activity.databases.tables.pojos.User.class);
    }

    /**
     * Create a new UserDao with an attached configuration
     */
    @Autowired
    public UserDao(Configuration configuration) {
        super(User.USER, com.kylin.activity.databases.tables.pojos.User.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.kylin.activity.databases.tables.pojos.User object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchById(Integer... values) {
        return fetch(User.USER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.User fetchOneById(Integer value) {
        return fetchOne(User.USER.ID, value);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByUsername(String... values) {
        return fetch(User.USER.USERNAME, values);
    }

    /**
     * Fetch a unique record that has <code>username = value</code>
     */
    public com.kylin.activity.databases.tables.pojos.User fetchOneByUsername(String value) {
        return fetchOne(User.USER.USERNAME, value);
    }

    /**
     * Fetch records that have <code>displayname IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByDisplayname(String... values) {
        return fetch(User.USER.DISPLAYNAME, values);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByPassword(String... values) {
        return fetch(User.USER.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>role IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByRole(String... values) {
        return fetch(User.USER.ROLE, values);
    }

    /**
     * Fetch records that have <code>enabled IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByEnabled(Boolean... values) {
        return fetch(User.USER.ENABLED, values);
    }

    /**
     * Fetch records that have <code>gender IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByGender(Integer... values) {
        return fetch(User.USER.GENDER, values);
    }

    /**
     * Fetch records that have <code>created IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByCreated(Timestamp... values) {
        return fetch(User.USER.CREATED, values);
    }

    /**
     * Fetch records that have <code>level IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByLevel(Integer... values) {
        return fetch(User.USER.LEVEL, values);
    }

    /**
     * Fetch records that have <code>id_card IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByIdCard(String... values) {
        return fetch(User.USER.ID_CARD, values);
    }

    /**
     * Fetch records that have <code>real_name IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByRealName(String... values) {
        return fetch(User.USER.REAL_NAME, values);
    }

    /**
     * Fetch records that have <code>real_time IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByRealTime(Timestamp... values) {
        return fetch(User.USER.REAL_TIME, values);
    }

    /**
     * Fetch records that have <code>is_real IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByIsReal(Boolean... values) {
        return fetch(User.USER.IS_REAL, values);
    }

    /**
     * Fetch records that have <code>open_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByOpenId(String... values) {
        return fetch(User.USER.OPEN_ID, values);
    }

    /**
     * Fetch records that have <code>avatar IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByAvatar(String... values) {
        return fetch(User.USER.AVATAR, values);
    }

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByEmail(String... values) {
        return fetch(User.USER.EMAIL, values);
    }

    /**
     * Fetch records that have <code>work_company IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByWorkCompany(String... values) {
        return fetch(User.USER.WORK_COMPANY, values);
    }

    /**
     * Fetch records that have <code>is_party IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByIsParty(Boolean... values) {
        return fetch(User.USER.IS_PARTY, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByAddress(String... values) {
        return fetch(User.USER.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>blood_type IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByBloodType(String... values) {
        return fetch(User.USER.BLOOD_TYPE, values);
    }

    /**
     * Fetch records that have <code>clothing_size IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByClothingSize(String... values) {
        return fetch(User.USER.CLOTHING_SIZE, values);
    }

    /**
     * Fetch records that have <code>occupation IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByOccupation(String... values) {
        return fetch(User.USER.OCCUPATION, values);
    }

    /**
     * Fetch records that have <code>emergency_contact_name IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByEmergencyContactName(String... values) {
        return fetch(User.USER.EMERGENCY_CONTACT_NAME, values);
    }

    /**
     * Fetch records that have <code>emergency_contact_mobile IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByEmergencyContactMobile(String... values) {
        return fetch(User.USER.EMERGENCY_CONTACT_MOBILE, values);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByWechatId(String... values) {
        return fetch(User.USER.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>union_id IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByUnionId(String... values) {
        return fetch(User.USER.UNION_ID, values);
    }

    /**
     * Fetch records that have <code>nick_name IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByNickName(String... values) {
        return fetch(User.USER.NICK_NAME, values);
    }

    /**
     * Fetch records that have <code>mobile IN (values)</code>
     */
    public List<com.kylin.activity.databases.tables.pojos.User> fetchByMobile(String... values) {
        return fetch(User.USER.MOBILE, values);
    }
}
