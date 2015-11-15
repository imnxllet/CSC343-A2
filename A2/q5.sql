Select distinct Artist.name, self_album.title
from Artist, 
(select Album.title, Album.artist_id from Album, BelongsToAlbum, Song where 
	Album.album_id = BelongsToAlbum.album_id and BelongsToAlbum.song_id = Song.song_id and Song.songwriter_id = Album.artist_id)
AS self_album
where self_album.artist_id = Artist.artist_id
order by name, self_album.title;