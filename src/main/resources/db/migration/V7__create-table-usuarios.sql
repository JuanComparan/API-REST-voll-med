
CREATE TABLE usuarios (
                         id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                         login VARCHAR(100) NOT NULL,
                         clave VARCHAR(300) NOT NULL UNIQUE,

                         PRIMARY KEY (id)
);