--create table [#tempProfessionnels] (
--[Id] [uniqueidentifier],
--[NomEntreprise] [nvarchar] (100) NULL,
--[Adresse] [nvarchar] (300) NULL,
--[NumeroTel] [nvarchar] (max) NULL,
--[MotDePasse] [nvarchar] (max) NULL,
--[Mail] [nvarchar] (100) NULL,
--[NumeroTVA] [nvarchar] (max) NULL,
--[LocaliteCode] [nvarchar] (450) NULL);


insert [#tempProfessionnels] ([Id],[NomEntreprise],[Adresse],[NumeroTel],[MotDePasse],[Mail],[NumeroTVA],[LocaliteCode])
select '{90a4eea9-59ad-411c-9454-096c63b3bfe9}','Le Locale','8 Avenue George Brassens','+32481642598','testtest22','gilles.dupierreux@gmail.com','1122334450','1000' UNION ALL
select '{e9684930-755f-4860-a728-604a3985edc4}','Michelin LLC','21 Rue du Roseraie','+32471865124','testtest11','JMichelin@gmail.com','1234567890','5000' UNION ALL
select '{1cfa3a07-8868-4737-bad8-f48e6008011d}','Pizza Hut','17 Rue Jean Baptiste Brabant','+3281260200','pizzapizza1','pizza.namur@pizzahut.com','5544332210','5000';