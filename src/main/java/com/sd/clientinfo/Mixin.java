package com.sd.clientinfo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Mixin {
    static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    static final Lock rLock = rwLock.readLock();
    static final Lock wLock = rwLock.writeLock();
}
