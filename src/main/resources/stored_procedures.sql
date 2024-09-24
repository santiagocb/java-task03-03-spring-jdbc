CREATE OR REPLACE PROCEDURE insertUser(
    IN userName VARCHAR,
    IN userSurname VARCHAR,
    IN userBirthdate VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Users (name, surname, birthdate)
    VALUES (userName, userSurname, userBirthdate);
END;
$$;


CREATE OR REPLACE PROCEDURE insertFriendship(
    IN userId1 BIGINT,
    IN userId2 BIGINT,
    IN created TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Friendships (userid1, userid2, timestamp)
    VALUES (userId1, userId2, created);
END;
$$;

CREATE OR REPLACE PROCEDURE insertPost(
    IN userId BIGINT,
    IN text VARCHAR,
    IN created TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Posts (userId, text, timestamp)
    VALUES (userId, text, created);
END;
$$;

CREATE OR REPLACE PROCEDURE insertLike(
    IN postId BIGINT,
    IN userId BIGINT,
    IN created TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Likes (postid, userid, timestamp)
    VALUES (postId, userId, created);
END;
$$;

