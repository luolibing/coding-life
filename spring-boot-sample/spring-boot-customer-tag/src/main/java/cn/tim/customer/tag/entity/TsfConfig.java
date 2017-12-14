package cn.tim.customer.tag.entity;

/**
 * User: luolibing
 * Date: 2017/12/14 15:09
 */
public class TsfConfig {

    private String host;

    private Integer port;

    private String alias;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "TsfConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
