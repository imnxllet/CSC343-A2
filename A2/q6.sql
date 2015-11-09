SET search_path TO artistdb;

CREATE VIEW CanadianArtists AS
SELECT a.artist_id, a.name, a.nationality
FROM Artist a
WHERE nationality="Canada";

CREATE VIEW PublishedAlbums AS
SELECT a.album_id, a.artist_id, r.country
FROM Album a JOIN ProducedBy p ON a.album_id = p.album_id
	JOIN RecordLabel r ON p.label_id = r.label_id;

CREATE VIEW StartedIndie AS
SELECT c.artist_id, c.name, c.nationality
FROM CanadianArtists c, 
	(SELECT a1.artist_id, a1.album_id
	 FROM Album a1, Album a2
	 WHERE a1.year < a2.year AND a1.album_id <> a2.album_id
		AND a1.artist_id = a2.artist_id) a
WHERE c.artist_id = a.artist_id AND NOT EXISTS
	(SELECT *
	 FROM PublishedAlbums p
	 WHERE p.album_id = a.album_id);

SELECT DISTINCT c.name AS artist_name
FROM StartedIndie i, PublishedAlbums p
WHERE i.artist_id = p.artist_id AND r.country = "US"
ORDER BY c.name;

