package nanshen.dao.common;

import org.nutz.dao.ConnCallback;
import org.nutz.dao.impl.DaoRunner;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 为了使得NutDao兼容Spring事务而设置的DaoRunner
 *
 * @Author Minghao Wang <wangmh@rd.netease.com>
 */
@Repository
public class SpringDaoRunnerForNutz implements DaoRunner {

    @Override
    public void run(DataSource dataSource, ConnCallback callback) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try {
            callback.invoke(con);
        }
        catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            else
                throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

}
