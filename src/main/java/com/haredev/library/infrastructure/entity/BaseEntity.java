package com.haredev.library.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Column(updatable = false, nullable = false)
    private final UUID uuid = UUID.randomUUID();

    @Version
    private Long version = 0L;

    @Override
    public boolean equals(Object that) {
        return this == that || that instanceof BaseEntity
                && Objects.equals(uuid, ((BaseEntity) that).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}