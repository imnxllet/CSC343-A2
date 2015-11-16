SET search_path TO artistdb;

select Song.title, Album.year, Artist.name
from Song, Album, Artist,
(
select t1.album_id, t1.song_id
from BelongsToAlbum t1 inner join (
select count(*) tot, song_id
from BelongsToAlbum
group by song_id) t2
on t1.song_id = t2.song_id
where t2.tot > 1) AS Cover_album

where Album.album_id = Cover_album.album_id and Song.song_id = Cover_album.song_id and Album.artist_id = Artist.artist_id
order by Song.title, Album.year, Artist.name;