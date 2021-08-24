package com.luck.basemodule.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/23 14:33
 * @描述:
 */
public class ObjectOperateLock {
    private final static long DEFAULT_PERIOD = 200;
    private final long period;
    private long lastOperatorTime = 0;
    private final List<WeakReference> operateObjList = new LinkedList<>();
    private final HashMap<WeakReference, Long> timeHashMap = new HashMap<>();

    public ObjectOperateLock() {
        period = DEFAULT_PERIOD;
        Object obj = new Object();
    }

    public ObjectOperateLock(int minimumPeriod) {
        period = minimumPeriod;
    }

    public boolean doing(Object obj) {
        return doing(obj, period);
    }

    public boolean doing() {
        boolean doing = false;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastOperatorTime > period) {
            lastOperatorTime = currentTime;
            doing = true;
        }
        return doing;
    }

    public boolean doing(int minimumPeriod) {
        boolean doing = false;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastOperatorTime > minimumPeriod) {
            lastOperatorTime = currentTime;
            doing = true;
        }
        return doing;
    }


    public boolean doing(Object obj, long minimumPeriod) {
        boolean doing = false;
        long lastOperateTime;
        WeakReference wk = null;
        Iterator<WeakReference> iterator = operateObjList.iterator();
        while (iterator.hasNext()) {
            WeakReference w = iterator.next();
            if (w.get() == null) {
                iterator.remove();
                timeHashMap.remove(w);
            } else if (w.get() == obj) {
                wk = w;
            }
        }
        long cur = System.currentTimeMillis();
        if (wk == null) {
            wk = new WeakReference(obj);
            operateObjList.add(wk);
            timeHashMap.put(wk, cur);
            doing = true;
        } else {
            lastOperateTime = timeHashMap.get(wk);
            if (cur - lastOperateTime > minimumPeriod) {
                doing = true;
                lastOperateTime = cur;
                timeHashMap.put(wk, lastOperateTime);
            }
        }
        return doing;
    }
}
