CREATE TABLE "password"(
                           "id" bigint NOT NULL,
                           "password_value" VARCHAR(255) NOT NULL,
                           "service_name" VARCHAR(255) NOT NULL,
                           "date" DATE NOT NULL
);
ALTER TABLE
    "password" ADD PRIMARY KEY("id");