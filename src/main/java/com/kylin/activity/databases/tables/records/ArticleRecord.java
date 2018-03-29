/*
 * This file is generated by jOOQ.
*/
package com.kylin.activity.databases.tables.records;


import com.kylin.activity.databases.tables.Article;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 文章 内容发布
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleRecord extends UpdatableRecordImpl<ArticleRecord> implements Record14<Integer, Integer, String, String, Integer, String, Integer, Timestamp, String, String, Timestamp, Integer, Timestamp, Integer> {

    private static final long serialVersionUID = -1914908236;

    /**
     * Setter for <code>activity.article.id</code>. 编号
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>activity.article.id</code>. 编号
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>activity.article.community_id</code>. 0：表示平台发布， &gt;0 : 团体发布
     */
    public void setCommunityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>activity.article.community_id</code>. 0：表示平台发布， &gt;0 : 团体发布
     */
    public Integer getCommunityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>activity.article.title</code>. 标题
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>activity.article.title</code>. 标题
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>activity.article.summary</code>. 摘要
     */
    public void setSummary(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>activity.article.summary</code>. 摘要
     */
    public String getSummary() {
        return (String) get(3);
    }

    /**
     * Setter for <code>activity.article.category</code>. 分类 1：公告通知、2： 赛事新闻、3 运动指南
     */
    public void setCategory(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>activity.article.category</code>. 分类 1：公告通知、2： 赛事新闻、3 运动指南
     */
    public Integer getCategory() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>activity.article.body</code>. 正文
     */
    public void setBody(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>activity.article.body</code>. 正文
     */
    public String getBody() {
        return (String) get(5);
    }

    /**
     * Setter for <code>activity.article.status</code>. 状态 0 草稿 1 发布 -1 禁用
     */
    public void setStatus(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>activity.article.status</code>. 状态 0 草稿 1 发布 -1 禁用
     */
    public Integer getStatus() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>activity.article.publish_time</code>. 发布时间 显示时按时间倒序排
     */
    public void setPublishTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>activity.article.publish_time</code>. 发布时间 显示时按时间倒序排
     */
    public Timestamp getPublishTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>activity.article.avatar</code>. 图片
     */
    public void setAvatar(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>activity.article.avatar</code>. 图片
     */
    public String getAvatar() {
        return (String) get(8);
    }

    /**
     * Setter for <code>activity.article.unit</code>. 发布单位
     */
    public void setUnit(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>activity.article.unit</code>. 发布单位
     */
    public String getUnit() {
        return (String) get(9);
    }

    /**
     * Setter for <code>activity.article.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>activity.article.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>activity.article.created_by</code>. 创建人
     */
    public void setCreatedBy(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>activity.article.created_by</code>. 创建人
     */
    public Integer getCreatedBy() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>activity.article.modified</code>.
     */
    public void setModified(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>activity.article.modified</code>.
     */
    public Timestamp getModified() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>activity.article.modified_by</code>. 修改人
     */
    public void setModifiedBy(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>activity.article.modified_by</code>. 修改人
     */
    public Integer getModifiedBy() {
        return (Integer) get(13);
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
    public Row14<Integer, Integer, String, String, Integer, String, Integer, Timestamp, String, String, Timestamp, Integer, Timestamp, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, Integer, String, String, Integer, String, Integer, Timestamp, String, String, Timestamp, Integer, Timestamp, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Article.ARTICLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Article.ARTICLE.COMMUNITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Article.ARTICLE.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Article.ARTICLE.SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Article.ARTICLE.CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Article.ARTICLE.BODY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Article.ARTICLE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return Article.ARTICLE.PUBLISH_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return Article.ARTICLE.AVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return Article.ARTICLE.UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return Article.ARTICLE.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Article.ARTICLE.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return Article.ARTICLE.MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return Article.ARTICLE.MODIFIED_BY;
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
    public Integer component2() {
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCategory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getPublishTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getModifiedBy();
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
    public Integer value2() {
        return getCommunityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCategory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getPublishTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getAvatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value2(Integer value) {
        setCommunityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value3(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value4(String value) {
        setSummary(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value5(Integer value) {
        setCategory(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value6(String value) {
        setBody(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value7(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value8(Timestamp value) {
        setPublishTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value9(String value) {
        setAvatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value10(String value) {
        setUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value11(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value12(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value13(Timestamp value) {
        setModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value14(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, String value6, Integer value7, Timestamp value8, String value9, String value10, Timestamp value11, Integer value12, Timestamp value13, Integer value14) {
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
     * Create a detached ArticleRecord
     */
    public ArticleRecord() {
        super(Article.ARTICLE);
    }

    /**
     * Create a detached, initialised ArticleRecord
     */
    public ArticleRecord(Integer id, Integer communityId, String title, String summary, Integer category, String body, Integer status, Timestamp publishTime, String avatar, String unit, Timestamp created, Integer createdBy, Timestamp modified, Integer modifiedBy) {
        super(Article.ARTICLE);

        set(0, id);
        set(1, communityId);
        set(2, title);
        set(3, summary);
        set(4, category);
        set(5, body);
        set(6, status);
        set(7, publishTime);
        set(8, avatar);
        set(9, unit);
        set(10, created);
        set(11, createdBy);
        set(12, modified);
        set(13, modifiedBy);
    }
}
