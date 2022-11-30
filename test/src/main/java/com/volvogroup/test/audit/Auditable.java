package com.volvogroup.test.audit;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @CreatedDate
    @Column(name = "registration_date", updatable = false)
    protected Date registrationDate;

    @LastModifiedDate
    @Column(name = "last_update_info")
    protected Date lastUpdateInfo;

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastUpdateInfo() {
        return lastUpdateInfo;
    }

    public void setLastUpdateInfo(Date lastUpdateInfo) {
        this.lastUpdateInfo = lastUpdateInfo;
    }
}