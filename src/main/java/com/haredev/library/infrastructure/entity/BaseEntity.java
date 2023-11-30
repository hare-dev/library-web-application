package com.haredev.library.infrastructure.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    private String uuid = UUID.randomUUID().toString();

    @Version
    private Integer version;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BaseEntity that)) return false;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}