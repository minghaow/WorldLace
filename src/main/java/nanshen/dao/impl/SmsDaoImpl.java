package nanshen.dao.impl;

import nanshen.dao.SmsDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sms;
import org.springframework.stereotype.Repository;

/**
 * Sms content backup
 *
 * @Author WANG Minghao
 */
@Repository
public class SmsDaoImpl extends BaseDao implements SmsDao {

    @Override
    public boolean insert(Sms sms) {
        return dao.insert(sms) != null;
    }
}
