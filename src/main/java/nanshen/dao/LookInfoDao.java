package nanshen.dao;

import nanshen.data.LookInfo;
import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface LookInfoDao {

    LookInfo insert(LookInfo lookInfo);

    LookInfo get(long lookId);

    boolean update(LookInfo lookInfo);

    boolean remove(long lookId);

    List<LookInfo> getAll();

    List<LookInfo> getAll(PublicationStatus status);

    List<LookInfo> getAll(PublicationStatus status, PageInfo pageInfo);

    boolean remove(long lookId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

}
