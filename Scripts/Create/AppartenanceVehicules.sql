USE [pubeotest]
GO

/****** Object:  Table [dbo].[AppartenanceVehicules]    Script Date: 30-12-19 02:50:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AppartenanceVehicules](
	[AppartenanceId] [uniqueidentifier] NOT NULL,
	[ParticulierId] [uniqueidentifier] NOT NULL,
	[Marque] [nvarchar](450) NULL,
	[Modele] [nvarchar](450) NULL,
 CONSTRAINT [PK_AppartenanceVehicules] PRIMARY KEY CLUSTERED 
(
	[AppartenanceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AppartenanceVehicules] ADD  CONSTRAINT [DF_AppartenanceVehicules_AppartenanceId]  DEFAULT (newsequentialid()) FOR [AppartenanceId]
GO

ALTER TABLE [dbo].[AppartenanceVehicules]  WITH CHECK ADD  CONSTRAINT [FK_AppartenanceVehicules_Particuliers_ParticulierId] FOREIGN KEY([ParticulierId])
REFERENCES [dbo].[Particuliers] ([Id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[AppartenanceVehicules] CHECK CONSTRAINT [FK_AppartenanceVehicules_Particuliers_ParticulierId]
GO

ALTER TABLE [dbo].[AppartenanceVehicules]  WITH CHECK ADD  CONSTRAINT [FK_AppartenanceVehicules_Vehicules_Marque_Modele] FOREIGN KEY([Marque], [Modele])
REFERENCES [dbo].[Vehicules] ([Marque], [Modele])
GO

ALTER TABLE [dbo].[AppartenanceVehicules] CHECK CONSTRAINT [FK_AppartenanceVehicules_Vehicules_Marque_Modele]
GO

