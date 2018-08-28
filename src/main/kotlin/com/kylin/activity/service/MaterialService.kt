package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.MaterialLibraryDao
import com.kylin.activity.databases.tables.pojos.MaterialLibrary
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: Richard C. Hu
 * @Date: 2018/8/16
 * @Description: 素材管理服务层
 */
@Service
class MaterialService {

    /**
     * DAO
     */
    @Autowired
    private val materialDao: MaterialLibraryDao? = null

    /**
     * 数据访问
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 添加
     * @param item: 素材信息
     */
    fun insert(item: MaterialLibrary) {
        materialDao!!.insert(item)
    }

    /**
     * 更新
     * @param item: 素材信息
     */
    fun update(item: MaterialLibrary) {
        materialDao!!.update(item)
    }

    /**
     * 获取信息集合
     * @param category: 分组
     * @return 海报信息集合
     */
    fun getMaterials(category: String?): Result<Record> {
        var sql = "select * from material_library where 1=1 "
        if (!category.isNullOrBlank()) {
            sql += "and category like '%$category%'"
        }

        sql += " order by sequence, created desc"
        return create!!.resultQuery(sql).fetch()
    }

    /**
     * 根据id,取得记录
     * @param id id
     * @return 素材信息
     */
    fun getMaterial(id: Int?): MaterialLibrary {
        return materialDao!!.findById(id)
    }

    /**
     * 删除信息
     * @param id id
     */
    fun delete(id: Int) {
        materialDao!!.deleteById(id)
    }

    /**
     * 取得图片分类信息集合
     */
    fun getMaterialCategories(): Result<Record> {
        var sql = "select distinct category from material_library where 1=1 "
        return create!!.resultQuery(sql).fetch()
    }
}