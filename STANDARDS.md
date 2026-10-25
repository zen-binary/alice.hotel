# Alice Hotel — Tiêu chuẩn triển khai cố định

Áp dụng cho cả monorepo: `alice.hotel.backend`, `alice.hotel.fe.admin`, `alice.hotel.fe.client`.

## 1. Workflow bắt buộc

```
Feature → Đọc STANDARDS / Cursor rules → Branch → Code đúng chuẩn → Checklist PR → Merge → Release checklist
```

1. Trước khi code: đọc tài liệu này và rule Cursor tương ứng (backend / FE).
2. Trong khi code: giữ cấu trúc layer, API envelope, store shape; không invent pattern mới.
3. Trước PR: lint/format FE, build backend, hoàn thành Definition of Done.
4. Trước release: chạy Release checklist bên dưới.

---

## 2. Coding — Backend (`alice.hotel.backend`)

### Stack

- Java **17** (`sourceCompatibility` trong `build.gradle`)
- Spring Boot 3.x, Gradle, package WAR: `bootWar` → `ALICE_HOTEL_ROOT.war`
- Context path: `/api/v1`

### Cấu trúc package

Base: `com.alice.hotel.api`

| Layer | Path |
|--------|------|
| Controller / service | `domain/{admin\|system\|common}/{feature}/web`, `service`, `impl` |
| DTO | `dto/{admin\|system\|common}/{feature}/` — `*DTO`, `*SearchDTO`, `*RequestDTO`, `*ResponseDTO` |
| Entity / Repo | `entity/`, `repository/` — `*Entity`, `*Repository` |
| Cross-cutting | `common/` (`ApiResponse`, `ApiStatus`), `config/`, `system/` |

Template feature mới: theo `domain/admin/hotelRoom/` (controller + service + impl + DTO + repository).

### API

- Mọi JSON response dùng envelope `{ code, message, data }` qua `ApiResponse` + `ApiStatus`.
- Không trả JPA entity thô ra controller.
- Lỗi nghiệp vụ: `CustomException` + `ApiStatus`; để `GlobalExceptionHandler` map về `ApiResponse`.
- Admin feature mới: prefix `/a/...`, **REST** theo `HotelRoomController`:
  - `POST /a/{resource}/list`
  - `GET /a/{resource}/{id}`
  - `POST /a/{resource}`
  - `PUT /a/{resource}/{id}`
  - `DELETE /a/{resource}/{id}`
- Không mở rộng pattern POST-heavy cũ cho endpoint mới.
- Auth: session cookie; public `/auth/**`, `/files/**`.

### Naming

- Class: PascalCase (`HotelRoomController`, `HotelRoomServiceImpl`)
- URL path: kebab-case (`/a/hotel-rooms`)
- Package feature: camelCase (`hotelRoom`)

---

## 3. Coding — Frontend (`alice.hotel.fe.admin` / `alice.hotel.fe.client`)

### Stack

- Vue 3 + Vite + TypeScript + Pinia + Vuetify 3 + vue-i18n
- HTTP: Axios trong `src/utils/http.ts` — `baseURL = VITE_API_ENDPOINT`, `withCredentials: true`
- Payload nghiệp vụ: `response.data.data`

### Cấu trúc feature

```
src/stores/{feature}/
  index.ts              # Pinia store: defineStore("{feature}Store", () => { ... })
  {feature}.service.ts  # URL constants (vd. HOTEL_ROOM_LIST = "/a/hotel-rooms/list")
  {feature}.type.ts     # types
```

Views: `src/views/`; shared: `components/`, `utils/`, `router/`, `middleware/`, `locales/`.

### Quy ước

- Component: PascalCase (`.vue`)
- Route names: tập trung trong `router/screen.ts`
- i18n: keys trong `locales/{vi,en,ko}/`
- Format: Prettier (double quotes, `printWidth: 150`, `trailingComma: "all"`)
- Trước PR: `npm run lint` và `npm run format` trong app tương ứng

### Env

- Dùng `.env` local; tham chiếu `.env.example`
- Biến bắt buộc: `VITE_API_ENDPOINT` (vd. `http://localhost:8080/api/v1`)
- Client thêm: `VITE_DOMAIN_SITE_CLIENT`, `VITE_DOMAIN_SITE_ADMIN`
- Không commit secret / mật khẩu thật

---

## 4. Git / PR

### Branch

- `feature/<ten-ngan>` — tính năng mới
- `fix/<ten-ngan>` — sửa lỗi
- `chore/<ten-ngan>` — tooling, docs, cleanup

### Commit

- Một ý tưởng / commit; message ngắn, rõ (tiếng Việt hoặc Anh, nhất quán trong branch)
- Ví dụ: `feat(admin): thêm CRUD hạng phòng`, `fix(auth): xử lý 401 trên http interceptor`

### PR

- Mô tả: mục tiêu + cách test (test plan)
- Không commit: `.env` có secret, `node_modules/`, `*.war`, file IDE tạm

### Checklist trước merge

- [ ] Đúng chuẩn coding (backend / FE) trong tài liệu này
- [ ] FE: `npm run lint` (app đã sửa)
- [ ] Backend: build Gradle thành công nếu đụng Java
- [ ] Không lộ credentials

---

## 5. Deploy

### Local

| Module | Lệnh chính |
|--------|------------|
| Backend | JDK 17 + Gradle; chạy `Application` hoặc `./gradlew bootRun` — API base `http://localhost:8080/api/v1` |
| FE Admin | `npm install` → `npm run dev` (mặc định cổng Vite; xem README app) |
| FE Client | tương tự |

### Release (DATN — thủ công, chưa Docker)

1. Kiểm tra profile / cấu hình DB & upload (không hard-code secret mới vào repo).
2. Backend: `./gradlew bootWar` → `ALICE_HOTEL_ROOT.war`.
3. FE: `npm run build` (admin + client); xác nhận `VITE_API_ENDPOINT` production.
4. Smoke: login `POST /api/v1/auth/login`, một API admin có session, mở UI admin/client.

---

## 6. Definition of Done

Feature / fix được coi là xong khi:

- [ ] Đúng layer / store / API envelope theo STANDARDS
- [ ] Endpoint mới (nếu có) theo REST `/a/...`
- [ ] Không trả entity thô; FE đọc `response.data.data`
- [ ] Lint/format (FE) / build (BE) OK
- [ ] Đã smoke luồng chính liên quan (login hoặc CRUD feature)
- [ ] PR có mục tiêu + test plan; không commit artifact/secret

