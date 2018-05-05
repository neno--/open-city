package com.github.nenomm.oc.core;

import org.springframework.data.domain.Persistable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

@MappedSuperclass
public class AbstractEntity implements Persistable<EntityIdentifier> {

    @EmbeddedId
    private EntityIdentifier id = new EntityIdentifier();

    @Transient
    private boolean isNew = true;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj.getClass().equals(this.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return ObjectUtils.nullSafeEquals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public EntityIdentifier getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
