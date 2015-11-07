SET search_path TO artistdb;

CREATE VIEW CollabAlbums AS
SELECT album_id 
FROM Collaboration NATURAL JOIN BelongsToAlbum;

CREATE VIEW CollabSales AS
SELECT r.artist_id, avg(a.sales) AS avg_sales
FROM Album a, Artist r, CollabAlbums c
WHERE a.artist_id = r.artist_id AND a.album_id = c.album_id
GROUP BY r.artist_id;

CREATE VIEW NotCollabSales AS
SELECT r.artist_id, a.sales
FROM Album a, Artist r, AllCollabs c
WHERE a.artist_id = r.artist_id AND a.album_id <> c.album_id;

SELECT a.name AS artists, c.avg_sale AS avg_collab_sales
FROM CollabSales c, Artist a
WHERE c.artist_id = a.artist_id 
	AND c.avg_sales > ANY 
		(SELECT n.sales 
			FROM NotCollabSales n
			WHERE n.artist_id = c.artist_id)							
ORDER BY a.name;
