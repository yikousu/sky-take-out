package com.sky.task;

// 雪花算法生成唯一ID的类
public class SnowflakeIdGenerator {
    private final long startTime = 1577836800000L; // 2020-01-01 00:00:00
    private final long workerIdBits = 10L; // 机器ID占用的位数
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits); // 最大的机器ID
    private final long sequenceBits = 12L; // 序列号占用的位数
    private final long workerIdShift = sequenceBits; // 机器ID左移位数
    private final long timestampLeftShift = sequenceBits + workerIdBits; // 时间戳左移位数
    private final long sequenceMask = -1L ^ (-1L << sequenceBits); // 序列号的掩码，用于保证序列号不超出范围

    private long workerId; // 当前机器ID
    private long sequence = 0L; // 当前序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    // 构造函数，传入当前机器的ID
    public SnowflakeIdGenerator(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker ID can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    // 生成下一个唯一ID的方法
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis(); // 获取当前时间戳

        if (timestamp < lastTimestamp) { // 检查时钟是否回退
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        if (timestamp == lastTimestamp) { // 如果是同一毫秒内生成的ID
            sequence = (sequence + 1) & sequenceMask; // 增加序列号
            if (sequence == 0) { // 如果序列号溢出
                timestamp = tilNextMillis(lastTimestamp); // 等待下一毫秒
            }
        } else {
            sequence = 0L; // 不同毫秒序列号重置为0
        }

        lastTimestamp = timestamp; // 更新上次生成ID的时间戳

        // 生成ID并返回
        return ((timestamp - startTime) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    // 等待直到下一毫秒
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    // 测试方法
    public static void main(String[] args) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1); // 例子中使用的机器ID为1
        for (int i = 0; i < 10; i++) {
            long id = idGenerator.nextId(); // 生成ID
            System.out.println("Generated ID: " + id); // 打印生成的ID
        }
    }
}
