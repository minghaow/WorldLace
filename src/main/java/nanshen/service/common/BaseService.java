package nanshen.service.common;

import nanshen.dao.OrderLogDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service层的基类，每个Service都应该继承该基类
 *
 * @author WANG Minghao
 */
public abstract class BaseService {

    @Autowired
    protected OrderLogDao orderLogDao;

}
