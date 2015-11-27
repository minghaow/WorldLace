package nanshen.dao;

import nanshen.data.SeasonTag;

import java.util.List;

/**
 * Season Tag database DAO
 *
 * @author WANG Minghao
 */
public interface SeasonTagDao {

    SeasonTag insert(SeasonTag seasonTag);

    List<SeasonTag> getAll();

}
