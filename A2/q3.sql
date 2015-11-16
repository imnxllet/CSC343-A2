SET search_path TO artistdb;

Select label_name, year, sum(sales)
from RecordLabel, Album, ProducedBy
where ProducedBy.album_id = Album.album_id and ProducedBy.label_id = RecordLabel.label_id
group by label_name, year
having sum(sales) > 0
order by label_name, year;