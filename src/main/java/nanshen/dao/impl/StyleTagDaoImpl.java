package nanshen.dao.impl;

import nanshen.dao.StyleTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.StyleTag;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class StyleTagDaoImpl extends BaseDao implements StyleTagDao {

    @Override
    public StyleTag insert(StyleTag styleTag) {
        return dao.insert(styleTag);
    }

    @Override
    public List<StyleTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(StyleTag.class, cnd);
    }
}
