package com.github.ioloolo.teacher_plan.domain.meal.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMealRequest {

    private String name;
    private String location;
}
