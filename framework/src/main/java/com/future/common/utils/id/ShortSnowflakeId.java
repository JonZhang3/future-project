package com.future.common.utils.id;

/**
 * 雪花ID生成算法
 *
 * @author JonZhang
 */
class ShortSnowflakeId {

    private static final long startTime = 1660815668070L;
    private static final long workerIdBits = 2L;// workerId 的位数  少 3 位
    private static final long maxWorkerId = ~(-1L << workerIdBits);// 2位的 workerId
    private static final long sequenceBits = 10L;// 增长序列的位数
    private static final long sequenceMask = ~(-1L << sequenceBits);// 增长序列最大值
    private static final long workerIdShift = sequenceBits;// workerId 的左移位置
    private static final long timestampLeftShift = sequenceBits + workerIdBits;// 时间左移序列的位置

    private final long workerId;// 用于分布式系统中
    private long sequence = 0L;// 增长序列初始值
    private long lastTimestamp = -1L;// 上一个时间戳

    ShortSnowflakeId(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized Long next() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalArgumentException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            // 通过位与运算保证计算的结果范围始终是范围值
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - startTime) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}
