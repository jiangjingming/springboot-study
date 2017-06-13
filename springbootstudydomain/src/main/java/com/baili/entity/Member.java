package com.baili.entity;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
    private Long uid;

    private String employeeId;

    private Boolean gender;

    private String nickname;

    private String uname;

    private String passwd;

    private String email;

    private String portrait;

    private String bigPortrait;

    private String phone;

    private Integer regTime;

    private Integer loginNum;

    private Integer lastLogin;

    private String lastIp;

    private Byte isSupper;

    private Date timeline;

    private String unionId;

    private static final long serialVersionUID = 1L;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public String getBigPortrait() {
        return bigPortrait;
    }

    public void setBigPortrait(String bigPortrait) {
        this.bigPortrait = bigPortrait == null ? null : bigPortrait.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getRegTime() {
        return regTime;
    }

    public void setRegTime(Integer regTime) {
        this.regTime = regTime;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    public Byte getIsSupper() {
        return isSupper;
    }

    public void setIsSupper(Byte isSupper) {
        this.isSupper = isSupper;
    }

    public Date getTimeline() {
        return timeline;
    }

    public void setTimeline(Date timeline) {
        this.timeline = timeline;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", gender=").append(gender);
        sb.append(", nickname=").append(nickname);
        sb.append(", uname=").append(uname);
        sb.append(", passwd=").append(passwd);
        sb.append(", email=").append(email);
        sb.append(", portrait=").append(portrait);
        sb.append(", bigPortrait=").append(bigPortrait);
        sb.append(", phone=").append(phone);
        sb.append(", regTime=").append(regTime);
        sb.append(", loginNum=").append(loginNum);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", lastIp=").append(lastIp);
        sb.append(", isSupper=").append(isSupper);
        sb.append(", timeline=").append(timeline);
        sb.append(", unionId=").append(unionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}