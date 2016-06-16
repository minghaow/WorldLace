package nanshen.dao.common;

import nanshen.data.SystemUtil.PageInfo;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Dao实现的基类，定义了Dao层的基本方法，所有Dao层类都应继承自该类
 * <p />
 * 之所以存在这样的基类是为了方便将dao层中经常使用的方法提取出来，减少代码重复
 *
 * @author WANG Minghao
 */
public abstract class BaseDao {

    /** 我们使用了一个轻量级的dao层框架Nutz，这个就是NutzDao的实例 */
    @Autowired
    protected Dao dao;

    /**
     * 生成分页信息数据
     * <p />
     * 将高层的{@link PageInfo}数据结构转换为Nutz使用的{@link org.nutz.dao.pager.Pager}
     *
     * @param pageInfo
     * @return
     */
    protected Pager genaratePager(PageInfo pageInfo) {
        return dao.createPager(pageInfo.getPage(), pageInfo.getMaxNumPerPage());
    }

}
