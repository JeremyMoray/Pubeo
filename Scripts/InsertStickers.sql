--create table [#tempStickers] (
--[Id] [uniqueidentifier],
--[Titre] [nvarchar] (max) NULL,
--[Description] [nvarchar] (300) NULL,
--[Hauteur] [int],
--[Largeur] [int],
--[NbUtilisationsRestantes] [int],
--[ProfessionnelId] [uniqueidentifier]);


insert [#tempStickers] ([Id],[Titre],[Description],[Hauteur],[Largeur],[NbUtilisationsRestantes],[ProfessionnelId])
select '{4182e8d0-16e8-4c3e-8f5f-151b83b29f46}','Pizza Namur','Circulez durant les heures (16:00 - 20:00) de pointes du Lundi au Vendredi',400,400,40,'{1cfa3a07-8868-4737-bad8-f48e6008011d}' UNION ALL
select '{5b47dc86-7409-4716-a44b-1ee9ed13b7bd}','24 day Drive','Touchez une prime après avoir circulez 24 jours d''affilés',100,100,40,'{e9684930-755f-4860-a728-604a3985edc4}' UNION ALL
select '{a6ca9c99-0542-4798-8c26-3ebb6a2097ba}','Namur Drive','Faites circulez le message autour de Bouge, Champion et Salzinnes',300,300,2,'{e9684930-755f-4860-a728-604a3985edc4}' UNION ALL
select '{1dae4f0f-4747-4e36-aa5a-4bb5ee254c6d}','24 day PizzaDrive','Circulez dans les environs de Namur pendant 24 jours!',500,500,100,'{1cfa3a07-8868-4737-bad8-f48e6008011d}' UNION ALL
select '{c869d310-3883-4796-a6d0-88b6f1a27183}','24 day Challenge','Pendant 24 jours, faites passez le message du projet Le Locale en centre-ville grâce à son sticker exclusive.',200,600,40,'{90a4eea9-59ad-411c-9454-096c63b3bfe9}' UNION ALL
select '{bc1d9bf1-d6b9-4f76-93ba-977fad48ea69}','Campagne Weekend','Prenez des photos de votre sticker appliqué ainsi que de votre kilométrage durant les weekend',100,100,20,'{1cfa3a07-8868-4737-bad8-f48e6008011d}' UNION ALL
select '{fdf69455-97fb-4904-a908-e5ffb677b1c1}','Campagne Schaerbeek ','Faites vos courses en toute tranquilité dans les commerces de Schaerbeek.',300,300,6,'{90a4eea9-59ad-411c-9454-096c63b3bfe9}' UNION ALL
select '{8997a549-0392-49bd-a1df-ffac83803a0c}','4 month Drive','Touchez une prime après avoir circulez 4 mois d''affilés',300,300,6,'{e9684930-755f-4860-a728-604a3985edc4}';