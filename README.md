# Sistema de Créditos

Gestión de solicitudes de crédito: crear, listar por estado y aprobar/rechazar con comentario.

## Cómo levantarlo

Requisito: Docker con Docker Compose.

### 1. Local (todo en Docker)

Base de datos + backend + frontend.

```bash
docker compose up --build
```

| Servicio | URL |
|---|---|
| Frontend | http://localhost:4200 |
| API | http://localhost:8080 |
| Swagger | http://localhost:8080/swagger-ui.html |

### 2. Producción (frontend contra el backend real)

```bash
cd frontend/sistemas-creditos
docker compose up --build
```

Frontend en http://localhost:4200, usando la API de `src/environments/environment.prod.ts`.

## Stack

| Capa | Tecnología |
|---|---|
| Backend | Java 21, Spring Boot 4.1, Spring Security (JWT), Spring Data JPA |
| Base de datos | PostgreSQL 17 |
| Frontend | Angular 22 + Ionic 9 |

## Estructura

```
/backend/demo
  ├─ docker-compose.yml        backend + PostgreSQL
  ├─ docker-compose.prod.yml   backend con .env
  └─ API.md                    endpoints y errores
/frontend/sistemas-creditos
  └─ docker-compose.yml        frontend de producción
/docker-compose.yml            stack local completo
```

## Sin Docker

Frontend (Node 24, pnpm 10):

```bash
cd frontend/sistemas-creditos
pnpm install
pnpm start
```

## Tests

```bash
cd backend/demo
./mvnw test
```

## API

| Método | Endpoint | Auth |
|---|---|---|
| POST | `/auth/register` | No |
| POST | `/auth/login` | No |
| POST | `/auth/refresh-token` | Sí |
| POST | `/credit-requests/create-credits` | Sí |
| POST | `/credit-requests/get-credits` | Sí |
| GET | `/credit-requests/{id}` | Sí |
| POST | `/credit-requests/update-credit-status` | Sí |



Reglas: monto entre $500 y $50,000, plazo entre 6 y 60 meses, solo una solicitud `PENDING` puede aprobarse o rechazarse (con comentario).

## Desplegar el backend (para prod)

```bash
cd backend/demo
cp .env.example .env
docker compose -f docker-compose.prod.yml up -d --build
```
