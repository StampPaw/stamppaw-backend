package org.example.stamppaw_backend.companion.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecruitmentStatus {
    ONGOING("모집 중"),
    CLOSED("마감");

    private final String description;
}