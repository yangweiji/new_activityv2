package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.TrainingCampDao
import com.kylin.activity.databases.tables.pojos.TrainingCamp
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: Administrator
 * @Date: 2018/10/8 13:16
 * @Description:
 */
@Service
class ThirdTrainingCampService {
/**
     * 数据访问上下文
     */
    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val trainingCampDao:TrainingCampDao?=null
    /**
     * 异步查询训练营信息
     */
    fun getTrainingCamps(communityId:Int,campName:String?):Result<Record>{
        var sql="select t1.*,t3.real_name as realName from training_camp t1 inner join community t2 on t1.community_id=t2.id inner join `user` t3 on " +
                "t1.modified_by=t3.id where t1.community_id=? "
        var params = mutableListOf<Any?>()
        if (!campName.isNullOrBlank()) {
            sql += "and t1.camp_name like ? "
            params.add("%$campName%")
        }
        return create!!.resultQuery(sql,communityId, *params.toTypedArray()).fetch()

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
     *
     */
    fun deleteTrainingCamp(id:Int) {
        trainingCampDao!!.deleteById(id)
    }

    /**
     * 获取单个内容信息
     * @param title 内容标题
     */
    fun getTrainingCampName(campName: String): TrainingCamp? {
        return trainingCampDao!!.fetchOne(Tables.TRAINING_CAMP.CAMP_NAME, campName)
    }
}