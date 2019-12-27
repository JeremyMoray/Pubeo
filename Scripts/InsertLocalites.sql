--create table [#tempLocalites] (
--[CodePostal] [nvarchar] (450),
--[Ville] [nvarchar] (max) NULL);


insert [#tempLocalites] ([CodePostal],[Ville])
select '1000','Bruxelles' UNION ALL
select '4000','Liège' UNION ALL
select '5000','Namur' UNION ALL
select '6000','Charleroi' UNION ALL
select '7000','Mons';