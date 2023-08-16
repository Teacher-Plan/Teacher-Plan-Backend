package com.github.ioloolo.teacher_plan.domain.meal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.teacher_plan.domain.meal.context.GetMealRequest;
import com.github.ioloolo.teacher_plan.domain.meal.data.Meal;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public final class MealController {

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final Gson gson = new Gson();

    @Value("${app.neis-key}")
    private String NEIS_API_KEY;

    @PostMapping
    public ResponseEntity<?> getMeal(@RequestBody GetMealRequest request) throws IOException {
        okhttp3.RequestBody body = okhttp3.RequestBody.create("null", MediaType.parse("text/plain"));

        String aptp = switch (request.getLocation()) {
            case "서울" -> "B10";
            case "부산" -> "C10";
            case "대구" -> "D10";
            case "인천" -> "E10";
            case "광주" -> "F10";
            case "대전" -> "G10";
            case "울산" -> "H10";
            case "세종" -> "I10";
            case "경기" -> "J10";
            case "강원" -> "K10";
            case "충북" -> "M10";
            case "충남" -> "N10";
            case "전북" -> "P10";
            case "전남" -> "Q10";
            case "경북" -> "R10";
            case "경남" -> "S10";
            case "제주" -> "T10";
            default -> "";
        };

        Request schoolRequest = new Request.Builder()
                .url("https://open.neis.go.kr/hub/schoolInfo?KEY=%s&Type=json&SCHUL_NM=%s&ATPT_OFCDC_SC_CODE=%s"
                        .formatted(
                                NEIS_API_KEY,
                                request.getName(),
                                aptp
                        ))
                .method("POST", body)
                .build();

        Response schoolResponse = client.newCall(schoolRequest).execute();
        JsonObject schoolJson = gson.fromJson(Objects.requireNonNull(schoolResponse.body()).string(), JsonObject.class);

        if (!schoolJson.has("schoolInfo")) {
            return ResponseEntity.ok("[]");
        }

        String schoolCode = schoolJson.getAsJsonArray("schoolInfo")
                .get(1).getAsJsonObject()
                .get("row").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("SD_SCHUL_CODE").getAsString();

        LocalDate today = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate();

        Request mealRequest = new Request.Builder()
                .url("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=%s&Type=json&ATPT_OFCDC_SC_CODE=%s&SD_SCHUL_CODE=%s&MLSV_YMD=%s"
                        .formatted(
                                NEIS_API_KEY,
                                aptp,
                                schoolCode,
                                today.toString().replace("-", "")
                        ))
                .method("POST", body)
                .build();

        Response mealResponse = client.newCall(mealRequest).execute();
        JsonObject mealJson = gson.fromJson(Objects.requireNonNull(mealResponse.body()).string(), JsonObject.class);

        if (!mealJson.has("mealServiceDietInfo")) {
            return ResponseEntity.ok("[]");
        }

        return ResponseEntity.ok(
                mealJson.getAsJsonArray("mealServiceDietInfo")
                        .get(1).getAsJsonObject()
                        .get("row").getAsJsonArray()
                        .asList()
                        .stream()
                        .map(JsonElement::getAsJsonObject)
                        .map(jsonObject -> {
                            String type = jsonObject.get("MMEAL_SC_NM").getAsString();
                            String menuStr = jsonObject.get("DDISH_NM").getAsString();

                            List<String> resultList = new ArrayList<>();

                            String[] items = menuStr.split("<br/>");

                            for (String item : items) {
                                String patternString = "\\((.*?)\\)";
                                Pattern pattern = Pattern.compile(patternString);
                                Matcher matcher = pattern.matcher(item);
                                String extractedText = matcher.replaceAll("").trim();
                                resultList.add(extractedText);
                            }

                            return new Meal(type, resultList);
                        }));
    }
}
