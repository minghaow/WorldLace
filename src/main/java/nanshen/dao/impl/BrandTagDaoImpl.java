package nanshen.dao.impl;

import nanshen.dao.BrandTagDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.BrandTag;
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
public class BrandTagDaoImpl extends BaseDao implements BrandTagDao {

    @Override
    public BrandTag insert(BrandTag brandTag) {
        return dao.insert(brandTag);
    }

    @Override
    public List<BrandTag> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(BrandTag.class, cnd);
    }

}
