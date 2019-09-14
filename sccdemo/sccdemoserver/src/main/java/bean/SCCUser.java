package bean;



/**
 * @ClassName SCCUser
 * @Description 用户信息类
 * @Author 逝风无言
 * @Data 2019/9/12 14:05
 * @Version 1.0
 **/
public class SCCUser {

    private String userName;

    private String lastLoginDateTime;

    private transient long uniqueId;

    public SCCUser(String userName,String lastLoginDate ,  String lastLoginTime) {
        this.userName = userName;
        if(lastLoginDate==null||lastLoginTime==null){
            this.lastLoginDateTime=null;
        }else{
            this.lastLoginDateTime = lastLoginDate+" "+lastLoginTime;
        }

    }

    public SCCUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(String lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
