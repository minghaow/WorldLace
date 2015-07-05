package nanshen.dao.impl;

import nanshen.dao.LookInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.LookInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
public class LookInfoDaoImpl extends BaseDao implements LookInfoDao {

    @Override
    public LookInfo insert(LookInfo lookInfo) {
        return dao.insert(lookInfo);
    }

    @Override
    public List<LookInfo> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(LookInfo.class, cnd);
    }
}
