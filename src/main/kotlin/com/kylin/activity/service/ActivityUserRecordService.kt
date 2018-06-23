package com.kylin.activity.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.*
import com.kylin.activity.databases.tables.pojos.*
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 活动服务
 * @author Richard C. Hu
 */
@Service
class ActivityUserRecordService {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 活动DAO
     */
    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 活动收藏DAO
     */
    @Autowired
    private val activityFavoriteDao: ActivityFavoriteDao? = null

    /**
     * 活动票种DAO
     */
    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    /**
     * 活动积分历史Dao
     */
    @Autowired
    private val scoreHistoryDao: ScoreHistoryDao? = null

    /**
     * 报名活动用户DAO
     */
    @Autowired
    private val activityUserDao: ActivityUserDao? = null



    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 操作历史记录DAO
     */
    @Autowired
    private val actionHistoryDao: ActionHistoryDao? = null




}