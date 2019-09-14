package bean;

/**
 * @ClassName RequestResult
 * @Description 返回数据格式
 * @Author 逝风无言
 * @Data 2019/9/12 13:55
 * @Version 1.0
 **/
public class RequestResult {

    /**
     * 返回状态
     */
    protected String status;

    /**
     * 错误消息
     */
    protected String errMsg;

    /**
     * 数据
     */
    protected Object data;

    public RequestResult(String status, String errMsg, Object data) {
        this.status = status;
        this.errMsg = errMsg;
        this.data = data;
    }

    public RequestResult() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
