package com.github.ioloolo.schoolhelper_teacher.domain.meal.data;

import lombok.Data;

import java.util.List;

@Data
public final class Meal {
    private final String type;
    private final List<String> menu;
}
