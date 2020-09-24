CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        PRIMARY KEY,
    full_name TEXT    NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL  NOT NULL
        PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL
        REFERENCES resume
            ON DELETE CASCADE,
    type        TEXT    NOT NULL,
    text_link   TEXT    NOT NULL,
    url_link    TEXT
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

CREATE TABLE section
(
    id        SERIAL  NOT NULL
        PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL
        REFERENCES resume
            ON DELETE CASCADE,
    type      TEXT NOT NULL,
    content   TEXT
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, type);
