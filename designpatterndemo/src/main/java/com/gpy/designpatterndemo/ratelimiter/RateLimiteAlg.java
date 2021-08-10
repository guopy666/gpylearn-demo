package com.gpy.designpatterndemo.ratelimiter;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName RateLimiteAlg
 * @Description
 * @Author guopy
 * @Date 2021/8/4 15:28
 */
public class  RateLimiteAlg {

    private final static Long TRY_LOCK_TIMEOUT = 200L; //200ms
    private Stopwatch stopwatch;
    private AtomicInteger currentCount = new AtomicInteger(0);
    private Integer limit;
    private Lock lock = new ReentrantLock();

    public RateLimiteAlg(int limit){
        this(limit, Stopwatch.createStarted());
    }

    @VisibleForTesting
    protected RateLimiteAlg(int limit, Stopwatch stopwatch){
        this.limit = limit;
        this.stopwatch = stopwatch;
    }

    public boolean tryAcquire() throws InternalException {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit){
            return true;
        }

        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)){
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)){
                        currentCount.set(0);
                        stopwatch.reset();
                    }
                    updatedCount = currentCount.incrementAndGet();
                    return updatedCount <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new InternalException("tryAcquire() wait lock too long");
            }
        } catch (InterruptedException e) {
            throw new InternalException("tryAcquire() is interrupted by lock_time_out");
        }
    }
}
