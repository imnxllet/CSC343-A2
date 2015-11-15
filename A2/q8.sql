SET search_path TO artistdb;

CREATE VIEW ACDCMembers AS
SELECT artist_id, band_id
FROM WasInBand
WHERE band_id = (SELECT artist_id
	FROM Artist WHERE name = 'AC/DC');
	
INSERT INTO WasInBand(artist_id, band_id, 
						start_year, end_year)
(SELECT artist_id, band_id, 
	2014 AS start_year, 2015 AS end_year
 FROM ACDCMembers);
	

