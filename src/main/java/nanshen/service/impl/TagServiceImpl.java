package nanshen.service.impl;

import nanshen.constant.TimeConstants;
import nanshen.dao.*;
import nanshen.data.*;
import nanshen.service.TagService;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TagServiceImpl
 *
 * @author WANG Minghao
 */
@Service
public class TagServiceImpl extends ScheduledService implements TagService {

    @Autowired
    private StyleTagDao styleTagDao;

    @Autowired
    private ClothTypeTagDao clothTypeTagDao;

    @Autowired
    private ColorTagDao colorTagDao;

    @Autowired
    private SeasonTagDao seasonTagDao;

    @Autowired
    private BrandTagDao brandTagDao;

    List<StyleTag> styleTagList = new ArrayList<StyleTag>();
    List<ClothTypeTag> clothTypeTagList = new ArrayList<ClothTypeTag>();
    List<ColorTag> colorTagList = new ArrayList<ColorTag>();
    List<SeasonTag> seasonTagList = new ArrayList<SeasonTag>();
    List<BrandTag> brandTagList = new ArrayList<BrandTag>();

    @Override
    public void update() {
        styleTagList = styleTagDao.getAll();
        clothTypeTagList = clothTypeTagDao.getAll();
        colorTagList = colorTagDao.getAll();
        seasonTagList = seasonTagDao.getAll();
        brandTagList = brandTagDao.getAll();
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public List<StyleTag> getAllStyleTag() {
        return styleTagList;
    }

    @Override
    public List<ColorTag> getAllColorTag() {
        return colorTagList;
    }

    @Override
    public List<SeasonTag> getAllSeasonTag() {
        return seasonTagList;
    }

    @Override
    public List<ClothTypeTag> getAllClothTypeTag() {
        return clothTypeTagList;
    }

    @Override
    public List<BrandTag> getAllBrandTag() {
        return brandTagList;
    }

    @Override
    public ComplexTag getSkuComplexTag(long lookId) {
        // TODO: fill the blank
        return new ComplexTag();
    }

    @Override
    public BrandTag insertBrandTag(String brandName, String description) {
        return brandTagDao.insert(new BrandTag(brandName, description, new Date(), new Date()));
    }

    @Override
    public long getCnt() {
        return styleTagList.size();
    }

}
