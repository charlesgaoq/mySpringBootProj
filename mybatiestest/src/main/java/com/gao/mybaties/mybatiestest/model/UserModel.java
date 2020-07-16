package com.gao.mybaties.mybatiestest.model;

import java.util.Date;

public class UserModel {
    private Integer useId;

    private String useName;

    private String useSex;

    private Integer useAge;

    private String useIdNo;

    private String usePhoneNum;

    private String useEmail;

    private Date createTime;

    private Date modifyTime;

    private String useState;

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName == null ? null : useName.trim();
    }

    public String getUseSex() {
        return useSex;
    }

    public void setUseSex(String useSex) {
        this.useSex = useSex == null ? null : useSex.trim();
    }

    public Integer getUseAge() {
        return useAge;
    }

    public void setUseAge(Integer useAge) {
        this.useAge = useAge;
    }

    public String getUseIdNo() {
        return useIdNo;
    }

    public void setUseIdNo(String useIdNo) {
        this.useIdNo = useIdNo == null ? null : useIdNo.trim();
    }

    public String getUsePhoneNum() {
        return usePhoneNum;
    }

    public void setUsePhoneNum(String usePhoneNum) {
        this.usePhoneNum = usePhoneNum == null ? null : usePhoneNum.trim();
    }

    public String getUseEmail() {
        return useEmail;
    }

    public void setUseEmail(String useEmail) {
        this.useEmail = useEmail == null ? null : useEmail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState == null ? null : useState.trim();
    }
}