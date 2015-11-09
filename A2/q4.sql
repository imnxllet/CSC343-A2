SET search_path TO artistdb;

CREATE VIEW MultiGenreArtist AS
SELECT a1.artist_id, a1.genre_id
FROM Album a1, Album a2, Album a3
WHERE a1.artist_id = a2.artist_id = a3.artist_id AND
	a1.genre_id <> a2.genre_id AND a1.genre_id <> a3.genre_id
	AND a2.genre_id <> a3.genre_id;

CREATE VIEW SongWriterGenre AS
SELECT s.songwriter_id AS artist_id, a.genre_id
FROM Song s JOIN BelongsToAlbum b ON s.song_id = b.song_id
	JOIN Album a ON a.album_id = b.album_id;
	
CREATE VIEW MultiGenreWriter AS
SELECT s1.artist_id, s1.genre_id
FROM SongWriterGenre s1, SongWriterGenre s2, SongWriterGenre s3
WHERE s1.artist_id = s2.artist_id = s3.artist_id AND
	s1.genre_id <> s2.genre_id AND s1.genre_id <> s3.genre_id
	AND s2.genre_id <> s3.genre_id;
	
CREATE VIEW MultiGenre AS
(SELECT *, capacity as "Musician/band" FROM MultiGenreArtist)
UNION ALL
(SELECT *, capacity as "Song writer" FROM MultiGenreWriter;)

CREATE VIEW GenreCount AS
SELECT DISTINCT m.artist_id, m.capacity, count(m.genre_id) AS genres
FROM MultiGenre m
GROUP BY m.capacity, m.artist_id
ORDER BY m.capacity ASC, count(m.genre_id) DESC, a.name ASC;

SELECT a.name, g.capacity, g.genres
FROM GenreCount g, Artist a
WHERE a.artist_id = g.artist_id;
