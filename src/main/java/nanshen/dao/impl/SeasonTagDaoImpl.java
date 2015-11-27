package nanshen.dao.impl;

import nanshen.dao.SeasonTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.SeasonTag;
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
public class SeasonTagDaoImpl extends BaseDao implements SeasonTagDao {

    @Override
    public SeasonTag insert(SeasonTag seasonTag) {
        return dao.insert(seasonTag);
    }

    @Override
    public List<SeasonTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(SeasonTag.class, cnd);
    }

}
