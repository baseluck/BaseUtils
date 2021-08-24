package com.luck.basemodule.utils;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @描述: 创建线程池
 * @创建日期: 2021/4/15 13:48
 * @author: ProcyonLotor
 */
public class CustomThreadPoolUtil {

    private static final ThreadPoolExecutor POOL;

    static {
        POOL = new ThreadPoolExecutor(1, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new CustomThreadFactory(), new CustomRejectedExecutionHandler());

    }

    public static void destory() {
        POOL.shutdown();
    }

    public static void execute(Runnable r) {
        POOL.execute(r);
    }

    public static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = CustomThreadPoolUtil.class.getSimpleName() + count.incrementAndGet();
            t.setName(threadName);

            return t;
        }
    }

    public static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        }

    }
}
