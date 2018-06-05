package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.ActivityPhotoPicture
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.jooq.*

@Service
class CommunityService {
    @Autowired
    private val communityDao: CommunityDao? = null

    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val userDao: UserDao? = null

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
    fun getCommunities(): List<Community> {
        return communityDao!!.findAll()
    }

    /**
     * 获取社团的id
     */
    fun getCommunityId(id: Int): Community {
        var items = communityDao!!.findById(id)
        return items
    }

    /**
     * 更新社团
     * @param community 社团
     */
    fun updateCommunity(community: Community) {
        communityDao!!.update(community)
    }


    /**
     * 添加社团
     * @param community 社团
     */
    fun addCommunity(community: Community) {
        communityDao!!.insert(community)
    }


    /**
     * 添加负责人联系方式
     * @param community 团体信息
     * @return 团体负责人联系方式
     */
    fun insertCommunity(community: Community): String {
        communityDao!!.insert(community)
        return community.managerPhoneNumber
    }


    /**
     *
     */
    fun updateUser(user: User): String {
        userDao!!.update(user)
        return user.username
    }

    /**
     * 取得团体组织关联用户
     * @param communityId: 团体组织ID
     * @param userId: 用户ID
     * @param
     */
    fun getCommunityUser(communityId: Int?, userId: Int?): CommunityUser? {
        return create!!.selectFrom(Tables.COMMUNITY_USER)
                .where(Tables.COMMUNITY_USER.COMMUNITY_ID.eq(communityId))
                .and(Tables.COMMUNITY_USER.USER_ID.eq(userId))
                .fetchOneInto(CommunityUser::class.java)
    }

}