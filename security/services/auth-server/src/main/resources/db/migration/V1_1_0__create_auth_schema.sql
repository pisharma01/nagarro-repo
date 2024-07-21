
CREATE TABLE USER_DTLS (
    ID NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    USERNAME NVARCHAR2(128) NOT NULL,
    PASSWORD NVARCHAR2(128) NOT NULL,
    FIRSTNAME NVARCHAR2(128) NOT NULL,
    LASTNAME NVARCHAR2(128)  NULL,
    ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL,
    CREATED_AT TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE AUTHORITY_DTLS (
    ID NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    USER_ID NUMBER NOT NULL,
    AUTHORITY NVARCHAR2(128) NOT NULL,
    CREATED_AT TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    PRIMARY KEY(ID)
);
ALTER TABLE AUTHORITY_DTLS ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (USER_ID, AUTHORITY);
ALTER TABLE AUTHORITY_DTLS ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (USER_ID) REFERENCES USER_DTLS (ID) ENABLE;