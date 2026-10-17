CREATE TABLE pictures(
    id                  NUMBER(9)       NOT NULL,
    source_file         VARCHAR2(256)   NOT NULL,
    upload_timestamp    TIMESTAMP       NOT NULL,

    CONSTRAINT pictures_id_unique UNIQUE(id),
    CONSTRAINT pictures_id_positive CHECK(id > 0)
)
