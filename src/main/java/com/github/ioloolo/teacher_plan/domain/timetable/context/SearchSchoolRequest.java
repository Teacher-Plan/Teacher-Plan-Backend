package com.github.ioloolo.teacher_plan.domain.timetable.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SearchSchoolRequest {

    private String query;
}