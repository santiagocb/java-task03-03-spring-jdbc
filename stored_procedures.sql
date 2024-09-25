DROP FUNCTION IF EXISTS selectfullnameswithhigherfriendsandlikes(BIGINT, TIMESTAMP, TIMESTAMP);

CREATE OR REPLACE FUNCTION selectfullnameswithhigherfriendsandlikes(
    minFriendCount BIGINT,
    startDate TIMESTAMP,
    endDate TIMESTAMP
) RETURNS TABLE (
    name VARCHAR,
    surname VARCHAR
)
AS $$
BEGIN
    RETURN QUERY SELECT DISTINCT u.name, u.surname
    FROM Users u
    JOIN (
        SELECT f.userid1 AS userid
        FROM Friendships f
        GROUP BY f.userid1
        HAVING COUNT(f.userid1) > minFriendCount
    ) friends
    ON u.id = friends.userid
    JOIN (
        SELECT l.userid AS userid
        FROM Likes l
        WHERE l.timestamp >= startDate AND l.timestamp < endDate
        GROUP BY l.userid
        HAVING COUNT(l.userid) > 100
    ) likes
    ON u.id = likes.userid;
END; $$
LANGUAGE plpgsql;

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


