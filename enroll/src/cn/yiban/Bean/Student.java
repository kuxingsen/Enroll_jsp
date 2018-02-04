package cn.yiban.Bean;

/**
 * Created by Éµ±Æ on 2018/1/24.
 */
public class Student {
    private String yb_userid;
    private String yb_usernick;
    private String yb_userhead;
    private String phonenumber;
    private String instest;
    private String first;
    private String second;
    private String reason;
    private String file;

    public Student(String yb_userid, String yb_usernick) {
        this.yb_userid = yb_userid;
        this.yb_usernick = yb_usernick;
    }
    public void setAll(String phonenumber,String instest,String first,String second,String reason,String file)
    {
        setPhonenumber(phonenumber);
        setInstest( instest);
        setFirst( first);
        setSecond(second);
        setReason(reason);
        setFile(file);
    }
    public String toString()
    {
        return yb_userid+yb_usernick+phonenumber+instest+first+second+reason+file;
    }
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getInstest() {
        return instest;
    }

    public void setInstest(String instest) {
        this.instest = instest;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getYb_userhead() {
        return yb_userhead;
    }

    public void setYb_userhead(String yb_userhead) {
        this.yb_userhead = yb_userhead;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
