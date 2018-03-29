package com.kylin.activity.util.thymeleaf

import com.kylin.activity.util.KylinUtil
import java.util.HashMap
import org.thymeleaf.context.IProcessingContext
import org.thymeleaf.dialect.IExpressionEnhancingDialect
import org.thymeleaf.dialect.AbstractDialect


class CustomDialect : AbstractDialect(), IExpressionEnhancingDialect {

    override fun getPrefix(): String {
        // @see org.thymeleaf.dialect.IDialect#getPrefix
        return ""
    }

    override fun getAdditionalExpressionObjects(ctx: IProcessingContext): Map<String, Any> {
        val expressions = HashMap<String, Any>()
        expressions.put("kylin", KylinUtil())
        return expressions
    }
}