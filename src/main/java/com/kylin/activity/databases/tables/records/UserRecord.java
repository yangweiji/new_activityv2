/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.User;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


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
public class UserRecord extends UpdatableRecordImpl<UserRecord> {

    private static final long serialVersionUID = -194963553;

    /**
     * Setter for <code>user.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>user.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>user.username</code>. 登录名
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>user.username</code>. 登录名
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>user.displayname</code>. 显示名
     */
    public void setDisplayname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>user.displayname</code>. 显示名
     */
    public String getDisplayname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>user.password</code>. 密码
     */
    public void setPassword(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>user.password</code>. 密码
     */
    public String getPassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>user.role</code>. 角色
     */
    public void setRole(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>user.role</code>. 角色
     */
    public String getRole() {
        return (String) get(4);
    }

    /**
     * Setter for <code>user.enabled</code>. 是否启用
     */
    public void setEnabled(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>user.enabled</code>. 是否启用
     */
    public Boolean getEnabled() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>user.gender</code>. 性别 1 男 2 女
     */
    public void setGender(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>user.gender</code>. 性别 1 男 2 女
     */
    public Integer getGender() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>user.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>user.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>user.level</code>. 会员级别 0 普通用户 年份： 值所确定的年份的会员
     */
    public void setLevel(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>user.level</code>. 会员级别 0 普通用户 年份： 值所确定的年份的会员
     */
    public Integer getLevel() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>user.id_card</code>. 身份证号
     */
    public void setIdCard(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>user.id_card</code>. 身份证号
     */
    public String getIdCard() {
        return (String) get(9);
    }

    /**
     * Setter for <code>user.real_name</code>. 真实姓名
     */
    public void setRealName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>user.real_name</code>. 真实姓名
     */
    public String getRealName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>user.real_time</code>.
     */
    public void setRealTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>user.real_time</code>.
     */
    public Timestamp getRealTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>user.is_real</code>. 是否通过认证
     */
    public void setIsReal(Boolean value) {
        set(12, value);
    }

    /**
     * Getter for <code>user.is_real</code>. 是否通过认证
     */
    public Boolean getIsReal() {
        return (Boolean) get(12);
    }

    /**
     * Setter for <code>user.open_id</code>. 微信openId
     */
    public void setOpenId(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>user.open_id</code>. 微信openId
     */
    public String getOpenId() {
        return (String) get(13);
    }

    /**
     * Setter for <code>user.avatar</code>. 头像
     */
    public void setAvatar(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>user.avatar</code>. 头像
     */
    public String getAvatar() {
        return (String) get(14);
    }

    /**
     * Setter for <code>user.email</code>.
     */
    public void setEmail(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>user.email</code>.
     */
    public String getEmail() {
        return (String) get(15);
    }

    /**
     * Setter for <code>user.work_company</code>. 工作单位
     */
    public void setWorkCompany(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>user.work_company</code>. 工作单位
     */
    public String getWorkCompany() {
        return (String) get(16);
    }

    /**
     * Setter for <code>user.is_party</code>. 是否党员
     */
    public void setIsParty(Boolean value) {
        set(17, value);
    }

    /**
     * Getter for <code>user.is_party</code>. 是否党员
     */
    public Boolean getIsParty() {
        return (Boolean) get(17);
    }

    /**
     * Setter for <code>user.address</code>. 家庭地址
     */
    public void setAddress(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>user.address</code>. 家庭地址
     */
    public String getAddress() {
        return (String) get(18);
    }

    /**
     * Setter for <code>user.blood_type</code>. 血型
     */
    public void setBloodType(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>user.blood_type</code>. 血型
     */
    public String getBloodType() {
        return (String) get(19);
    }

    /**
     * Setter for <code>user.clothing_size</code>. T恤尺寸
     */
    public void setClothingSize(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>user.clothing_size</code>. T恤尺寸
     */
    public String getClothingSize() {
        return (String) get(20);
    }

    /**
     * Setter for <code>user.occupation</code>. 职业
     */
    public void setOccupation(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>user.occupation</code>. 职业
     */
    public String getOccupation() {
        return (String) get(21);
    }

    /**
     * Setter for <code>user.emergency_contact_name</code>. 紧急联系人姓名
     */
    public void setEmergencyContactName(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>user.emergency_contact_name</code>. 紧急联系人姓名
     */
    public String getEmergencyContactName() {
        return (String) get(22);
    }

    /**
     * Setter for <code>user.emergency_contact_mobile</code>. 紧急联系人电话
     */
    public void setEmergencyContactMobile(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>user.emergency_contact_mobile</code>. 紧急联系人电话
     */
    public String getEmergencyContactMobile() {
        return (String) get(23);
    }

    /**
     * Setter for <code>user.wechat_id</code>. 微信号
     */
    public void setWechatId(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>user.wechat_id</code>. 微信号
     */
    public String getWechatId() {
        return (String) get(24);
    }

    /**
     * Setter for <code>user.union_id</code>. 微信开放平台，联合身份ID
     */
    public void setUnionId(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>user.union_id</code>. 微信开放平台，联合身份ID
     */
    public String getUnionId() {
        return (String) get(25);
    }

    /**
     * Setter for <code>user.nick_name</code>. 微信昵称
     */
    public void setNickName(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>user.nick_name</code>. 微信昵称
     */
    public String getNickName() {
        return (String) get(26);
    }

    /**
     * Setter for <code>user.mobile</code>. 双向绑定时，使用的手机号码
     */
    public void setMobile(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>user.mobile</code>. 双向绑定时，使用的手机号码
     */
    public String getMobile() {
        return (String) get(27);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer id, String username, String displayname, String password, String role, Boolean enabled, Integer gender, Timestamp created, Integer level, String idCard, String realName, Timestamp realTime, Boolean isReal, String openId, String avatar, String email, String workCompany, Boolean isParty, String address, String bloodType, String clothingSize, String occupation, String emergencyContactName, String emergencyContactMobile, String wechatId, String unionId, String nickName, String mobile) {
        super(User.USER);

        set(0, id);
        set(1, username);
        set(2, displayname);
        set(3, password);
        set(4, role);
        set(5, enabled);
        set(6, gender);
        set(7, created);
        set(8, level);
        set(9, idCard);
        set(10, realName);
        set(11, realTime);
        set(12, isReal);
        set(13, openId);
        set(14, avatar);
        set(15, email);
        set(16, workCompany);
        set(17, isParty);
        set(18, address);
        set(19, bloodType);
        set(20, clothingSize);
        set(21, occupation);
        set(22, emergencyContactName);
        set(23, emergencyContactMobile);
        set(24, wechatId);
        set(25, unionId);
        set(26, nickName);
        set(27, mobile);
    }
}
