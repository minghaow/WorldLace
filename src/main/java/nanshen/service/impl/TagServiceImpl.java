package nanshen.service.impl;

import nanshen.constant.TimeConstants;
import nanshen.dao.LookTagDao;
import nanshen.data.LookTag;
import nanshen.service.TagService;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TagServiceImpl
 *
 * @author WANG Minghao
 */
@Service
public class TagServiceImpl extends ScheduledService implements TagService {

    @Autowired
    private LookTagDao lookTagDao;

    List<LookTag> lookTagList = new ArrayList<LookTag>();

    @Override
    public void update() {
        lookTagList = lookTagDao.getAll();
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public List<LookTag> getAll() {
        return lookTagList;
    }

    @Override
    public long getCnt() {
        return lookTagList.size();
    }

}
