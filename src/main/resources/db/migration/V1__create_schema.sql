CREATE TABLE measurement
(
    code             VARCHAR(10) PRIMARY KEY,
    source           VARCHAR(3)  NOT NULL,
    code_list_code   VARCHAR(6)  NOT NULL,
    display_value    VARCHAR(50) NOT NULL,
    long_description VARCHAR(255) NULL,
    from_date        DATE        NOT NULL,
    to_date          DATE NULL,
    sorting_priority INT NULL
);

