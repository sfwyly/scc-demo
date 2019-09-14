package bean;

import java.time.Duration;

/**
 * @ClassName RedisParam
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 13:42
 * @Version 1.0
 **/
public class RedisParam {

    private String host;

    private int port;

    private Duration timeout;

    public RedisParam() {
    }

    public RedisParam(String host, int port, Duration timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}
