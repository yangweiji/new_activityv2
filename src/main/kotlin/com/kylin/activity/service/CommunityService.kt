package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Community
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.jooq.*

@Service
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
    fun deleteCommunity(id: Int) {
        communityDao!!.deleteById(id)
    }

    /**
     * 添加团体信息
     */
    fun createCommunity(community: Community): Community {
        communityDao!!.insert(community)
        return community
    }

    /**
     * 根据id,编辑团体信息
     */
    fun editCommunity(id: Int): Community {
        var community = communityDao!!.findById(id)
        return community
    }

    /**
     * 切换团体信息
     */
    fun getCommunities(name: String?): Result<Record> {
        var sql = "select * from community c where 1=1 "
        var params = mutableListOf<Any?>()
        if (!name.isNullOrBlank()) {
            sql += "and c.name = ? "
            params.add(name)
        }
        sql += "order by c.created desc limit 10"
        return create!!.resultQuery(sql, params.toTypedArray()).fetch()
    }

    /**
     * 获取社团的id
     */
    fun getCommunityId(id: Int?): Community {
        var items = communityDao!!.findById(id)
        return items
    }

}