-- CREATE DATABASE IF NOT EXISTS 테이블명;
-- INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
-- INSERT IGNORE INTO roles(name) VALUES('ROLE_MANAGER');
-- INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');

-- INSERT INTO users(username, email, password)
-- VALUES ('test1', 'test1@test.com', '$2a$10$lStJzKk6ZGrc5uOfQp52yOYz5EQUELAI/U1braBDzJIKgr3MB5MPS');
-- INSERT INTO users(username, email, password)
-- VALUES ('test2', 'test2@test.com', '$2a$10$2z3X5YRNb5OrCmpfDQfuHuB7zsWijfrA0HkiG39tMjZCFPihp6epe');
-- INSERT INTO users(username, email, password)
-- VALUES ('test3', 'test3@test.com', '$2a$10$9X3g7RNH1g/5OAi9CXPrA.jj.7mfQEHJctSWdk/FRLIP7qmfW2Aci');

-- INSERT INTO user_roles VALUES (1, 1);
-- INSERT INTO user_roles VALUES (2, 1);
-- INSERT INTO user_roles VALUES (2, 2);
-- INSERT INTO user_roles VALUES (3, 1);
-- INSERT INTO user_roles VALUES (3, 2);
-- INSERT INTO user_roles VALUES (3, 3);

-- 아래는 중복 데이터 삽입을 방지하기 위해 데이터 조회 후, 없을 때만 삽입하도록 작성한 SQL문이다.

-- USER, MANAGER, ADMIN 권한 추가
INSERT INTO roles(name)
SELECT 'ROLE_USER' FROM dual
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_USER');

INSERT INTO roles(name)
SELECT 'ROLE_MANAGER' FROM dual
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_MANAGER');

INSERT INTO roles(name)
SELECT 'ROLE_ADMIN' FROM dual
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_ADMIN');

-- test1, test2, test3 계정 추가 (비밀번호는 test1, test2, test3)
INSERT INTO users(username, email, password)
SELECT 'test1', 'test1@test.com', '$2a$10$lStJzKk6ZGrc5uOfQp52yOYz5EQUELAI/U1braBDzJIKgr3MB5MPS' FROM dual
WHERE NOT EXISTS (SELECT username, email, password FROM users WHERE username = 'test1');

INSERT INTO users(username, email, password)
SELECT 'test2', 'test2@test.com', '$2a$10$2z3X5YRNb5OrCmpfDQfuHuB7zsWijfrA0HkiG39tMjZCFPihp6epe' FROM dual
WHERE NOT EXISTS (SELECT username, email, password FROM users WHERE username = 'test2');

INSERT INTO users(username, email, password)
SELECT 'test3', 'test3@test.com', '$2a$10$9X3g7RNH1g/5OAi9CXPrA.jj.7mfQEHJctSWdk/FRLIP7qmfW2Aci' FROM dual
WHERE NOT EXISTS (SELECT username, email, password FROM users WHERE username = 'test3');

-- test1 계정에 USER 권한 추가
-- test2 계정에 USER, MANAGER 권한 추가
-- test3 계정에 USER, MANAGER, ADMIN 권한 추가
INSERT INTO user_roles(user_id, role_id)
SELECT 1, 1 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 1 AND role_id = 1);

INSERT INTO user_roles(user_id, role_id)
SELECT 2, 1 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 2 AND role_id = 1);

INSERT INTO user_roles(user_id, role_id)
SELECT 2, 2 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 2 AND role_id = 2);

INSERT INTO user_roles(user_id, role_id)
SELECT 3, 1 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 3 AND role_id = 1);

INSERT INTO user_roles(user_id, role_id)
SELECT 3, 2 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 3 AND role_id = 2);

INSERT INTO user_roles(user_id, role_id)
SELECT 3, 3 FROM dual
WHERE NOT EXISTS (SELECT user_id, role_id FROM user_roles WHERE user_id = 3 AND role_id = 3);