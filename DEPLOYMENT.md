# Deploy to Render with Supabase PostgreSQL

Local development continues to use the H2 database configured in `application.properties`.

## 1. Create the database

Create a Supabase project, open **Connect**, and copy the PostgreSQL connection details. For a Render web service, use the Supabase session pooler connection when it is available.

## 2. Create the Render web service

Connect this GitHub repository to Render and create a **Web Service**. Choose **Docker** as the runtime. Render will build the included `Dockerfile` automatically.

Set these environment variables in the Render dashboard:

- `SPRING_PROFILES_ACTIVE=prod`
- `JDBC_DATABASE_URL=jdbc:postgresql://HOST:5432/DATABASE`
- `DB_USERNAME=your_database_user`
- `DB_PASSWORD=your_database_password`

Use the host, port, database name, username, and password shown by Supabase. The application reads Render's `PORT` automatically, so no port variable needs to be added manually.

Deploy the service. Spring Boot will load `application-prod.properties` and use PostgreSQL.

Do not commit real database credentials or `.env` files to this repository.
