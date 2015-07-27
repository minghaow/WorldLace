package nanshen.dao;

import nanshen.data.LookTag;

import java.util.List;

/**
 * LookTagDao 标签数据库操作
 *
 * @author WANG Minghao
 */
public interface LookTagDao {

    LookTag insert(LookTag lookTag);

    List<LookTag> getAll();

}
