/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.Community;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


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
public class CommunityRecord extends UpdatableRecordImpl<CommunityRecord> implements Record14<Integer, String, String, String, Integer, Boolean, String, Double, Integer, Timestamp, String, String, String, String> {

    private static final long serialVersionUID = 969069610;

    /**
     * Setter for <code>activityv2.community.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activityv2.community.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activityv2.community.name</code>. 社团名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>activityv2.community.name</code>. 社团名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>activityv2.community.description</code>. 社团标语
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>activityv2.community.description</code>. 社团标语
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>activityv2.community.background</code>. 社团背景图片
     */
    public void setBackground(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>activityv2.community.background</code>. 社团背景图片
     */
    public String getBackground() {
        return (String) get(3);
    }

    /**
     * Setter for <code>activityv2.community.status</code>. 社团状态 0 ： 草稿、1 待审核、2、拒绝、 9审核通过
     */
    public void setStatus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>activityv2.community.status</code>. 社团状态 0 ： 草稿、1 待审核、2、拒绝、 9审核通过
     */
    public Integer getStatus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>activityv2.community.using_score</code>. 是否开启积分功能(0:（false）关闭，&gt;0(true)开启)
     */
    public void setUsingScore(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>activityv2.community.using_score</code>. 是否开启积分功能(0:（false）关闭，&gt;0(true)开启)
     */
    public Boolean getUsingScore() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>activityv2.community.avatar</code>. 社团小图标
     */
    public void setAvatar(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>activityv2.community.avatar</code>. 社团小图标
     */
    public String getAvatar() {
        return (String) get(6);
    }

    /**
     * Setter for <code>activityv2.community.vip_amount</code>. 会费金额 0：不开启会员功能， &gt;0 :开启会员功能
     */
    public void setVipAmount(Double value) {
        set(7, value);
    }

    /**
     * Getter for <code>activityv2.community.vip_amount</code>. 会费金额 0：不开启会员功能， &gt;0 :开启会员功能
     */
    public Double getVipAmount() {
        return (Double) get(7);
    }

    /**
     * Setter for <code>activityv2.community.created_by</code>. 社团创建人
     */
    public void setCreatedBy(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>activityv2.community.created_by</code>. 社团创建人
     */
    public Integer getCreatedBy() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>activityv2.community.created</code>. 创建时间
     */
    public void setCreated(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>activityv2.community.created</code>. 创建时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>activityv2.community.address</code>. 社团地址
     */
    public void setAddress(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>activityv2.community.address</code>. 社团地址
     */
    public String getAddress() {
        return (String) get(10);
    }

    /**
     * Setter for <code>activityv2.community.contact</code>. 社团联系电话
     */
    public void setContact(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>activityv2.community.contact</code>. 社团联系电话
     */
    public String getContact() {
        return (String) get(11);
    }

    /**
     * Setter for <code>activityv2.community.company</code>. 社团公司名称
     */
    public void setCompany(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>activityv2.community.company</code>. 社团公司名称
     */
    public String getCompany() {
        return (String) get(12);
    }

    /**
     * Setter for <code>activityv2.community.about</code>. 关于社团富文本
     */
    public void setAbout(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>activityv2.community.about</code>. 关于社团富文本
     */
    public String getAbout() {
        return (String) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Integer, Boolean, String, Double, Integer, Timestamp, String, String, String, String> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Integer, Boolean, String, Double, Integer, Timestamp, String, String, String, String> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Community.COMMUNITY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Community.COMMUNITY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Community.COMMUNITY.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Community.COMMUNITY.BACKGROUND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Community.COMMUNITY.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return Community.COMMUNITY.USING_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Community.COMMUNITY.AVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field8() {
        return Community.COMMUNITY.VIP_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Community.COMMUNITY.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return Community.COMMUNITY.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return Community.COMMUNITY.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return Community.COMMUNITY.CONTACT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return Community.COMMUNITY.COMPANY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return Community.COMMUNITY.ABOUT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getBackground();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getUsingScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component8() {
        return getVipAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getContact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getCompany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getAbout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getBackground();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getUsingScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value8() {
        return getVipAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getContact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getCompany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getAbout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value3(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value4(String value) {
        setBackground(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value5(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value6(Boolean value) {
        setUsingScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value7(String value) {
        setAvatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value8(Double value) {
        setVipAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value9(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value10(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value11(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value12(String value) {
        setContact(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value13(String value) {
        setCompany(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord value14(String value) {
        setAbout(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommunityRecord values(Integer value1, String value2, String value3, String value4, Integer value5, Boolean value6, String value7, Double value8, Integer value9, Timestamp value10, String value11, String value12, String value13, String value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CommunityRecord
     */
    public CommunityRecord() {
        super(Community.COMMUNITY);
    }

    /**
     * Create a detached, initialised CommunityRecord
     */
    public CommunityRecord(Integer id, String name, String description, String background, Integer status, Boolean usingScore, String avatar, Double vipAmount, Integer createdBy, Timestamp created, String address, String contact, String company, String about) {
        super(Community.COMMUNITY);

        set(0, id);
        set(1, name);
        set(2, description);
        set(3, background);
        set(4, status);
        set(5, usingScore);
        set(6, avatar);
        set(7, vipAmount);
        set(8, createdBy);
        set(9, created);
        set(10, address);
        set(11, contact);
        set(12, company);
        set(13, about);
    }
}
