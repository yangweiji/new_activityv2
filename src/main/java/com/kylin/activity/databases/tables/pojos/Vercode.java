/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 验证码
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Vercode implements Serializable {

    private static final long serialVersionUID = -908313729;

    private Integer   id;
    private String    mobile;
    private String    code;
    private Timestamp created;

    public Vercode() {}

    public Vercode(Vercode value) {
        this.id = value.id;
        this.mobile = value.mobile;
        this.code = value.code;
        this.created = value.created;
    }

    public Vercode(
        Integer   id,
        String    mobile,
        String    code,
        Timestamp created
    ) {
        this.id = id;
        this.mobile = mobile;
        this.code = code;
        this.created = created;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vercode (");

        sb.append(id);
        sb.append(", ").append(mobile);
        sb.append(", ").append(code);
        sb.append(", ").append(created);

        sb.append(")");
        return sb.toString();
    }
}
