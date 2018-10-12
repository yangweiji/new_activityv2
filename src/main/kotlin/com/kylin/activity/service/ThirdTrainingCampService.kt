package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.TrainingCampDao
import com.kylin.activity.databases.tables.pojos.TrainingCamp
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ThirdTrainingCampService {
    /**
     * 数据访问上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 训练营DAO
     */
    @Autowired
    private val trainingCampDao: TrainingCampDao? = null

    /**
     * 异步查询训练营信息
     * @param communityId 团体编号
     * @param campName 训练营名称
     * @return 某个团体下对应的训练营信息
     */
    fun getTrainingCamps(communityId: Int, campName: String?): Result<Record> {
        var sql = "select t1.*,t3.real_name as realName from training_camp t1 inner join community t2 on t1.community_id=t2.id inner join `user` t3 on " +
                "t1.modified_by=t3.id where t1.community_id=? "
        var params = mutableListOf<Any?>()
        if (!campName.isNullOrBlank()) {
            sql += "and t1.camp_name like ? "
            params.add("%$campName%")
        }
        return create!!.resultQuery(sql, communityId, *params.toTypedArray()).fetch()

    }

    /**
     * 查询团体组织下的训练营信息
     * @param communityId: 团体编号
     * @return 团体组织下的所有训练营
     */
    fun getCommunityTrainingCampList(communityId: Int): List<TrainingCamp> {
        return trainingCampDao!!.fetchByCommunityId(communityId)
    }

    /**
     * 获取训练营信息
     * @param id: 训练营id
     * @return 单个训练营
     */
    fun getTrainingCamp(id: Int): TrainingCamp {
        return trainingCampDao!!.findById(id)
    }


    /**
     * 更新训练营
     * @param trainingCamp 训练营
     */

    fun update(trainingCamp: TrainingCamp) {
        trainingCampDao!!.update(trainingCamp)
    }

    /**
     * 添加训练营
     * @param trainingCamp 训练营
     */

    fun insert(trainingCamp: TrainingCamp) {
        trainingCampDao!!.insert(trainingCamp)
    }

    /**
     * 删除训练营
     * @param id 训练营编号
     */
    fun deleteTrainingCamp(id: Int) {
        trainingCampDao!!.deleteById(id)
    }

    /**
     * 获取单个训练营信息
     * @param campName 训练营名称
     * @return 单个训练营信息
     */
    fun getTrainingCampName(campName: String): TrainingCamp? {
        return trainingCampDao!!.fetchOne(Tables.TRAINING_CAMP.CAMP_NAME, campName)
    }
}