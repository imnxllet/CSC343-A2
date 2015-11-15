SET search_path TO artistdb;

CREATE VIEW MultiGenreArtist AS
SELECT a1.artist_id, a1.genre_id
FROM Album a1, Album a2, Album a3
WHERE a1.artist_id = a2.artist_id AND a1.artist_id = a3.artist_id AND
	a2.artist_id = a3.artist_id AND a1.genre_id <> a2.genre_id AND 
	a1.genre_id <> a3.genre_id	AND a2.genre_id <> a3.genre_id;
	
CREATE VIEW IsABand AS
SELECT *
FROM MultiGenreArtist m
WHERE EXISTS (SELECT * FROM WasInBand w 
				WHERE m.artist_id = w.band_id);

CREATE VIEW NotABand AS
SELECT *
FROM MultiGenreArtist m
WHERE NOT EXISTS (SELECT * FROM WasInBand w 
					WHERE m.artist_id = w.band_id);

CREATE VIEW SongWriterGenre AS
SELECT s.songwriter_id AS artist_id, a.genre_id
FROM Song s JOIN BelongsToAlbum b ON s.song_id = b.song_id
	JOIN Album a ON a.album_id = b.album_id;
	
CREATE VIEW MultiGenreWriter AS
SELECT s1.artist_id, s1.genre_id
FROM SongWriterGenre s1, SongWriterGenre s2, SongWriterGenre s3
WHERE s1.artist_id = s2.artist_id AND s1.artist_id = s3.artist_id AND
	s2.artist_id = s3.artist_id AND s1.genre_id <> s2.genre_id AND 
	s1.genre_id <> s3.genre_id AND s2.genre_id <> s3.genre_id;
	
CREATE VIEW MultiGenre AS
(SELECT *, 'Musician' AS capacity FROM NotABand)
UNION
(SELECT *, 'Band' AS capacity FROM IsABand)
UNION
(SELECT *, 'Song writer' AS capacity FROM MultiGenreWriter);

CREATE VIEW GenreCount AS
SELECT DISTINCT a.name, m.capacity, count(m.genre_id) AS genres
FROM MultiGenre m, Artist a
WHERE m.artist_id = a.artist_id
GROUP BY m.capacity, a.name
ORDER BY m.capacity ASC, count(m.genre_id) DESC, a.name ASC;

SELECT *
FROM GenreCount g;
