# QA-ThinkBoard (Spring Boot + React + MongoDB)

Simple fullstack app for QA testing with Sign Up / Sign In and CRUD Notes.

## Tech
- Backend: Spring Boot 3, Spring Security (JWT), MongoDB
- Frontend: React + Vite + Tailwind/DaisyUI

## Prerequisites
- Java 17+
- Node 18+
- MongoDB Atlas connection string

## Environment

Backend uses env vars:
- `MONGODB_URI` – your MongoDB connection string
- `JWT_SECRET` – base64-encoded secret for JWT (HS256)

Frontend optional env:
- `VITE_API_BASE_URL` – defaults to `http://localhost:5001/api`

For quick start, you can export inline in your shell or set OS env vars.

Example values:
```
MONGODB_URI=mongodb+srv://rmashendeminda_db_user:TAkzGkh6PCn2lG93@cluster0.ho19hc6.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
JWT_SECRET=dGhpc19pc19hX3NhbXBsZV9iYXNlNjRfMjU2Yml0X3NlY3JldA==
```

## Run Backend

```
cd thinkboard
set MONGODB_URI=your_connection_string_here
set JWT_SECRET=your_base64_secret_here
mvn spring-boot:run
```

Server runs on `http://localhost:5001` with API under `/api`.

## Run Frontend

```
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

## Auth
- Sign Up: `POST /api/auth/signup` { email, password } → { token }
- Sign In: `POST /api/auth/signin` { email, password } → { token }
Send `Authorization: Bearer <token>` for protected endpoints.

## Notes API (protected)
- GET `/api/notes` – list current user notes
- POST `/api/notes` – create note { title, content }
- GET `/api/notes/{id}` – get note by id
- PUT `/api/notes/{id}` – update note
- DELETE `/api/notes/{id}` – delete note

## Frontend routes
- `/` – list notes
- `/create` – create note
- `/note/:id` – edit/delete note
- `/signin`, `/signup` – auth pages

## Build frontend for prod
```
cd frontend
npm run build
```

Serve `frontend/dist` behind any static server/proxy to backend `/api`.
