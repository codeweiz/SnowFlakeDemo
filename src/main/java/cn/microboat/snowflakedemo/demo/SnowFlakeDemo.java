package cn.microboat.snowflakedemo.demo;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

import javax.annotation.PostConstruct;

/**
 * @author zhouwei
 */
public class SnowFlakeDemo {
    /**
     * 机器编号
     */
    private static long workerId = 0;

    /**
     * 机房编号
     * */
    private static long dataCenterId = 1;

    private Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized long snowflakeId() {
        return this.snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long dataCenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        SnowFlakeDemo snowFlakeDemo = new SnowFlakeDemo();
        Snowflake snowflake = new Snowflake(SnowFlakeDemo.workerId, SnowFlakeDemo.dataCenterId);
        System.out.println(snowflake.nextId());
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(snowflake.nextId());
            }, String.valueOf(i)).start();
        }
    }
}
