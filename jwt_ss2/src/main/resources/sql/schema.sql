DROP TABLE USERS CASCADE CONSTRAINTS;

CREATE TABLE USERS (
     id VARCHAR2(20) PRIMARY KEY,              -- id를 PRIMARY KEY로 설정
    name VARCHAR2(20) NOT NULL,    
      role VARCHAR2(20) DEFAULT 'USER' NOT NULL,-- name은 필수
    email9 VARCHAR2(100) UNIQUE NOT NULL,      -- email9은 고유값
    password VARCHAR2(1000) NOT NULL,         -- password는 필수
    tel VARCHAR2(50),                         -- 전화번호 (필수는 아님)
    birth VARCHAR2(20)   
    
);

-- 예시 사용자 데이터 추가
INSERT INTO user (id, password, role) VALUES ('admin', '$2y$05$ZI08kWOifik0r6YR6QbpxO0ovUmAXYB9FRAvIwlCsDwjYN475m6R6', 'ROLE_USER');

COMMIT;