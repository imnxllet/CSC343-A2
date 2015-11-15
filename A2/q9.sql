
update WasInBand set end_year = 2014
where artist_id = 
(Select artist_id from Artist where name = 'Adam Levine');

update WasInBand set end_year = 2014 
where artist_id = (Select artist_id from Artist where name = 'Mick Jagger');

Insert into WasInBand Values ((Select artist_id from Artist where name = 'Mick Jagger'), 
(Select artist_id from Artist where name = 'Maroon 5'), 
2014, 2015);
