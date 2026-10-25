# Uiduk Internship Backend — Cấu trúc code, triển khai & quy ước đặt tên

> Tài liệu suy ra từ codebase hiện tại (`uiduk.internship.backend`).  
> Base package: `com.winwintek.web.backend.api`

---

## Mục lục

1. [Tổng quan](#1-tổng-quan)
2. [Tech stack](#2-tech-stack)
3. [Cấu trúc thư mục / package](#3-cấu-trúc-thư-mục--package)
4. [Kiến trúc & luồng xử lý](#4-kiến-trúc--luồng-xử-lý)
5. [Tổ chức module theo audience](#5-tổ-chức-module-theo-audience)
6. [Ví dụ feature đầy đủ](#6-ví-dụ-feature-đầy-đủ)
7. [Quy ước API path](#7-quy-ước-api-path)
8. [Quy cách đặt tên](#8-quy-cách-đặt-tên)
9. [Triển khai / vận hành](#9-triển-khai--vận-hành)
10. [Cross-cutting](#10-cross-cutting)
11. [Checklist thêm feature mới](#11-checklist-thêm-feature-mới)

---

## 1. Tổng quan

Backend Spring Boot cho hệ thống quản lý thực tập Uiduk.

- Kiến trúc: **Layered + feature-sliced** (Controller → Service → Repository)
- Chia theo **audience/portal**: `admin` / `client` / `system` / `common`
- API REST JSON, response bọc trong `ApiResponse<T>`
- Auth: session (Redis) + Spring Security + phân quyền URL–role từ DB
- Packaging: **WAR** deploy servlet container

**Không phải** Clean Architecture / DDD đầy đủ: package `domain` ở đây nghĩa là “nhóm feature nghiệp vụ”, không phải domain layer theo DDD.

---

## 2. Tech stack

| Thành phần | Chi tiết |
|------------|----------|
| Language | Java **21** |
| Framework | Spring Boot **3.3.0** |
| Build | Gradle **8.8** (wrapper), plugin `war` |
| Web | `spring-boot-starter-web` |
| ORM | Spring Data JPA + Hibernate (`ddl-auto=none`) |
| DB | Oracle + HikariCP |
| Session | Spring Session + **Redis** |
| Security | Spring Security (REST login filter, dynamic URL–role) |
| Mapping | ModelMapper |
| Validation | `spring-boot-starter-validation` |
| AOP | Spring AOP (`LogAspect`, `CommonAspect`) |
| Mail | Spring Mail |
| ID | TSID (`tsid-creator`, `hypersistence-tsid`) |
| SSO | Local JAR `lib/SIServerAPI.4.3.jar` |
| Lombok | Có |
| Tests | Khai báo trong Gradle; chưa có cây `src/test` thực tế |

**Entrypoint**

```java
@SpringBootApplication
@EnableWebSecurity
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

`extends SpringBootServletInitializer` → chạy embedded hoặc deploy WAR lên Tomcat ngoài.

---

## 3. Cấu trúc thư mục / package

### 3.1. Top-level

```
uiduk.internship.backend/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── gradle/wrapper/
├── lib/
│   └── SIServerAPI.4.3.jar          # Uiduk SSO
├── README.md
├── docs/
│   └── CAU_TRUC_VA_QUY_UOC.md       # File này
└── src/main/
    ├── resources/
    │   ├── application.properties
    │   └── banner.txt
    └── java/com/winwintek/web/backend/api/
        ├── Application.java
        ├── common/
        ├── config/
        ├── domain/
        ├── dto/
        ├── entity/
        ├── repository/
        ├── system/
        ├── util/
        └── lib/                     # ojdbc local (nếu dùng)
```

### 3.2. Vai trò từng package gốc

| Package | Vai trò |
|---------|---------|
| `common/` | Contract API toàn cục: `ApiResponse`, `ApiStatus`, `Constants`, enum |
| `config/` | Hạ tầng: security, redis, jpa, file, aop, modelMapper, webmvc |
| `domain/` | Feature modules: `web` (Controller) + `service` + `impl` |
| `dto/` | Request / Response DTO (mirror theo audience/feature) |
| `entity/` | JPA entity + interface projection query |
| `repository/` | Spring Data JPA repository |
| `system/` | Interceptor, Aspect, XSS escape (runtime cross-cutting) |
| `util/` | Tiện ích dùng chung |

### 3.3. `common/` — contract API

| Class | Vai trò |
|-------|---------|
| `ApiResponse<T>` | Response chuẩn `{ code, message, data }` |
| `ApiStatus` | Enum mã nghiệp vụ (`200001`, `400001`, `401002`…) |
| `Constants` | Flag `Y`/`N`, role id, mã trạng thái CD |
| `ApprovalStatus`, `GlobalEnum` | Enum nghiệp vụ |

**Dạng response:**

```json
{
  "code": "200001",
  "message": "Success",
  "data": { }
}
```

**Một số mã `ApiStatus`:**

| Enum | HTTP | Code | Message |
|------|------|------|---------|
| `SUCCESS` | 200 | `200001` | Success |
| `CREATED` | 201 | `201001` | Created |
| `BAD_REQUEST_VALID` | 400 | `400001` | Bad request |
| `BAD_REQUEST_BUSINESS` | 400 | `400002` | Business |
| `USER_NOT_FOUND` | 401 | `401001` | Unauthorized Error |
| `INVALID_CREDENTIALS` | 401 | `401002` | Incorrect username or password |
| `FORBIDDEN` | 403 | `403001` | Access denied |
| `NOT_FOUND` | 404 | `404001` | Not found |
| `CONFLICT` | 409 | `409001` | Conflict |
| `INTERNAL_SERVER_ERROR` | 500 | `500001` | Internal server error |

### 3.4. `config/` — hạ tầng

```
config/
├── security/
│   ├── app/          SecurityConfig, SecurityController (/auth), SecurityAppConfig
│   ├── filter/       RestAuthenticationFilter  → POST /auth/login
│   ├── provider/     RestAuthenticationProvider
│   ├── handler/      Success/Failure/AccessDenied, GlobalExceptionHandler, CustomException
│   ├── manager/      CustomDynamicAuthorizationManager
│   ├── mapper/       PersistentUrlRoleMapper, MapBasedUrlRoleMapper
│   ├── service/      FormUserDetailsService, DynamicAuthorizationService, LoginEventService…
│   ├── dto/          LoginDTO, AccountContext
│   ├── entity/       Authority
│   ├── token/        RestAuthenticationToken
│   ├── entrypoint/   RestAuthenticationEntryPoint
│   └── util/         SecurityUtils, WebUtil, SequenceGenerator
├── redis/app/        RedisConfig
├── jpa/              JpaAuditingConfiguration, SpringSecurityAuditorAware, CustomAuditingEntityListener
├── file/             FileMngController (/cmm/files), FileMngService (+ Impl)
├── aop/              LogAspect
├── modelMapper/      ModelMapperConfig
└── webmvc/           WebMvcConfig
```

### 3.5. Cấu trúc 1 feature (bắt buộc mirror)

Mỗi feature gần như luôn có:

```
domain/{audience}/{featureName}/
├── web/       *Controller.java
├── service/   I*Service.java  (hoặc *Service.java)
└── impl/      *ServiceImpl.java

dto/{audience}/{featureName}/
entity/{audience}/{featureName}/
repository/{audience}/{featureName}/
```

---

## 4. Kiến trúc & luồng xử lý

### 4.1. Layer

| Layer | Package | Annotation / kiểu |
|-------|---------|-------------------|
| Presentation | `domain.**.web` | `@RestController` |
| Business | `domain.**.service` + `impl` | `@Service` |
| Persistence | `repository.**` | `JpaRepository` |
| Model | `entity.**` | `@Entity` |
| Transfer | `dto.**` | POJO + Lombok |
| Cross-cutting | `config.**`, `system.**`, `common.**` | Security, AOP, wrapper |

### 4.2. Luồng request

```
Frontend
  → SecurityFilterChain (/api/v1/...)
  → Session Redis + DynamicAuthorization (URL ↔ role)
  → *Controller
  → *ServiceImpl
  → *Repository (JPA / native SQL)
  → Oracle
  ← ApiResponse { code, message, data }
```

### 4.3. Auth

1. Login: `POST /api/v1/auth/login` — `RestAuthenticationFilter` đọc `UserDTO` JSON.
2. Session lưu **Redis** (không JWT-first).
3. Request tiếp theo: `CustomDynamicAuthorizationManager` map URL ↔ role từ `sys_resource` / `sys_resource_role`.
4. Cookie SSO liên quan: `SSOTOKEN` (Uiduk SSO JAR).

### 4.4. Auditing entity

Mọi entity kế thừa `AbstractAuditingEntity`:

| Field Java | Cột DB |
|------------|--------|
| `regId` | `reg_id` |
| `regDate` | `reg_date` |
| `modifyId` | `modify_Id` |
| `modifyDate` | `modify_date` |

Soft delete phổ biến: `delYn` = `"Y"` / `"N"` (`Constants.STATE_Y` / `STATE_N`).

---

## 5. Tổ chức module theo audience

### 5.1. `domain/admin/`

Admin / Support Center / Corporate admin portal.

```
admin/
├── attendanceTrackingCenter/
├── category/
├── corporate/
│   ├── evaluationTable/
│   ├── internAttendanceTracking/
│   ├── internCorporateSurvey/
│   ├── operateInternMng/
│   └── signed/
├── corporateApproval/
├── corporateChange/
├── corporatePreTest/
├── internPlan/
├── internPlanCenter/
├── notice/
├── preTest/
├── qna/
└── supportCenter/
    ├── agreement/
    ├── completeManage/
    ├── completionMng/
    ├── corpSurvey/
    ├── docRoom/
    ├── evaluationReportCenter/
    ├── evaluationTableCenter/
    ├── guidanceReportCenter/
    ├── indexInternPlan/
    ├── stdSurvey/
    ├── summaryReport/
    └── weekReport/
```

### 5.2. `domain/client/`

Portal người dùng cuối.

```
client/
├── student/
│   ├── agreeTerm/
│   ├── dashboard/          # notice, qna, docRoom
│   ├── docRoom/
│   ├── internshipRegistration/
│   ├── internStudent/
│   ├── internStdSurvey/
│   ├── qna/
│   ├── reportWeek/
│   ├── schoolNotice/
│   ├── signedAgreement/
│   ├── stdSurveyAns/
│   └── summaryReportStudent/
├── professor/
│   ├── attendanceTrackingLecturer/
│   ├── evaluationReport/
│   ├── guidanceReport/
│   └── internPlanLecturer/
├── corporateRegister/
├── internMng/
└── participationCertificate/
```

### 5.3. `domain/system/`

RBAC / menu / lịch sử đăng nhập.

```
system/
├── loginHistory/
├── menu/
├── menuHistory/
├── roles/
├── sysResource/
├── sysResourceRole/
└── sysRole/
```

### 5.4. `domain/common/`

API dùng chung: nav, code danh mục, danh sách sinh viên thực tập.

---

## 6. Ví dụ feature đầy đủ

### 6.1. Admin — `corporateChange`

```
domain/admin/corporateChange/
  web/CorporateChangeController.java
  service/ICorporateChangeService.java
  impl/CorporateChangeServiceImpl.java

dto/admin/corporateChange/
  CorporateInfoChangeRequest.java
  CorporateInfoChangeResponse.java
  CorporateChangeRequest.java
  CorporateChangeResponse.java

entity/admin/corporateChange/
  CorporateChange.java          → CORPORATE_MEMBERS_MANAGER
  CorporateInfoChange.java
  CorporateUserChange.java

repository/admin/corporateChange/
  CorporateChangeRepository.java
  CorporateInfoChangeRepository.java
  CorporateUserChangeRepository.java
```

| Mục | Giá trị |
|-----|---------|
| Base path | `/a/c/corporate-change` |
| Detail | `POST /api/v1/a/c/corporate-change` |
| Update | `PUT /api/v1/a/c/corporate-change/update` |
| Return | `ApiResponse<...>` với `ApiStatus.SUCCESS` |
| Mapping | `ModelMapper` entity ↔ DTO |
| Lỗi | `CustomException(ApiStatus.xxx)` |

### 6.2. Client — `internStudent`

| Layer | Tên |
|-------|-----|
| Path package | `domain/client/student/internStudent/` |
| Controller | `InternStudentController` → `/c/intern-student` |
| Service | `IInternStudentService` / `InternStudentServiceImpl` |
| Entity | `InternStudentEntity` → bảng `INTERN_STD` |
| DTO | `InternStudentReqDTO`, `InternStudentListResDTO`, `InternStudentRegResDTO` |
| Actions | `/create`, `/reg-list`, `/update`, `/internship-available-year` (POST) |

Field entity tiêu biểu: `internStdSeq`, `stdNo`, `internPlanSeq`, `approveStatus`, `internshipStatus`, `delYn`.

---

## 7. Quy ước API path

**Context path toàn cục:** `/api/v1`  
(`server.servlet.context-path=/api/v1`)

### 7.1. Prefix theo đối tượng

| Prefix | Ý nghĩa | Ví dụ |
|--------|---------|--------|
| `/a/...` | Admin / Support Center | `/a/notice`, `/a/qna`, `/a/support-center/week-report` |
| `/a/c/...` | Admin phía Corporate | `/a/c/corporate-change`, `/a/c/operate-intern` |
| `/c/...` | Client (SV / chung) | `/c/intern-student`, `/c/internship-plan` |
| `/c/prof/...` | Giảng viên | `/c/prof/intern-plan`, `/c/prof/eval-report` |
| `/s/...` | Survey | `/s/std-survey-ans`, `/s/intern-std-survey` |
| `/p/...` | Portal quản lý thực tập | `/p/intern-mng` |
| `/cmm/...` | Common | `/cmm/navitems`, `/cmm/files` |
| `/auth/...` | Auth | `/auth/login`, `/auth/logout` |
| `/code/...` | Mã danh mục | `/code/listCode` |
| `/admin/...` | Ít dùng (full word) | `/admin/category` |

### 7.2. Action path

Thường dùng: `/list`, `/detail`, `/create`, `/update`, `/`.

- Chủ yếu `@PostMapping` **kể cả thao tác đọc**.
- Có chỗ dùng `@GetMapping` / `@PutMapping` (auth, một số update).

### 7.3. Style path

- Segment: **kebab-case** — `corporate-change`, `week-report`, `signed-agreement`.
- Full ví dụ: `POST /api/v1/a/c/corporate-change`.

### 7.4. Danh sách `@RequestMapping` chính (tham khảo)

| Controller area | Mapping |
|-----------------|---------|
| Auth | `/auth` |
| Files | `/cmm/files` |
| Common nav | `/cmm` |
| Code | `/code` |
| Menu / Role / Resource | `/a/menu`, `/a/role`, `/a/roles`, `/a/resources`, `/a/resources-roles` |
| Notice / QnA admin | `/a/notice`, `/a/qna` |
| Support center | `/a/support-center/week-report`, `.../summary-report`, `.../std-survey`, `.../corp-survey` |
| Corporate admin | `/a/c/corporate-change`, `/a/c/intern-plan`, `/a/c/operate-intern`… |
| Student | `/c/intern-student`, `/c/internship-plan`, `/c/agree-term`, `/c/signed-agreement`… |
| Professor | `/c/prof/intern-plan`, `/c/prof/eval-report`, `/c/prof/guid-report`… |
| Survey | `/s/std-survey-ans`, `/s/intern-std-survey` |
| Portal | `/p/intern-mng` |

---

## 8. Quy cách đặt tên

### 8.1. Folder / package

| Quy tắc | Ví dụ đúng | Tránh |
|---------|------------|--------|
| Audience: lowercase | `admin`, `client`, `system` | — |
| Feature: **camelCase** | `corporateChange`, `reportWeek`, `supportCenter` | `Corporate_Change`, `CorporateChange` (PascalCase folder) |
| Layer cố định | `web`, `service`, `impl` | tên khác |

### 8.2. Class / file (PascalCase + hậu tố)

| Loại | Pattern | Ví dụ |
|------|---------|--------|
| Controller | `{Feature}Controller` | `WeekReportSupportCenterController` |
| Service interface | `I{Feature}Service` hoặc `{Feature}Service` | `ICorporateChangeService`, `MenuService` |
| Service impl | `{Feature}ServiceImpl` | `CorporateChangeServiceImpl` |
| Entity | `{Feature}Entity` hoặc tên rút gọn | `InternStudentEntity`, `CorporateChange` |
| Repository | `{Feature}Repository` (± `I`) | `CorporateChangeRepository`, `IUserRepository` |
| Request DTO | `*Request`, `*ReqDTO`, `*RequestDTO` | `CorporateInfoChangeRequest`, `InternStudentReqDTO` |
| Response DTO | `*Response`, `*ResDTO`, `*ResponseDTO` | `CorporateInfoChangeResponse` |
| Search DTO | `*SearchDTO` | `QnaSearchDTO`, `RoleSearchDTO` |
| Projection | `I*DTO`, `I*Response` | `IQnaCusDTO`, `IStdInternAdminResponse` |

> Project **chưa thống nhất 100%** (`DTO` vs `Dto`, có/không prefix `I`). Khi viết mới: **bám convention của feature lân cận cùng audience**.

### 8.3. Biến / method / field

| Quy tắc | Ví dụ |
|---------|--------|
| Field: camelCase | `internStdSeq`, `approveStatus`, `cormNameKr` |
| List: tiền tố `lst` | `lstCorporateChange` |
| Repo field: rút `Repo` | `corporateChangeRepo` |
| Method: verb + noun | `detailCorporate()`, `getInternStudentList()`, `getLstCorporate()` |
| Flag DB dạng Y/N | `delYn`, `useYn` |
| Constant: SCREAMING_SNAKE | `STATE_Y`, `ROLE_PROFESSOR`, `CD_STD_APPROVAL` |

### 8.4. Từ điển viết tắt

| Viết tắt | Nghĩa | Ví dụ |
|----------|-------|-------|
| `std` | student | `stdNo`, `InternStd`, `USER_DIV_STD` |
| `corm` | corporate member | `cormSeq`, `cormNameKr`, `cormUserId` |
| `comm` | corporate members manager | `commSeq`, `commNm` |
| `Mng` | manage | `FileMng`, `CodeMng`, `completionMng` |
| `His` | history | `LoginHisController` |
| `cmm` / `CMMN` | common | `/cmm/files`, `CMMN_CODE_MNG` |
| `prof` | professor / lecturer | `/c/prof/...` |
| `corp` | corporate | `corpSurvey` |
| `seq` | sequence / PK | `internStdSeq`, `internPlanSeq` |
| `Cd` | code | `cormClassificationCd` |
| `Yn` | Yes/No flag | `delYn`, `useYn` |
| `Reg` | register | `InternStudentRegResDTO` |
| `Nm` | name | `commNm`, `cormAgentNmKr` |

### 8.5. Entity ↔ bảng DB

| Entity | `@Table` |
|--------|----------|
| `InternStudentEntity` | `INTERN_STD` |
| `CorporateChange` | `CORPORATE_MEMBERS_MANAGER` |
| `FileMngEntity` | `FILE_MNG` |
| `CodeMngEntity` | `CMMN_CODE_MNG` |
| `SysRolesEntity` | `sys_role` |

- Tên bảng: thường **SCREAMING_SNAKE** (Oracle).
- Không có Flyway/Liquibase — schema quản lý **ngoài** ứng dụng (`ddl-auto=none`).
- Query native Oracle phổ biến (`FN_GET_CODE_NAME`, `EXTRACT(YEAR FROM …)`).

### 8.6. Mã trạng thái nghiệp vụ (`Constants`)

| Nhóm | Ví dụ mã |
|------|----------|
| Intern plan | `INTERN_PLAN_01` (chờ), `INTERN_PLAN_02` (duyệt), `INTERN_PLAN_07` (từ chối) |
| Duyệt SV | `STD_A01_P01` … `STD_A01_P08` |
| Trạng thái thực tập | `STD_I01_P01` … `STD_I01_P04` |
| Corporate | `CORM_01`… |
| Flag | `STATE_Y` = `"Y"`, `STATE_N` = `"N"` |

**User division:**

| Constant | Giá trị | Ý nghĩa |
|----------|---------|---------|
| `USER_DIV_STD` | `std` | Sinh viên |
| `USER_DIV_STAFF` | `stf` | Staff |
| `USER_DIV_ADM` | `ADM` | Admin |
| `USER_DIV_OUT` | `OUT` | Outside |
| Check token | `s` / `p` / `a` / `o` | std / prof / adm / out |

**Role id (ví dụ):** `ROLE_PROFESSOR = 4L`, `ROLE_BUSINESS = 2L`, `ROLE_CORPORATE_MEMBERS = 4L`.

---

## 9. Triển khai / vận hành

### 9.1. Chạy local

1. Cài **JDK 21** và Gradle (hoặc dùng `./gradlew` / `gradlew.bat`).
2. Oracle reachable theo cấu hình trong `application.properties`.
3. Redis chạy tại host/port cấu hình (mặc định `127.0.0.1:6379`).
4. Chạy `com.winwintek.web.backend.api.Application` từ IDE  
   hoặc build WAR: `./gradlew bootWar`.

### 9.2. Build artifact

```gradle
bootWar {
    archiveBaseName = 'ROOT'
    archiveFileName = 'INTERNSHIP_ROOT.war'
    archiveVersion = '0.0.0'
}
```

Output: `INTERNSHIP_ROOT.war` (deploy như ROOT trên servlet container).

### 9.3. Deploy production (theo thiết kế WAR)

```
gradlew bootWar
  → INTERNSHIP_ROOT.war
  → Deploy Tomcat (hoặc servlet container tương đương)
  → Context path ứng dụng: /api/v1
  → Session: Redis
  → Upload: upload.path (ví dụ /tmp/upload)
```

### 9.4. Cấu hình quan trọng (`application.properties`)

| Key | Ý nghĩa |
|-----|---------|
| `server.servlet.context-path` | `/api/v1` |
| `spring.profiles.active` | `prod` (hiện tại) |
| `spring.datasource.*` | Oracle + HikariCP |
| `spring.jpa.hibernate.ddl-auto` | `none` |
| `spring.data.redis.host/port` | Redis session |
| `spring.servlet.multipart.*` | Max upload (ví dụ 500MB) |
| `upload.path` / `download.path` | Thư mục file |

> **Lưu ý bảo mật:** Không commit mật khẩu DB/mail thật vào tài liệu công khai. Giữ secret trong env / secret manager khi triển khai thực tế.

### 9.5. Frontend / CORS (tham chiếu trong `SecurityConfig`)

- `http://localhost:3000`
- `http://uu-intern-client.winwintek.net`
- `http://uu-intern-admin.winwintek.net`
- (và một số host khác trong code)

### 9.6. Những gì chưa có trong repo

| Mục | Trạng thái |
|-----|------------|
| Docker / docker-compose | Không có |
| CI/CD (GitHub Actions, Jenkins…) | Không có |
| Flyway / Liquibase | Không có |
| `src/test` thực tế | Không có |
| `.env.example` | Không có |
| Coding standard chính thức trong README | Không có (README chỉ hướng dẫn chạy local) |

---

## 10. Cross-cutting

| Thành phần | Vai trò |
|------------|---------|
| `system/CommonInterceptor` | Interceptor HTTP |
| `system/CommonAspect` | AOP chung |
| `config/aop/LogAspect` | Logging |
| `system/HTMLCharacterEscapes` | XSS escape JSON |
| `GlobalExceptionHandler` + `CustomException` | Bắt lỗi → trả `ApiStatus` |
| `FileMng*` | Upload/download qua `/cmm/files` |
| JPA Auditing | Tự set `regId` / `modifyId` từ user đăng nhập |

---

## 11. Checklist thêm feature mới

1. **Chọn audience:** `admin` | `client/student` | `client/professor` | `system` | `common`.
2. **Tạo folder camelCase**, ví dụ:  
   `domain/admin/myFeature/{web,service,impl}`.
3. **Mirror packages:**  
   `dto/admin/myFeature/`, `entity/admin/myFeature/`, `repository/admin/myFeature/`.
4. **Đặt tên class:**
   - `MyFeatureController`
   - `IMyFeatureService` + `MyFeatureServiceImpl`
   - `MyFeatureEntity` (extends `AbstractAuditingEntity`)
   - `MyFeatureRepository extends JpaRepository<…>`
   - `MyFeatureReqDTO` / `MyFeatureResDTO`
5. **Route:**  
   `@RequestMapping("/a/my-feature")` + `@PostMapping("/list" | "/detail" | "/create" | "/update")`.
6. **Return:**  
   `return new ApiResponse<>(ApiStatus.SUCCESS, data);`
7. **Soft delete:** luôn filter `delYn = Constants.STATE_N` khi query đang active.
8. **Map DTO:** dùng `ModelMapper` như các `*ServiceImpl` hiện có.
9. **Lỗi nghiệp vụ:** `throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);` (hoặc mã phù hợp).

---

## Tóm tắt một dòng

> **Layered Spring Boot, cắt theo portal (admin/client/system); mỗi feature = Controller + Service(+Impl) + DTO + Entity + Repository; API prefix `/a|/c|/s|/p|/cmm` dưới `/api/v1`; session Redis; deploy WAR.**

---

*Cập nhật: dựa trên khảo sát codebase `uiduk.internship.backend`.*
