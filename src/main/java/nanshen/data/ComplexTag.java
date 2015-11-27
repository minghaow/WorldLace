package nanshen.data;

import java.util.ArrayList;
import java.util.List;

/**
 * ComplexTag
 * <br/>
 * <strong>NOTE: </strong> All tag is needed.
 *
 * @author WANG Minghao
 */
public class ComplexTag {

    /** Style Tag List */
    List<StyleTag> styleTagList = new ArrayList<StyleTag>();

    /** Color Tag */
    ColorTag colorTag;

    /** Season Tag List */
    List<SeasonTag> seasonTagList = new ArrayList<SeasonTag>();

    /** Cloth Type Tag */
    ClothTypeTag clothTypeTag;

    /** Brand Tag */
    BrandTag brandTag;

    public ComplexTag() {}

    public ComplexTag(BrandTag brandTag, ClothTypeTag clothTypeTag, ColorTag colorTag,
                      List<SeasonTag> seasonTagList, List<StyleTag> styleTagList) {
        this.brandTag = brandTag;
        this.clothTypeTag = clothTypeTag;
        this.colorTag = colorTag;
        this.seasonTagList = seasonTagList;
        this.styleTagList = styleTagList;
    }

    public BrandTag getBrandTag() {
        return brandTag;
    }

    public void setBrandTag(BrandTag brandTag) {
        this.brandTag = brandTag;
    }

    public ClothTypeTag getClothTypeTag() {
        return clothTypeTag;
    }

    public void setClothTypeTag(ClothTypeTag clothTypeTag) {
        this.clothTypeTag = clothTypeTag;
    }

    public ColorTag getColorTag() {
        return colorTag;
    }

    public void setColorTag(ColorTag colorTag) {
        this.colorTag = colorTag;
    }

    public List<SeasonTag> getSeasonTagList() {
        return seasonTagList;
    }

    public void setSeasonTagList(List<SeasonTag> seasonTagList) {
        this.seasonTagList = seasonTagList;
    }

    public List<StyleTag> getStyleTagList() {
        return styleTagList;
    }

    public void setStyleTagList(List<StyleTag> styleTagList) {
        this.styleTagList = styleTagList;
    }
}
