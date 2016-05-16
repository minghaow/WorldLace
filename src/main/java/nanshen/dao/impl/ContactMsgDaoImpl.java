package nanshen.dao.impl;

import nanshen.dao.ContactMsgDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.ContactMsg;
import org.springframework.stereotype.Repository;

/**
 * ContactMsgDaoImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class ContactMsgDaoImpl extends BaseDao implements ContactMsgDao {

    @Override
    public ContactMsg insert(ContactMsg contentMsg) {
        return dao.insert(contentMsg);
    }

    @Override
    public ContactMsg get(long contentId) {
        return dao.fetch(ContactMsg.class, contentId);
    }

    @Override
    public boolean remove(long contentId) {
        return dao.delete(ContactMsg.class, contentId) == 1;
    }
}
