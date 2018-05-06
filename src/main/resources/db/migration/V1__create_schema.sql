CREATE TABLE city
  (
     id          VARCHAR(255) NOT NULL,
     created_at  TIMESTAMP NOT NULL,
     description VARCHAR(255) NOT NULL,
     NAME        VARCHAR(255) NOT NULL,
     popularity  INTEGER NOT NULL,
     population  INTEGER NOT NULL,
     PRIMARY KEY (id)
  );

CREATE TABLE favorites
  (
     users_id     VARCHAR(255) NOT NULL,
     favorites_id VARCHAR(255) NOT NULL,
     PRIMARY KEY (users_id, favorites_id)
  );

CREATE TABLE token
  (
     id         VARCHAR(255) NOT NULL,
     expires_at TIMESTAMP NOT NULL,
     token      VARCHAR(255) NOT NULL,
     user_id    VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  );

CREATE TABLE USER
  (
     id       VARCHAR(255) NOT NULL,
     email    VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL,
     verified BOOLEAN NOT NULL,
     PRIMARY KEY (id)
  );

ALTER TABLE city
  ADD CONSTRAINT uk_name UNIQUE (NAME);

ALTER TABLE USER
  ADD CONSTRAINT uk_email UNIQUE (email);

ALTER TABLE favorites
  ADD CONSTRAINT fk_favorites_favorites_id_city FOREIGN KEY (favorites_id)
  REFERENCES city;

ALTER TABLE favorites
  ADD CONSTRAINT fk_users_users_id_user FOREIGN KEY (users_id) REFERENCES
  USER;

ALTER TABLE token
  ADD CONSTRAINT fk_token_user_id_user FOREIGN KEY (user_id) REFERENCES
  USER;