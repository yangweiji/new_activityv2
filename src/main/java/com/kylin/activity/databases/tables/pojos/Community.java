/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Community implements Serializable {

    private static final long serialVersionUID = -316372327;

    private Integer   id;
    private String    name;
    private String    description;
    private String    background;
    private Integer   status;
    private Boolean   usingScore;
    private String    avatar;
    private Boolean   isVip;
    private String    vipAgreement;
    private Double    vipAmount;
    private Integer   createdBy;
    private Timestamp created;
    private String    address;
    private String    contact;
    private String    company;
    private String    about;
    private String    controlName;
    private String    businessLicense;
    private String    managerPhoneNumber;
    private Integer   countPeople;

    public Community() {}

    public Community(Community value) {
        this.id = value.id;
        this.name = value.name;
        this.description = value.description;
        this.background = value.background;
        this.status = value.status;
        this.usingScore = value.usingScore;
        this.avatar = value.avatar;
        this.isVip = value.isVip;
        this.vipAgreement = value.vipAgreement;
        this.vipAmount = value.vipAmount;
        this.createdBy = value.createdBy;
        this.created = value.created;
        this.address = value.address;
        this.contact = value.contact;
        this.company = value.company;
        this.about = value.about;
        this.controlName = value.controlName;
        this.businessLicense = value.businessLicense;
        this.managerPhoneNumber = value.managerPhoneNumber;
        this.countPeople = value.countPeople;
    }

    public Community(
        Integer   id,
        String    name,
        String    description,
        String    background,
        Integer   status,
        Boolean   usingScore,
        String    avatar,
        Boolean   isVip,
        String    vipAgreement,
        Double    vipAmount,
        Integer   createdBy,
        Timestamp created,
        String    address,
        String    contact,
        String    company,
        String    about,
        String    controlName,
        String    businessLicense,
        String    managerPhoneNumber,
        Integer   countPeople
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.background = background;
        this.status = status;
        this.usingScore = usingScore;
        this.avatar = avatar;
        this.isVip = isVip;
        this.vipAgreement = vipAgreement;
        this.vipAmount = vipAmount;
        this.createdBy = createdBy;
        this.created = created;
        this.address = address;
        this.contact = contact;
        this.company = company;
        this.about = about;
        this.controlName = controlName;
        this.businessLicense = businessLicense;
        this.managerPhoneNumber = managerPhoneNumber;
        this.countPeople = countPeople;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getUsingScore() {
        return this.usingScore;
    }

    public void setUsingScore(Boolean usingScore) {
        this.usingScore = usingScore;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsVip() {
        return this.isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public String getVipAgreement() {
        return this.vipAgreement;
    }

    public void setVipAgreement(String vipAgreement) {
        this.vipAgreement = vipAgreement;
    }

    public Double getVipAmount() {
        return this.vipAmount;
    }

    public void setVipAmount(Double vipAmount) {
        this.vipAmount = vipAmount;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getControlName() {
        return this.controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getBusinessLicense() {
        return this.businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getManagerPhoneNumber() {
        return this.managerPhoneNumber;
    }

    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public Integer getCountPeople() {
        return this.countPeople;
    }

    public void setCountPeople(Integer countPeople) {
        this.countPeople = countPeople;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Community (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(background);
        sb.append(", ").append(status);
        sb.append(", ").append(usingScore);
        sb.append(", ").append(avatar);
        sb.append(", ").append(isVip);
        sb.append(", ").append(vipAgreement);
        sb.append(", ").append(vipAmount);
        sb.append(", ").append(createdBy);
        sb.append(", ").append(created);
        sb.append(", ").append(address);
        sb.append(", ").append(contact);
        sb.append(", ").append(company);
        sb.append(", ").append(about);
        sb.append(", ").append(controlName);
        sb.append(", ").append(businessLicense);
        sb.append(", ").append(managerPhoneNumber);
        sb.append(", ").append(countPeople);

        sb.append(")");
        return sb.toString();
    }
}
