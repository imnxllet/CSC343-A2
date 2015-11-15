SET search_path TO artistdb;

CREATE VIEW Thriller AS
SELECT *
FROM Album a
WHERE a.title = 'Thriller';

CREATE VIEW ThrillerSongs AS
SELECT b.song_id
FROM Thriller t JOIN BelongsToAlbum b 
	ON t.album_id = b.album_id;

DELETE FROM ProducedBy 
	WHERE album_id = (SELECT t.album_id FROM Thriller t);

DELETE FROM Collaboration
	WHERE song_id = (SELECT t.song_id FROM ThrillerSongs t);

DELETE FROM Song
	WHERE song_id = (SELECT t.song_id FROM ThrillerSongs t);

DELETE FROM BelongsToAlbum
	WHERE album_id = (SELECT t.album_id FROM Thriller t);
	
DELETE FROM Album WHERE title = 'Thriller';
