# Teacher Plan
2023학년도 제천고등학교 코딩 동아리인 코딩연구소에서 제작한 [School Helper](https://github.com/School-Helper-ioloolo/School-Helper-Backend)의 교사 버전 백엔드 API

## 엔드포인트

### 학교 검색
컴시간 시간표에 등록된 학교를 검색합니다.
- /api/timetable/school
- POST
- ```json
  {
    "query": (검색어)
  }
  ```

### 선생님 목록
해당 학교에 있는 교사 ID와 이름을 조회합니다.
- /api/timetable/teachers
- POST
- ```json
  {
    "school": (학교 코드)
  }
  ```

### 시간표
해당 학교에 있는 교사의 시간표를 조회합니다.
- /api/timetable/timetable
- POST
- ```json
  {
    "school": (학교 코드),
    "teacher": (교사 ID)
  }
  ```

### 일과시간
해당 학교의 일과 시간을 조회합니다.
- /api/timetable/ranges
- POST
- ```json
  {
    "school": (학교 코드)
  }
  ```

### 급식
해당 학교의 급식을 조회합니다.
- /api/meal
- POST
- ```json
  {
    "name": (학교 이름),
    "location": (학교 도시)
  }
  ```