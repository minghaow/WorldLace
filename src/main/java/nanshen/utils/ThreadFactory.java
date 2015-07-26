package nanshen.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个通用的可以配置名称和是否后台运行的ThreadFactory
 *
 * @author chenjh
 * 陈建华，2014年夏季离职。在职时作为惠惠网站项目的Scrum Master，为惠惠网站项目做出了巨大的贡献，
 * 这段代码是从网站项目中拷贝出来的，原作者就是建华。
 * 署上他的名字，谨以此纪念已经离开的英雄们。
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {
    
    private static final String SEPARATOR = "-";
    
    private final AtomicInteger threadNo = new AtomicInteger(1);
    
    private final ThreadGroup group;
    
    private final String threadNamePrefix;

    private final boolean isDaemon;
    
    public ThreadFactory(String threadNamePrefix, boolean isDaemon){
        this.threadNamePrefix = threadNamePrefix;
        this.isDaemon = isDaemon;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, threadNamePrefix + SEPARATOR + threadNo.getAndIncrement());
        thread.setDaemon(isDaemon);
        return thread;
    }

}
