package nanshen.dao;

import nanshen.data.ClothTypeTag;

import java.util.List;

/**
 * Cloth type tag database DAO
 *
 * @author WANG Minghao
 */
public interface ClothTypeTagDao {

    ClothTypeTag insert(ClothTypeTag clothTypeTag);

    List<ClothTypeTag> getAll();

}
