package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityFavoriteDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityFavorite
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityService {

    @Autowired
    private var commonService: CommonService? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    @Autowired
    private val activityFavoriteDao: ActivityFavoriteDao? = null

    @Autowired
    private val create: DSLContext? = null

//    fun getPublicActivities(): Result<out Record7<Int, String, Timestamp, Timestamp, String, out Int?, out Int?>>  {
//        var t1 = Tables.ACTIVITY.`as`("t1")
//        var t2 = create!!.select(Tables.ACTIVITY_USER.ACTIVITY_ID, DSL.ifnull(DSL.count(Tables.ACTIVITY_USER.ID), 0).`as`("attend_count"))
//                .from(Tables.ACTIVITY_USER)
//                .groupBy(Tables.ACTIVITY_USER.ACTIVITY_ID).asTable("t2")
//
//        var t3 = create!!.select(Tables.ACTIVITY_FAVORITE.ACTIVITY_ID, DSL.ifnull(DSL.count(Tables.ACTIVITY_FAVORITE.ID),0).`as`("favorite_count"))
//                .from(Tables.ACTIVITY_FAVORITE)
//                .groupBy(Tables.ACTIVITY_FAVORITE.ACTIVITY_ID).asTable("t3")
//
//        var activities = create!!.select(t1.ID, t1.TITLE, t1.START_TIME, t1.END_TIME, t1.AVATAR
//                , t2.field("attend_count", Int::class.java), t3.field("favorite_count", Int::class.java))
//                        .from(t1)
//                        .leftJoin(t2)
//                        .on(t1.ID.eq(t2.field(Tables.ACTIVITY_USER.ACTIVITY_ID)))
//                        .leftJoin(t3)
//                        .on(t1.ID.eq(t3.field(Tables.ACTIVITY_FAVORITE.ACTIVITY_ID)))
////                        .where(t1.PUBLIC.eq(true).and(t1.END_TIME.lessOrEqual(DateUtil.date().toTimestamp())))
//                        .where(t1.PUBLIC.eq(true))
//                        .orderBy(t1.START_TIME.desc())
//
//        return activities.fetch()
//    }

    /**
     * 定义分页索引起始位置
     */
    public var page: Int = 0

    /**
     * 定义每页记录数
     */
    public var size: Int = 12

    /**
     * 取得活动
     */
    fun getActivity(id: Int): Activity? {
        return activityDao!!.fetchOne(Tables.ACTIVITY.ID, id)
    }

    /**
     * 取得活动
     */
    fun getAtivity(title: String): Activity? {
        var activity = activityDao!!.fetchOne(Tables.ACTIVITY.TITLE, title)
        return activity
    }

    /**
     * 取得活动、其他信息
     */
    fun getActivityAndOthers(id: Int): Record? {
        var sql = "select t1.* from activity t1 where t1.id = ?"
        var item = create!!.fetchOne(sql, id)
        return item
    }

    /**
     * 取得活动信息、活动参与人数、活动收藏人数
     * 最新的前1000条记录
     */
    fun getPublicActivities(): Result<Record> {

        //构建活动数据源
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_favorite where activity_id = t1.id) favorite_count " +
                "from activity t1 " +
                "left join user t2 on t1.created_by = t2.id " +
                "order by t1.start_time desc " +
                "limit 1000"

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

   /* fun useGetPublicActivities(id: Int): Result<Record> {
        //构建活动数据源
        var sql = "select a.id,a.avatar,a.start_time,a.title from activity a left join community c on a.community_id=c.id"
        return create!!.resultQuery(sql).fetch()
    }*/

    /**
     * 取得活动信息、活动参与人数、活动收藏人数
     * 最新的前1000条记录
     */
    fun getPublicActivities(tags: String): Result<Record> {

        //构建活动数据源
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_favorite where activity_id = t1.id) favorite_count " +
                "from activity t1 " +
                "left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} " +
                "order by t1.start_time desc " +
                "limit 1000"
        var strCondition = ""
        if (!tags.isNullOrBlank()) {
            if (tags.contains('-')) {
                var ss = ""
                for (s in tags.split("-")) {
                    ss = "$ss,'$s'"
                }
                strCondition = "and t1.tags in ({0})".replace("{0}", ss.substring(1))
            } else {
                strCondition = "and t1.tags = '{0}'".replace("{0}", tags)
            }
        }
        sql = sql.replace("{0}", strCondition)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     *  获取活动信息、活动参与人数、活动收藏人数及用户选择的团队活动信息
     */
    fun getTeamActivities(sid: Int, tag: String): Result<Record> {
        //获取团队活动信息
        var sql = "select t1.*, " +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count," +
                "(select count(*) from activity_favorite where activity_id = t1.id) favorite_count " +
                "from activity t1" +
                " where 1=1 {0}{1}"
        var sqlsid = ""
        if (sid != 0) {
            sqlsid = "and community_id = sid"
        }
        sql = sql.replace("{0}", sqlsid)
        var sqltag = ""
        if (!tag.isNullOrBlank() && !tag.equals("0")) {
            sqltag = "and tags ='{0}'".replace("{0}", tag)
        }
        sql = sql.replace("{1}", sqltag)
        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 取得活动总数
     */
    public var activityCount: Long = 0

    /**
     * 取得活动信息、活动参与人数、活动收藏人数
     */
    fun getPublicActivities(tag: String, time: String, pay: String): Result<Record> {

        var sql_count = "select count(*) count from activity t1 where 1=1 {0} {1}"

        //构建活动数据源
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_favorite where activity_id = t1.id) favorite_count " +
                "from activity t1 " +
                "left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} {1} " +
                "order by t1.start_time desc " +
                "limit {99}, {100}"
        var strTag = ""
        if (!tag.isNullOrBlank() && !tag.equals("0")) {
            strTag = "and t1.tags = '{0}'".replace("{0}", tag)
        }
        sql_count = sql_count.replace("{0}", strTag)
        sql = sql.replace("{0}", strTag)

        var strTime = ""
        if (!time.isNullOrBlank() && !time.equals("0")) {

            if (time == "d") {
                //今天
                strTime = "and to_days(t1.start_time) = to_days(now())"
            } else if (time == "w") {
                //近一周
                strTime = "and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(t1.start_time)"
            } else if (time == "m") {
                //近一月
                strTime = "and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(t1.start_time)"
            } else if (time == "z") {
                //周末
                strTime = "and (DATE_FORMAT(t1.start_time,'%w') = 6 or DATE_FORMAT(t1.start_time,'%w') = 7)"
            } else {
                //指定日期
                strTime = "and DATE_FORMAT(t1.start_time,'%Y%m%d') = " + time
            }
        }

        sql_count = sql_count.replace("{1}", strTime)
        activityCount = create!!.resultQuery(sql_count).fetch("count")[0] as Long

        sql = sql.replace("{1}", strTime)
        //分页条件
        if (page*size < 0) {
            sql = sql.replace("{99}", "0")
        }
        else {
            sql = sql.replace("{99}", (page * size).toString())
        }
        sql = sql.replace("{100}", size.toString())
        var items = create!!.resultQuery(sql).fetch()

        return items
    }

    /**
     * 取得活动详情信息
     */
    fun getActivityDetail(id: Int): Record {
        var sql = "select t1.*, t2.avatar user_avatar, t2.displayname, " +
                "ifnull(tu.attend_count, 0) attend_count, " +
                "ifnull(tf.favorite_count, 0) favorite_count " +
                "from activity t1 " +
                "left join user t2 on t1.created_by  = t2.id " +
                "left join ( " +
                " select activity_id, count(user_id) attend_count from activity_user where activity_id=? " +
                ") as tu on t1.id = tu.activity_id " +
                "left join ( " +
                " select activity_id, count(user_id) favorite_count from activity_favorite where activity_id=? " +
                ") as tf on t1.id = tf.activity_id " +
                "where t1.id = ? "
        var activity = create!!.resultQuery(sql, id, id, id).fetchOne()
        return activity
    }


    /**
     * 取得所有活动集合，返回记录集合Result
     */
    fun getActivities(): Result<Record> {
        var sql = "select * from activity"
        var items = create!!.resultQuery(sql).fetch()
        return items
    }


    /**
     * 取得所有活动信息，返回List
     */
    fun getAllActivities(): List<Activity> {
        var items = activityDao!!.findAll()
        return items
    }


    /**
     * 取得所有活动信息，关联创建人
     */
    fun getAllActivityItems(): Result<Record> {
        var sql = "select t1.*, t2.displayname from activity t1 left join user t2 on t1.created_by = t2.id"
        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 取得最新活动以及每个活动的参与人数信息
     * 前50条记录
     */
    fun getAllActivityUserItems(): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "order by t1.start_time desc " +
                "limit 50"
        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 取得最新活动以及每个活动的参与人数信息
     * 前50条记录
     */
    fun getAllActivityUserItems(status: Int): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} " +
                "order by t1.start_time desc " +
                "limit 50"
        var strStatus = ""
        if (status == 1) {
            //未开始的活动
            strStatus = "and t1.start_time > CURDATE()"
        } else if (status == 2) {
            //进行中的活动
            strStatus = "and t1.start_time <= CURDATE() and end_time > CURDATE()"
        } else if (status == 3) {
            //已结束的活动
            strStatus = "and t1.end_time <= CURDATE()"
        }

        sql = sql.replace("{0}", strStatus)
        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 按活动标题、分类、状态查询活动
     */
    fun getAllActivityUserItems(title: String?, tags: String?, status: String?): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_user where activity_id = t1.id and check_in_time is not null) check_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} {1} {2}" +
                "order by t1.start_time desc "
        var strCondition = ""
        if (!title.isNullOrBlank()) {
            strCondition = "and t1.title like '%{0}%'".replace("{0}", title!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!tags.isNullOrBlank() && tags != "0") {
            strCondition = "and t1.tags = '{0}'".replace("{0}", tags!!)
        }
        sql = sql.replace("{1}", strCondition)


        if (status == "1") {
            //未开始的活动
            strCondition = "and t1.start_time > CURDATE()"
        } else if (status == "2") {
            //进行中的活动
            strCondition = "and t1.start_time <= CURDATE() and end_time > CURDATE()"
        } else if (status == "3") {
            //已结束的活动
            strCondition = "and t1.end_time <= CURDATE()"
        }
        sql = sql.replace("{2}", strCondition)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 活动报名信息
     */
    fun getAttendUsers(start: String?, end: String?, activityId: String?, title: String?, mobile: String?, real_name: String?, ticket_title: String?, checked: String?, status: String?, other_info: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.id, t1.user_id, t1.activity_id, t1.activity_ticket_id, t1.created, t1.created_by " +
                ", t1.attend_time, t1.check_in_time, t1.real_name, t1.mobile, t1.other_info, t1.price, t1.score " +
                ", t2.displayname, t2.avatar user_avatar " +
                ", t3.title, t4.price as activity_price, t4.title as ticket_title " +
                "from activity_user t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id and t3.Id = t4.activity_id " +
                "where 1=1 {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} " +
                "order by t1.created desc " +
                "{99}"
        var strCondition = ""
        if (!mobile.isNullOrBlank()) {
            strCondition = "and t1.mobile = '{0}'".replace("{0}", mobile!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!title.isNullOrBlank()) {
            strCondition = "and t3.title like '%{0}%'".replace("{0}", title!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank()) {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!activityId.isNullOrBlank()) {
            strCondition = "and t1.activity_id = {0}".replace("{0}", activityId!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!checked.isNullOrBlank()) {
            strCondition = "and t1.check_in_time is not null"
        }
        sql = sql.replace("{4}", strCondition)

        if (!ticket_title.isNullOrBlank()) {
            strCondition = "and t4.title like '%{0}%'".replace("{0}", ticket_title!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!start.isNullOrBlank()) {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{6}", strCondition)

        if (!end.isNullOrBlank()) {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{7}", strCondition)

        if (!status.isNullOrBlank()) {
            strCondition = " and t1.status = $status "
        } else {
            strCondition = " and (t1.status is null or t1.status = 0)  "
        }
        sql = sql.replace("{8}", strCondition)

        if (!other_info.isNullOrBlank()) {
            strCondition = "and t1.other_info like '%{0}%'".replace("{0}", other_info!!)
        }
        sql = sql.replace("{9}", strCondition)

        var strLimit = ""
        if (strCondition.isNullOrBlank()) {
            //如果无条件，默认取得最近的100条记录
            //strLimit = "limit 100"
        }
        sql = sql.replace("{99}", strLimit)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 阳光杯活动报名信息
     */
    fun getSunnyCupAttendUsers(start: String?, end: String?, activityId: String?, title: String?, mobile: String?, real_name: String?, ticket_title: String?, checked: String?, status: String?, other_info: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.id, t1.user_id, t1.activity_id, t1.activity_ticket_id, t1.created, t1.created_by " +
                ", t1.attend_time, t1.check_in_time, t1.real_name, t1.mobile, t1.other_info, t1.price, t1.score, t1.status " +
                ", t2.displayname, t2.avatar user_avatar " +
                ", t3.title, t4.price as activity_price, t4.title as ticket_title " +
                "from activity_user t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id and t3.Id = t4.activity_id " +
                "where t1.activity_id = 128 {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} " +
                "order by t1.created desc " +
                "{99}"
        var strCondition = ""
        if (!mobile.isNullOrBlank()) {
            strCondition = "and t1.mobile = '{0}'".replace("{0}", mobile!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!title.isNullOrBlank()) {
            strCondition = "and t3.title like '%{0}%'".replace("{0}", title!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank()) {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!activityId.isNullOrBlank()) {
            strCondition = "and t1.activity_id = {0}".replace("{0}", activityId!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!checked.isNullOrBlank()) {
            strCondition = "and t1.check_in_time is not null"
        }
        sql = sql.replace("{4}", strCondition)

        if (!ticket_title.isNullOrBlank()) {
            strCondition = "and t4.title like '%{0}%'".replace("{0}", ticket_title!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!start.isNullOrBlank()) {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{6}", strCondition)

        if (!end.isNullOrBlank()) {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{7}", strCondition)

        if (!status.isNullOrBlank()) {
            strCondition = " and t1.status = $status "
        } else {
            strCondition = " and (t1.status is null or t1.status = 0)  "
        }
        sql = sql.replace("{8}", strCondition)

        if (!other_info.isNullOrBlank()) {
            strCondition = "and t1.other_info like '%{0}%'".replace("{0}", other_info!!)
        }
        sql = sql.replace("{9}", strCondition)

        var strLimit = ""
        if (strCondition.isNullOrBlank()) {
            strLimit = ""
        }
        sql = sql.replace("{99}", strLimit)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 删除活动
     */
    fun deleteById(id: Int) {
        activityDao!!.deleteById(id)
    }

    /**
     * 收藏活动
     */
    fun createActivityFavorite(item: ActivityFavorite) {
        item.created = DateUtil.date().toTimestamp()
        activityFavoriteDao!!.insert(item)
    }

    /**
     * 取得活动收藏数
     */
    fun getActivityFavoriteCount(activityId: Int): Int {
        val count = activityFavoriteDao!!.fetch(Tables.ACTIVITY_FAVORITE.ACTIVITY_ID, activityId).size
        return count
    }


    /**
     * 取得用户收藏的活动
     */
    fun getUserFavoriteActivities(userId: Int): Result<Record> {
        var sql = "select t1.* from activity t1 " +
                "left join activity_favorite t2 on t1.id = t2.activity_id and t2.user_id = ? "
        var items = create!!.resultQuery(sql, userId).fetch()
        return items
    }

    /**
     * 活动统计
     */
    fun getActivityStatisticsByTicket(activityId: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.activity_ticket_id, t2.title ticket_title, t1.attendcount, t1.checkcount " +
                "from (select activity_ticket_id, count(attend_time) as attendcount, count(check_in_time) as checkcount from activity_user where activity_id = ? group by activity_ticket_id) t1 " +
                "inner join activity_ticket t2 " +
                "on t1.activity_ticket_id = t2.id"
        var items = create!!.resultQuery(sql, activityId).fetch()
        return items
    }

    /**
     * 活动签到人数
     */
    fun getActivityCheckCount(activityId: String?): Any? {
        var sql = "select count(*) from activity_user where activity_id = ? and check_in_time is not null"
        var count = create!!.resultQuery(sql, activityId).single()[0]
        return count
    }

    /**
     * 活动报名人数
     */
    fun getActivityAttendCount(activityId: String?): Any {
        var sql = "select count(*) from activity_user where activity_id = ?"
        var count = create!!.resultQuery(sql, activityId).single()[0]
        return count
    }

    /**
     * 更新活动报名状态
     */
    fun updateActivityUserStatus(id: Int, status: Int?): Any {
        val sql = "update activity_user set status = ? where id = ?"
        var rs = create!!.execute(sql, status, id)
        return rs
    }
}