INSERT INTO member (member_id, email, password, nick_name, birth_date, gender, phone_number, profile_img, role, signup_path, signup_dttm, membership, created_dttm, updated_dttm, deleted_dttm)
VALUES
    (1L, 'user1@example.com', 'password1', 'User1', '1990-01-01', 'MALE', '01012345678', 'profile1.jpg', 'ROLE_MEMBER', 'NORMAL', NOW(), 'FREE', NOW(), NOW(), NULL),
    (2L, 'user2@example.com', 'password2', 'User2', '1995-05-05', 'FEMALE', '01098765432', 'profile2.jpg', 'ROLE_MEMBER', 'NORMAL', NOW(), 'FREE', NOW(), NOW(), NULL),
    (3L, 'user3@example.com', 'password3', 'User3', '1988-12-25', 'NONE', '01011112222', 'profile3.jpg', 'ROLE_MEMBER', 'NORMAL', NOW(), 'FREE', NOW(), NOW(), NULL),
    (4L, 'user4@example.com', 'password4', 'User4', '1990-10-05', 'MALE', '01022223333', 'profile4.jpg', 'ROLE_MEMBER', 'NORMAL', NOW(), 'FREE', NOW(), NOW(), NULL);

INSERT INTO comment (comment_id, gathering_id, member_id, comment_content, created_dttm, updated_dttm, deleted_dttm)
VALUES
    (1L, 1L, 1L, 'comment1', NOW(), NOW(), NULL),
    (2L, 1L, 2L, 'comment2', NOW(), NOW(), NULL),
    (3L, 2L, 1L, 'comment3', NOW(), NOW(), NULL),
    (4L, 1L, 1L, 'comment4', NOW(), NOW(), NOW());

INSERT INTO gathering (gathering_id, member_id, title, content, gathering_type, maximum_participant, start_dttm, end_dttm, category, address, detailed_address, main_img, participants_type, fee, participant_selection_method, status, created_dttm, updated_dttm, deleted_dttm)
VALUES (1L, 1L, '제목1', '본문1', 'MEETING', 30, NOW(), NOW(), '카테고리1', '주소1', '상세주소1', '메인이미지1', 'ADULT', 0, 'FIRST_COME', 'PROGRESS', NOW(), NOW(), NULL),
       (2L, 1L, '제목2', '본문2', 'EVENT', 40, NOW(), NOW(), '카테고리2', '주소2', '상세주소2', '메인이미지2', 'MINOR', 10, 'UNLIMITED_APPLICATION', 'CANCELED', NOW(), NOW(), NULL);

INSERT INTO participation (participation_id, gathering_id, member_id, status, created_dttm, updated_dttm, deleted_dttm)
VALUES
    (1L, 1L, 2L, 'APPROVED', NOW(), NOW(), NULL),
    (2L, 1L, 3L, 'APPROVED', NOW(), NOW(), NULL),
    (3L, 2L, 2L, 'CANCELED', NOW(), NOW(), NULL);