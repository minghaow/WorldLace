package nanshen.dao;

import nanshen.data.ContentStatus;
import nanshen.data.LookInfo;

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

    List<LookInfo> getAll(ContentStatus status);
}
