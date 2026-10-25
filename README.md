# Alice Hotel

Monorepo quản lý khách sạn — backend Spring Boot + 2 frontend Vue 3.

| Module | Path | Stack |
|--------|------|--------|
| Backend API | [`alice.hotel.backend`](alice.hotel.backend/) | Java 17, Spring Boot, Gradle (WAR) |
| Admin UI | [`alice.hotel.fe.admin`](alice.hotel.fe.admin/) | Vue 3, Vite, Pinia, Vuetify |
| Client UI | [`alice.hotel.fe.client`](alice.hotel.fe.client/) | Vue 3, Vite, Pinia, Vuetify |

## Tiêu chuẩn & backlog tính năng

- **Bắt buộc đọc trước khi code:** [STANDARDS.md](STANDARDS.md)
- **Backlog P0–P2 + khảo sát thị trường:** [FEATURES.md](FEATURES.md)
- Cursor rules: [`.cursor/rules/`](.cursor/rules/)

### Schema booking (Phase 0+)

Sau `alice_hotel_init.sql`, chạy thêm:

`alice.hotel.backend/src/main/resources/db/alice_hotel_booking_schema.sql`


## Chạy local nhanh

### Backend

```bash
cd alice.hotel.backend
# JDK 17 — chạy Application hoặc:
./gradlew bootRun
```

API: `http://localhost:8080/api/v1`

### Admin

```bash
cd alice.hotel.fe.admin
cp .env.example .env   # lần đầu
npm install
npm run dev
```

### Client

```bash
cd alice.hotel.fe.client
cp .env.example .env   # lần đầu
npm install
npm run dev
```

Chi tiết deploy / Git / DoD: xem [STANDARDS.md](STANDARDS.md).
