INSERT INTO Account(account_id, createdAt, modifiedAt, email, intro, nickname, password, profile, role, deleted) VALUES('10001', '2022-10-14 10:00:01', '2022-10-14 10:00:01', 'test1@test.com', 'testIntro', 'testNickname1', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'https://main-image-repo.s3.ap-northeast-2.amazonaws.com/39c10d6d-2765-479d-a45f-662e619fd006.jpeg', 'USER', false);
INSERT INTO Account(account_id, createdAt, modifiedAt, email, intro, nickname, password, profile, role, deleted) VALUES('10002', '2022-10-14 10:00:02', '2022-10-14 10:00:02', 'test2@test.com', 'testIntro', 'testNickname2', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'https://main-image-repo.s3.ap-northeast-2.amazonaws.com/39c10d6d-2765-479d-a45f-662e619fd006.jpeg', 'USER', false);
INSERT INTO Account(account_id, createdAt, modifiedAt, email, intro, nickname, password, profile, role, deleted) VALUES('10003', '2022-10-14 10:00:03', '2022-10-14 10:00:03', 'test3@test.com', 'testIntro', 'testNickname3', '$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.', 'https://main-image-repo.s3.ap-northeast-2.amazonaws.com/39c10d6d-2765-479d-a45f-662e619fd006.jpeg', 'USER', false);

INSERT INTO Follow(follow_id, createdAt, modifiedAt, follower_id, following_id, deleted) VALUES('20001', '2022-10-14 11:00:01', '2022-10-14 11:00:01', '10001', '10002', false);
INSERT INTO Follow(follow_id, createdAt, modifiedAt, follower_id, following_id, deleted) VALUES('20002', '2022-10-14 11:00:01', '2022-10-14 11:00:01', '10001', '10003', false);
INSERT INTO Follow(follow_id, createdAt, modifiedAt, follower_id, following_id, deleted) VALUES('20003', '2022-10-14 11:00:01', '2022-10-14 11:00:01', '10002', '10001', false);

INSERT INTO Board(board_id, createdAt, modifiedAt, category, content, images, location, thumbnail, title, views, account_id, deleted) VALUES('30001', '2022-10-14 12:00:01', '2022-10-14 12:00:01', 'RESTAURANT', 'testContents', '["test1Image1","test1Image2"]', 'test-location', 'test1Thumbnail', 'testTitle', '10', '10001', false);
INSERT INTO Board(board_id, createdAt, modifiedAt, category, content, images, location, thumbnail, title, views, account_id, deleted) VALUES('30002', '2022-10-14 12:00:02', '2022-10-14 12:00:02', 'RESTAURANT', 'testContents', '["test2Image1","test2Image2"]', 'test-location', 'test2Thumbnail', 'testTitle', '10', '10002', false);
INSERT INTO Board(board_id, createdAt, modifiedAt, category, content, images, location, thumbnail, title, views, account_id, deleted) VALUES('30003', '2022-10-14 12:00:03', '2022-10-14 12:00:03', 'RESTAURANT', 'testContents', '["test3Image1","test3Image2"]', 'test-location', 'test3Thumbnail', 'testTitle', '10', '10003', false);

INSERT INTO Comment(comment_id, createdAt, modifiedAt, content, account_id, board_id, deleted) VALUES('40001', '2022-10-14 13:00:01', '2022-10-14 13:00:01', 'testContents', '10001', '30001', false);
INSERT INTO Comment(comment_id, createdAt, modifiedAt, content, account_id, board_id, targetAccount_id, parent_id, deleted) VALUES('40002', '2022-10-14 13:00:01', '2022-10-14 13:00:01', 'testContents', '10002', '30001', '10001', '40001', false);
INSERT INTO Comment(comment_id, createdAt, modifiedAt, content, account_id, board_id, targetAccount_id, parent_id, deleted) VALUES('40003', '2022-10-14 13:00:01', '2022-10-14 13:00:01', 'testContents', '10002', '30001', '10001', '40001', false);
INSERT INTO Comment(comment_id, createdAt, modifiedAt, content, account_id, board_id, targetAccount_id, parent_id, deleted) VALUES('40004', '2022-10-14 13:00:01', '2022-10-14 13:00:01', 'testContents', '10002', '30001', '10001', '40001', false);