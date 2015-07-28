package nanshen.dao;

import nanshen.data.LookInfo;
import nanshen.data.PublicationStatus;

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
}
