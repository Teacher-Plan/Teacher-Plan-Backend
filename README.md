# [Backend] School Helper For Teacher
2023학년도 제천고등학교 코딩 동아리인 코딩연구소에서 제작한 [School Helper](https://github.com/School-Helper-ioloolo/School-Helper-Backend)의 교사 버전 백엔드 서버

## 기술 스택
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

## 엔드포인트

### 학교 검색
컴시간 시간표에 등록된 학교를 검색합니다.

![api](https://img.shields.io/badge/%2Fapi%2Ftimetable%2Fschool-green?style=for-the-badge&label=POST)
```json
{
  "query": (검색어)
}
```

### 선생님 목록
해당 학교에 있는 교사 ID와 이름을 조회합니다.

![api](https://img.shields.io/badge/%2Fapi%2Ftimetable%2Fteachers-green?style=for-the-badge&label=POST)
```json
{
  "school": (학교 코드)
}
```

### 시간표
해당 학교에 있는 교사의 시간표를 조회합니다.

![api](https://img.shields.io/badge/%2Fapi%2Ftimetable%2Ftimetable-green?style=for-the-badge&label=POST)
```json
{
  "school": (학교 코드)
  "teacher": (교사 ID)
}
```

### 일과시간
해당 학교의 일과 시간을 조회합니다.

![api](https://img.shields.io/badge/%2Fapi%2Ftimetable%2Franges-green?style=for-the-badge&label=POST)
```json
{
  "school": (학교 코드)
}
```

### 급식
해당 학교의 급식을 조회합니다.

![api](https://img.shields.io/badge/%2Fapi%2Fmeal-green?style=for-the-badge&label=POST)
```json
{
  "name": (학교 이름)
  "location": (학교 도시)
}
```
