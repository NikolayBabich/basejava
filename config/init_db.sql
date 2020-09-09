CREATE TABLE resume
(
    uuid      CHAR(8) NOT NULL
        PRIMARY KEY,
    full_name TEXT    NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL  NOT NULL
        PRIMARY KEY,
    resume_uuid CHAR(8) NOT NULL
        REFERENCES resume
            ON DELETE CASCADE,
    type        TEXT    NOT NULL,
    text_link   TEXT    NOT NULL,
    url_link    TEXT    NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);
