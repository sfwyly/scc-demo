package bean;

/**
 * @ClassName DBParam
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 13:43
 * @Version 1.0
 **/
public class DBParam {

    private String url;

    private String username;

    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBParam() {
    }

    public DBParam(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
