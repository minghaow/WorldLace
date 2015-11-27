package nanshen.dao.impl;

import nanshen.dao.ClothTypeTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.ClothTypeTag;
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
public class ClothTypeTagDaoImpl extends BaseDao implements ClothTypeTagDao {

    @Override
    public ClothTypeTag insert(ClothTypeTag clothTypeTag) {
        return dao.insert(clothTypeTag);
    }

    @Override
    public List<ClothTypeTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(ClothTypeTag.class, cnd);
    }

}
