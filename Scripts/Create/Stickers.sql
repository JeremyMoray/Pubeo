USE [pubeotest]
GO

/****** Object:  Table [dbo].[Stickers]    Script Date: 30-12-19 02:48:29 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Stickers](
	[Id] [uniqueidentifier] NOT NULL,
	[Titre] [nvarchar](max) NOT NULL,
	[Description] [nvarchar](300) NULL,
	[Hauteur] [int] NOT NULL,
	[Largeur] [int] NOT NULL,
	[NbUtilisationsRestantes] [int] NOT NULL,
	[ProfessionnelId] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_Stickers] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[Stickers] ADD  CONSTRAINT [DF_Stickers_Id]  DEFAULT (newsequentialid()) FOR [Id]
GO

ALTER TABLE [dbo].[Stickers]  WITH CHECK ADD  CONSTRAINT [FK_Stickers_Professionnels_ProfessionnelId] FOREIGN KEY([ProfessionnelId])
REFERENCES [dbo].[Professionnels] ([Id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Stickers] CHECK CONSTRAINT [FK_Stickers_Professionnels_ProfessionnelId]
GO

