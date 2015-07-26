package nanshen.service.common;

import nanshen.utils.LogUtils;
import nanshen.utils.ThreadFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装了定时更新的逻辑，子类只需要指定更新间隔，并实现{@link #update()}方法即可。
 * 如果使用spring annotation进行api管理，直接使用autowired的实例即可；
 * 如果使用spring xml初始化api对象，需要配置init-method="init"；
 * 如果直接使用new生成对象，需要new之后调用init方法初始化。
 * <p />
 * <b>注意</b>：在{@link #update()}方法中执行的代码，在服务器宕机等情况下会被打断，
 * 因此请务必考虑被打断的后果，必要的时候可增加事务等额外机制保证数据完整性。
 * <p />
 * <b>注意</b>：包含PostConstruct方法的类，若出现在循环引用的链条上，会导致在未完成自动注入的情况下，
 * PostConstruct方法被执行，若此时PostConstruct方法恰好使用了未完成注入的字段就会抛空指针错误。
 * {@link ScheduledService}中包含PostConstruct方法，因此使用时需要格外小心。
 * 在必要的情况下，可以仅保留{@link ScheduledService}的{@link #update()}方法，最大限度的避免其他类引用该Service，
 * 从而防止该问题的发生。
 *
 * @author LIU Shufa
 * 刘树发，2014年春季离职，我们都称他为发哥。发哥离职的时候，惠惠海淘项目还不存在呢，
 * 所以他并没有真正意义上为买手系统编写过代码。这个定时任务类是从惠惠网站的代码中拷贝过来并修改而成的，
 * 而惠惠网站项目是发哥搭建起来的，当然这个定时任务类原型也是发哥创建的。
 * 在这里署上他的名字，谨以此纪念已经离开的英雄们。
 * @author KONG Xiangxin
 */
public abstract class ScheduledService extends BaseService {

    private static final ScheduledThreadPoolExecutor SCHEDULER = new ScheduledThreadPoolExecutor(
            20,
            new ThreadFactory("ScheduledService", true));

    private static final AtomicInteger RUNNING_TASK_COUNT = new AtomicInteger(0);

    /** 一个可以停止所有定时更新的开关，以便在重启服务的时候暂时停止定时更新 */
    private static volatile boolean runnable = true;

    /** 在定时更新停止后是否需要输出定时更新停止警告 */
    private static volatile boolean scheduleStoppedWarning = true;

    /**
     * 是否在启动时同步执行一次update
     * <p />
     * 一般情况下需要在启动时同步执行一次update，以便在启动后缓存的数据是正确的，
     * 但这样会增加启动时间，部分耗时的定时任务可以不必在启动时运行
     *
     * @return 是否需要在启动时同步执行一次update
     */
    protected boolean runPreUpdate() {
        return true;
    }

    /**
     * 更新的具体代码，子类通过实现该方法实现定时更新。
     */
    public abstract void update();

    /**
     * 返回定时更新的时间间隔，子类通过实现该方法指定更新的时间间隔
     *
     * @return 缓存主动更新的时间间隔，单位是秒
     */
    public abstract int updatePeriod();

    /** 防止误调用导致的多次初始化 */
    private volatile boolean alreadyInit = false;

    /**
     * 获取当前正在执行的定时任务的数量，以便判断是否可以重启服务器
     *
     * @return
     */
    public static int runningTaskCount() {
        return RUNNING_TASK_COUNT.get();
    }

    /**
     * 启动或者暂定所有的定时任务，使用时务必小心！
     *
     * @param value
     */
    public static void setRunnable(boolean value) {
        runnable = value;
    }

    public static void setScheduleStoppedWarning(boolean value) {
        scheduleStoppedWarning = value;
    }

    /**
     * 初始化定时更新任务，初始化过程中会同步调用一次{@link #update()}
     */
    @PostConstruct
    public synchronized void init() {
        if (alreadyInit) {
            return;
        }
        int period = updatePeriod();
        if (runPreUpdate()) {
            updateCacheWithoutException();
        }
        SCHEDULER.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                updateCacheWithoutException();
            }
        }, 30 + (int) (Math.random() * 30), period, TimeUnit.SECONDS);
        alreadyInit = true;
    }

    private void updateCacheWithoutException() {
        if (!runnable) {
            if (scheduleStoppedWarning) {
                LogUtils.info("ScheduledService: schedule service stop!");
            }
            return;
        }
        RUNNING_TASK_COUNT.incrementAndGet();
        try {
            update();
        } catch (Exception e) {
            // 异常不应该影响后续执行
            e.printStackTrace();
        } finally {
            RUNNING_TASK_COUNT.decrementAndGet();
        }
    }

}
