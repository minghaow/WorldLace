package nanshen.dao.impl;

import nanshen.dao.ColorTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.ColorTag;
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
public class ColorTagDaoImpl extends BaseDao implements ColorTagDao {

    @Override
    public ColorTag insert(ColorTag colorTag) {
        return dao.insert(colorTag);
    }

    @Override
    public List<ColorTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(ColorTag.class, cnd);
    }

}
