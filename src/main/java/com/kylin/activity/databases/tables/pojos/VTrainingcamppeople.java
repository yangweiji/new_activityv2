/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VTrainingcamppeople implements Serializable {

    private static final long serialVersionUID = -177035908;

    private String communityname;
    private String campname;
    private Long   attendpeoplecount;
    private Long   checkpeoplecount;

    public VTrainingcamppeople() {}

    public VTrainingcamppeople(VTrainingcamppeople value) {
        this.communityname = value.communityname;
        this.campname = value.campname;
        this.attendpeoplecount = value.attendpeoplecount;
        this.checkpeoplecount = value.checkpeoplecount;
    }

    public VTrainingcamppeople(
        String communityname,
        String campname,
        Long   attendpeoplecount,
        Long   checkpeoplecount
    ) {
        this.communityname = communityname;
        this.campname = campname;
        this.attendpeoplecount = attendpeoplecount;
        this.checkpeoplecount = checkpeoplecount;
    }

    public String getCommunityname() {
        return this.communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public String getCampname() {
        return this.campname;
    }

    public void setCampname(String campname) {
        this.campname = campname;
    }

    public Long getAttendpeoplecount() {
        return this.attendpeoplecount;
    }

    public void setAttendpeoplecount(Long attendpeoplecount) {
        this.attendpeoplecount = attendpeoplecount;
    }

    public Long getCheckpeoplecount() {
        return this.checkpeoplecount;
    }

    public void setCheckpeoplecount(Long checkpeoplecount) {
        this.checkpeoplecount = checkpeoplecount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VTrainingcamppeople (");

        sb.append(communityname);
        sb.append(", ").append(campname);
        sb.append(", ").append(attendpeoplecount);
        sb.append(", ").append(checkpeoplecount);

        sb.append(")");
        return sb.toString();
    }
}