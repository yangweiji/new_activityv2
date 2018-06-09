package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.jooq.*
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable

@Service
@CacheConfig(cacheNames = ["community"])
class CommunityService {
    @Autowired
    private val communityDao: CommunityDao? = null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 获取团体信息
     */
    fun queryCommunity(name: String?): Result<Record> {
        var sql = "select c.* from community c where 1=1 "
        var params = mutableListOf<Any?>()
        if (!name.isNullOrBlank()) {
            sql += "and c.name like '%?%' ".replace("?", name!!)
            params.add(name)
        }
        return create!!.resultQuery(sql, params.toTypedArray()).fetch()
    }

    /**
     * 根据id,删除团体信息
     */
    @CacheEvict(allEntries=true)
    fun deleteCommunity(id: Int) {
        communityDao!!.deleteById(id)
    }

    /**
     * 切换团体信息
     */
    @Cacheable()
    fun getCommunities(): List<Community> {
        return communityDao!!.findAll()
    }

    /**
     * 获取社团
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

}