package com.github.ioloolo.schoolhelper_teacher.domain.timetable;

import com.github.ioloolo.comcigan.ComciganBaseApi;
import com.github.ioloolo.comcigan.ComciganTeacherApi;
import com.github.ioloolo.schoolhelper_teacher.domain.timetable.context.GetRangeRequest;
import com.github.ioloolo.schoolhelper_teacher.domain.timetable.context.GetTeacherListRequest;
import com.github.ioloolo.schoolhelper_teacher.domain.timetable.context.GetTimeTableRequest;
import com.github.ioloolo.schoolhelper_teacher.domain.timetable.context.SearchSchoolRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public final class ComciganController {

    @PostMapping("/school")
    public ResponseEntity<?> searchSchool(@RequestBody SearchSchoolRequest request) throws IOException {
        return ResponseEntity.ok(ComciganBaseApi.searchSchool(request.getQuery()));
    }

    @PostMapping("/teachers")
    public ResponseEntity<?> getTeachers(@RequestBody GetTeacherListRequest request) throws Exception {
        return ResponseEntity.ok(ComciganTeacherApi.getTeacherList(request.getSchool()));
    }

    @PostMapping("/timetable")
    public ResponseEntity<?> getTimetable(@RequestBody GetTimeTableRequest request) throws Exception {
        return ResponseEntity.ok(ComciganTeacherApi.getTimeTable(request.getSchool(), request.getTeacher()));
    }

    @PostMapping("/range")
    public ResponseEntity<?> getRange(@RequestBody GetRangeRequest request) throws Exception {
        return ResponseEntity.ok(
                ComciganTeacherApi.getRange(request.getSchool())
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            Map<Object, Object> convertedMap = new HashMap<>();
                            convertedMap.put("period", entry.getKey());
                            convertedMap.put("hour", entry.getValue().getHour());
                            convertedMap.put("minute", entry.getValue().getMinute());
                            return convertedMap;
                        })
                        .toList()
        );
    }
}
