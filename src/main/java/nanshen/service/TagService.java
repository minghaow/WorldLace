package nanshen.service;

import nanshen.data.LookTag;

import java.util.List;

/**
 * Tag related services
 *
 * @author WANG Minghao
 */
public interface TagService {

    /**
     * Get all of the tag
     *
     * @return List<LookTag>
     */
    List<LookTag> getAll();

    /**
     * Get the count of tags
     *
     * @return long
     */
    long getCnt();
}
