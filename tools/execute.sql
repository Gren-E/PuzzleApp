CREATE TABLE pictures(
    picture_id                  NUMBER(9)       NOT NULL,
    source_file         VARCHAR2(256)   NOT NULL,
    upload_timestamp    TIMESTAMP       NOT NULL,

    CONSTRAINT pictures_id_unique UNIQUE(picture_id),
    CONSTRAINT pictures_id_positive CHECK(picture_id > 0)
)

CREATE TABLE picture_tags(
    picture_id  NUMBER(9)       NOT NULL,
    tag         VARCHAR2(256)   NOT NULL,

    CONSTRAINT picture_tags_id_positive CHECK(picture_id > 0)
)

CREATE TABLE game_templates(
    template_id NUMBER(9)       NOT NULL,
    picture_id  NUMBER(9)       NOT NULL,
    piece_style VARCHAR2(256)   NOT NULL,
    rows        NUMBER(9)       NOT NULL,
    columns     NUMBER(9)       NOT NULL,
    rotation    BOOLEAN         NOT NULL,

    CONSTRAINT game_templates_template_id_unique UNIQUE(template_id),
    CONSTRAINT game_templates_template_id_positive CHECK(template_id > 0),
    CONSTRAINT game_templates_picture_id_exists FOREIGN KEY(picture_id) REFERENCES pictures(picture_id),
    CONSTRAINT game_templates_rows_positive CHECK(rows > 0),
    CONSTRAINT game_templates_columns_positive CHECK(columns > 0)
)

CREATE TABLE games(
    game_id             NUMBER(9)       NOT NULL,
    status              VARCHAR2(256)   NOT NULL,
    template_id         NUMBER(9),
    picture_id          NUMBER(9)       NOT NULL,
    piece_style         VARCHAR2(256)   NOT NULL,
    rows                NUMBER(9)       NOT NULL,
    columns             NUMBER(9)       NOT NULL,
    rotation            BOOLEAN         NOT NULL,
    start_timestamp     TIMESTAMP       NOT NULL,
    duration_in_seconds NUMBER(99)      NOT NULL,

    CONSTRAINT games_game_id_unique UNIQUE(game_id),
    CONSTRAINT games_game_id_positive CHECK(game_id > 0),
    CONSTRAINT games_template_id_exists FOREIGN KEY(template_id) REFERENCES game_templates(template_id),
    CONSTRAINT games_picture_id_exists FOREIGN KEY(picture_id) REFERENCES pictures(picture_id),
    CONSTRAINT games_rows_positive CHECK(rows > 0),
    CONSTRAINT games_columns_positive CHECK(columns > 0),
    CONSTRAINT games_duration_positive CHECK(duration_in_seconds > 0)
)

CREATE TABLE puzzle_data(
    game_id                 NUMBER(9)       NOT NULL,
    piece_ordinal           NUMBER(9)       NOT NULL,
    cluster_id              NUMBER(9)       NOT NULL,
    piece_x                 NUMBER(9)       NOT NULL,
    piece_y                 NUMBER(9)       NOT NULL,
    orientation             NUMBER(9)       NOT NULL,
    neighbouring_ordinals   VARCHAR2(256)   NOT NULL,
    shape                   BLOB            NOT NULL,

    CONSTRAINT puzzle_data_game_id_exists FOREIGN KEY(game_id) REFERENCES games(game_id),
    CONSTRAINT puzzle_data_piece_ordinal CHECK(piece_ordinal > 0),
    CONSTRAINT puzzle_data_cluster_id_positive CHECK(cluster_id > 0),
    CONSTRAINT puzzle_data_orientation CHECK(orientation >= 0)
)
