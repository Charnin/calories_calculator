# Production database

Local development continues to use the H2 database configured in `application.properties`.

For production, create a PostgreSQL database and set these environment variables:

- `SPRING_PROFILES_ACTIVE=prod`
- `JDBC_DATABASE_URL=jdbc:postgresql://HOST:5432/DATABASE`
- `DB_USERNAME=your_database_user`
- `DB_PASSWORD=your_database_password`

Then run the application normally. Spring Boot will load `application-prod.properties` and use PostgreSQL. Do not commit real database credentials to this repository.
