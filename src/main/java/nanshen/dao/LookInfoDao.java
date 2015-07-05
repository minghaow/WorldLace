package nanshen.dao;

import nanshen.data.LookInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface LookInfoDao {

    LookInfo insert(LookInfo lookInfo);

    List<LookInfo> getAll();

}
