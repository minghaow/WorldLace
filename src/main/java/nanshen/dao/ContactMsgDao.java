package nanshen.dao;

import nanshen.data.ContactMsg;

/**
 * @author WANG Minghao
 */
public interface ContactMsgDao {

    ContactMsg insert(ContactMsg contentMsg);

    ContactMsg get(long contentId);

    boolean remove(long contentId);

}
