Select name, nationality
from Artist
where EXTRACT(YEAR from Artist.birthdate) = (
	select min(year)
	from Album, Artist 
	where Artist.artist_id = Album.artist_id and Artist.name = 'Steppenwolf')
order by name;