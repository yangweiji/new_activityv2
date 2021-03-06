package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.daos.CommunityUserDao
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.util.LogUtil
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["community"])
class CommunityService {
    @Autowired
    private val communityDao: CommunityDao? = null


    @Autowired
    private val communityUserDao: CommunityUserDao? = null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 获取团体信息
     * @param name: 团体标题名称
     * @return 团体列表信息
     */
    fun queryCommunity(name: String?): Result<Record> {
        var sql = "select c.* from community c where 1=1 "
        var params = mutableListOf<Any?>()
        if (!name.isNullOrBlank()) {
            sql += "and c.name like ? "
            params.add("%$name%")
        }
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()
    }

    /**
     * 根据id,删除团体信息
     * @param id: 组织ID
     */
    @CacheEvict(allEntries=true)
    fun deleteCommunity(id: Int) {
        communityDao!!.deleteById(id)
    }

    /**
     * 取得所有的团体组织列表信息
     * @return 团体组织列表信息
     */
    @Cacheable()
    fun getCommunities(): List<Community> {
        return communityDao!!.findAll()
    }

    /**
     * 获取社团
     * @param id: 团体组织ID
     * @return 单个团体组织信息
     */
    fun getCommunity(id: Int): Community {
        return communityDao!!.findById(id)
    }

    /**
     * 更新社团
     * @param community 社团
     */
    @CacheEvict(allEntries=true)
    fun update(community: Community) {
        communityDao!!.update(community)
        LogUtil.printLog("更新缓存, 团体组织ID: ${community.id}")
    }

    /**
     * 添加团体
     * @param community 团体信息
     */
    @CacheEvict(allEntries=true)
    fun insert(community: Community) {
        communityDao!!.insert(community)
        LogUtil.printLog("添加缓存, 团体组织ID: ${community.id}")
    }

    /**
     * 保存团体组织信息
     * @param community: 团体组织信息
     * @return 团体组织信息
     */
    @CacheEvict(allEntries = true)
    fun save(community: Community): Community {
        if (community.id != null && community.id > 0) {
            communityDao!!.update(community)
        }
        else {
            communityDao!!.insert(community)
        }

        return community
    }

    /**
     * 取得用户所在的团体列表信息
     * @param userId: 用户ID
     * @return 团体列表信息
     */
    fun getUserCommunities(userId: Int): List<CommunityUser> {
        return communityUserDao!!.fetchByUserId(userId)
    }

    /**
     * 取得用户团体关联信息
     * @param userId: 用户ID
     * @param communityId: 组织ID
     * @return 用户团体关联信息
     */
    fun getCommunityUser(userId: Int?, communityId: Int?): CommunityUser? {

        return create!!.selectFrom(Tables.COMMUNITY_USER)
                .where(Tables.COMMUNITY_USER.USER_ID.eq(userId))
                .and(Tables.COMMUNITY_USER.COMMUNITY_ID.eq(communityId))
                .fetchOneInto(CommunityUser::class.java)
    }

    /**
     * 检查团体组织下是否有关联的活动或者相册
     * @param communityId: 团体组织ID
     * @return 是否存在
     */
    fun checkCommunity(communityId: Int): Boolean {

        return (activityService!!.getCommunityActivityCount(communityId) > 0)
    }

    /**
     * 依据当前用户获取其管理的团体组织，如有多个，只获取其中一个
     * @param userId: 用户ID
     * @return 团体组织用户关联信息
     */
    fun getCommunityAdminUser(userId: Int): CommunityUser? {
        var item = create!!.selectFrom(Tables.COMMUNITY_USER)
                .where(Tables.COMMUNITY_USER.ROLE.eq("管理员").or(Tables.COMMUNITY_USER.ROLE.eq("发布者")))
                .and(Tables.COMMUNITY_USER.USER_ID.eq(userId))
                .firstOrNull()
        return if (item != null) item.into(CommunityUser::class.java) else null
    }


    /**
     * 获取默认加入的团体
     */
    fun getDefaultCommunity(userId:Int): Community{
        var sql = "select t1.* from community t1 " +
                "inner join community_user t2 " +
                "on t1.id = t2.community_id and t2.user_id = ? and t2.is_default = 1"

        var item = create!!.resultQuery(sql, userId).fetchOne()
        return if ( item == null) {
             communityDao!!.fetchOneById(1)
        } else item.into(Community::class.java)
    }

    /**
     * 取得用户所在的团体列表信息
     * @param userId: 用户ID
     * @return 团体列表信息
     */
    fun getCommunitiesByUser(userId: Int): Any {
        var sql = "select t1.*" +
                ", date_format(t2.member_time, '%Y年%m月%d日') as member_time " +
                ", date_format(t2.created, '%Y年%m月%d日') as created_time " +
                ", t2.is_default " +
                "from community t1 " +
                "inner join community_user t2 on t1.id = t2.community_id " +
                "where t2.user_id = ? " +
                "order by t2.is_default desc, t2.created"

        var items = create!!.fetch(sql, userId).intoMaps()
        return items
    }


    /**
     * 设置某团体组织为用户的默认团体组织,默认团体组织有且只有一个
     */
    fun setDefault(userId: Int, communityId: Int): Boolean {
        var sql = "update community_user set is_default = 0 where user_id = ?"
        create!!.execute(sql, userId)
        sql = "update community_user set is_default = 1 where user_id = ? and community_id = ?"
        return create!!.execute(sql, userId, communityId) > 0
    }


}