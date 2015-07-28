package nanshen.dao.impl;

import nanshen.dao.LookInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.LookInfo;
import nanshen.data.PublicationStatus;
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
public class LookInfoDaoImpl extends BaseDao implements LookInfoDao {

    @Override
    public LookInfo insert(LookInfo lookInfo) {
        return dao.insert(lookInfo);
    }

    @Override
    public LookInfo get(long lookId) {
        return dao.fetch(LookInfo.class, lookId);
    }

    @Override
    public boolean update(LookInfo lookInfo) {
        return dao.update(lookInfo) == 1;
    }

    @Override
    public boolean remove(long lookId) {
        return dao.delete(LookInfo.class, lookId) == 1;
    }

    @Override
    public List<LookInfo> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01").desc("id");
        return dao.query(LookInfo.class, cnd);
    }

    @Override
    public List<LookInfo> getAll(PublicationStatus status) {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01")
                .and("status", "=", status).desc("id");
        return dao.query(LookInfo.class, cnd);
    }
}
