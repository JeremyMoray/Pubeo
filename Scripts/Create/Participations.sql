USE [pubeotest]
GO

/****** Object:  Table [dbo].[Participations]    Script Date: 30-12-19 02:49:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Participations](
	[ParticipationId] [uniqueidentifier] NOT NULL,
	[ParticulierId] [uniqueidentifier] NOT NULL,
	[StickerId] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_Participations] PRIMARY KEY CLUSTERED 
(
	[ParticipationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Participations] ADD  CONSTRAINT [DF_Participations_ParticipationId]  DEFAULT (newsequentialid()) FOR [ParticipationId]
GO

ALTER TABLE [dbo].[Participations]  WITH CHECK ADD  CONSTRAINT [FK_Participations_Particuliers_ParticulierId] FOREIGN KEY([ParticulierId])
REFERENCES [dbo].[Particuliers] ([Id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Participations] CHECK CONSTRAINT [FK_Participations_Particuliers_ParticulierId]
GO

ALTER TABLE [dbo].[Participations]  WITH CHECK ADD  CONSTRAINT [FK_Participations_Stickers_StickerId] FOREIGN KEY([StickerId])
REFERENCES [dbo].[Stickers] ([Id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Participations] CHECK CONSTRAINT [FK_Participations_Stickers_StickerId]
GO

