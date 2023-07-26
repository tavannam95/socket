package com.a2m.gen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "GEN_EVENT_MGT")
public class EventMgt {
    @Id
    @Column(name = "EVENT_ID", nullable = false, length = 50)
    private long id;

    @Column(name = "FROM_DT")
    private String fromDt;

    @Column(name = "TO_DT")
    private String toDt;

    @Column(name = "INS_DT")
    private String insDt;

    @Column(name = "DISCOUNT")
    private Integer discount;

    @Column(name = "EVENT_NM")
    private String eventNm;

    @Column(name = "UPDATE_DT")
    private String updateDt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getInsDt() {
        return insDt;
    }

    public void setInsDt(String insDt) {
        this.insDt = insDt;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getEventNm() {
        return eventNm;
    }

    public void setEventNm(String eventNm) {
        this.eventNm = eventNm;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

}