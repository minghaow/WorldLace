package nanshen.service;

import nanshen.data.*;

import java.util.List;

/**
 * Tag related services
 *
 * @author WANG Minghao
 */
public interface TagService {

    /**
     * Get all of the style tag
     *
     * @return List<StyleTag>
     */
    List<StyleTag> getAllStyleTag();

    /**
     * Get all of the color tag
     *
     * @return List<ColorTag>
     */
    List<ColorTag> getAllColorTag();

    /**
     * Get all of the season tag
     *
     * @return List<SeasonTag>
     */
    List<SeasonTag> getAllSeasonTag();

    /**
     * Get all of the cloth type tag
     *
     * @return List<ClothTypeTag>
     */
    List<ClothTypeTag> getAllClothTypeTag();

    /**
     * Get all of the brand tag
     *
     * @return List<BrandTag>
     */
    List<BrandTag> getAllBrandTag();

    /**
     * Get the complex tag info for a sku
     *
     * @return ComplexTag
     */
    ComplexTag getSkuComplexTag(long lookId);

    /**
     * Insert a new brand
     *
     * @param brandName
     * @param description
     * @return
     */
    BrandTag insertBrandTag(String brandName, String description);

    /**
     * Get the count of tags
     *
     * @return long
     */
    long getCnt();
}
