package com.github.ioloolo.teacher_plan.domain.timetable;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.comcigan.ComciganBaseApi;
import com.github.ioloolo.comcigan.data.School;
import com.github.ioloolo.comcigan.teacher.ComciganTeacherApi;
import com.github.ioloolo.comcigan.teacher.data.Teacher;
import com.github.ioloolo.comcigan.teacher.timetable.DailyTimeTable;
import com.github.ioloolo.teacher_plan.domain.timetable.context.GetTeacherListRequest;
import com.github.ioloolo.teacher_plan.domain.timetable.context.GetTimeTableRequest;
import com.github.ioloolo.teacher_plan.domain.timetable.context.SearchSchoolRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    @PostMapping("/school")
    public ResponseEntity<?> searchSchool(@RequestBody SearchSchoolRequest request) {
        String query = request.getQuery();

        List<School> body = ComciganBaseApi.searchSchool(query);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/teachers")
    public ResponseEntity<?> getTeachers(@RequestBody GetTeacherListRequest request) {
        int school = request.getSchool();

        List<Teacher> teacherList = ComciganTeacherApi.getTeacherList(school);

        return ResponseEntity.ok(teacherList);
    }

    @PostMapping("/timetable")
    public ResponseEntity<?> getTimetable(@RequestBody GetTimeTableRequest request) {
        int school = request.getSchool();
        int teacher = request.getTeacher();

        List<DailyTimeTable> weeklyTimeTable = ComciganTeacherApi.getWeeklyTimeTable(school, teacher);

        log.info("[TimeTable] Request. ({} - {})", school, teacher);

        return ResponseEntity.ok(weeklyTimeTable);
    }
}
