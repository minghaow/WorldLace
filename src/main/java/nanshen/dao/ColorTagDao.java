package nanshen.dao;

import nanshen.data.ColorTag;

import java.util.List;

/**
 * Color tag database DAO
 *
 * @author WANG Minghao
 */
public interface ColorTagDao {

    ColorTag insert(ColorTag colorTag);

    List<ColorTag> getAll();

}
