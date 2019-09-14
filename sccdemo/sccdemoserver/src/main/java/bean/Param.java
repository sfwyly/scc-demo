package bean;

/**
 * @ClassName Param
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 11:25
 * @Version 1.0
 **/
public class Param {
    private int id;
    private String key;
    private String value;

    public Param() {
    }

    public Param(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
