package nanshen.dao;

import nanshen.data.BrandTag;

import java.util.List;

/**
 * LookTagDao 标签数据库操作
 *
 * @author WANG Minghao
 */
public interface BrandTagDao {

    BrandTag insert(BrandTag brandTag);

    List<BrandTag> getAll();

}
