/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 社团成员
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommunityUser implements Serializable {

    private static final long serialVersionUID = 350704484;

    private Integer   id;
    private Integer   communityId;
    private Integer   userId;
    private String    role;
    private Timestamp created;
    private Integer   level;

    public CommunityUser() {}

    public CommunityUser(CommunityUser value) {
        this.id = value.id;
        this.communityId = value.communityId;
        this.userId = value.userId;
        this.role = value.role;
        this.created = value.created;
        this.level = value.level;
    }

    public CommunityUser(
        Integer   id,
        Integer   communityId,
        Integer   userId,
        String    role,
        Timestamp created,
        Integer   level
    ) {
        this.id = id;
        this.communityId = communityId;
        this.userId = userId;
        this.role = role;
        this.created = created;
        this.level = level;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CommunityUser (");

        sb.append(id);
        sb.append(", ").append(communityId);
        sb.append(", ").append(userId);
        sb.append(", ").append(role);
        sb.append(", ").append(created);
        sb.append(", ").append(level);

        sb.append(")");
        return sb.toString();
    }
}
