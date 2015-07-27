package nanshen.dao.impl;

import nanshen.dao.LookTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.LookTag;
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
public class LookTagDaoImpl extends BaseDao implements LookTagDao {

    @Override
    public LookTag insert(LookTag lookTag) {
        return dao.insert(lookTag);
    }

    @Override
    public List<LookTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(LookTag.class, cnd);
    }
}
