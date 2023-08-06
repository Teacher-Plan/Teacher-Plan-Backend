package com.github.ioloolo.teacher_plan.domain.timetable;

import com.github.ioloolo.comcigan.ComciganBaseApi;
import com.github.ioloolo.comcigan.teacher.ComciganTeacherApi;
import com.github.ioloolo.teacher_plan.domain.timetable.context.GetTeacherListRequest;
import com.github.ioloolo.teacher_plan.domain.timetable.context.GetTimeTableRequest;
import com.github.ioloolo.teacher_plan.domain.timetable.context.SearchSchoolRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public final class TimeTableController {

    @PostMapping("/school")
    public ResponseEntity<?> searchSchool(@RequestBody SearchSchoolRequest request) {
        return ResponseEntity.ok(ComciganBaseApi.searchSchool(request.getQuery()));
    }

    @PostMapping("/teachers")
    public ResponseEntity<?> getTeachers(@RequestBody GetTeacherListRequest request) {
        return ResponseEntity.ok(ComciganTeacherApi.getTeacherList(request.getSchool()));
    }

    @PostMapping("/timetable")
    public ResponseEntity<?> getTimetable(@RequestBody GetTimeTableRequest request) {
        return ResponseEntity.ok(ComciganTeacherApi.getWeeklyTimeTable(request.getSchool(), request.getTeacher()));
    }
}
