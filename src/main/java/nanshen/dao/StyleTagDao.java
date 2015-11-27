package nanshen.dao;

import nanshen.data.StyleTag;

import java.util.List;

/**
 * Style tag database DAO
 *
 * @author WANG Minghao
 */
public interface StyleTagDao {

    StyleTag insert(StyleTag styleTag);

    List<StyleTag> getAll();

}
