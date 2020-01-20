USE [pubeotest]
GO

/****** Object:  Table [dbo].[Professionnels]    Script Date: 30-12-19 02:48:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Professionnels](
	[Id] [uniqueidentifier] NOT NULL,
	[NomEntreprise] [nvarchar](100) NOT NULL,
	[Adresse] [nvarchar](300) NOT NULL,
	[NumeroTel] [nvarchar](max) NOT NULL,
	[MotDePasse] [nvarchar](max) NOT NULL,
	[Mail] [nvarchar](100) NOT NULL,
	[NumeroTVA] [nvarchar](max) NOT NULL,
	[LocaliteCode] [nvarchar](450) NULL,
 CONSTRAINT [PK_Professionnels] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[Professionnels] ADD  CONSTRAINT [DF_Professionnels_Id]  DEFAULT (newsequentialid()) FOR [Id]
GO

ALTER TABLE [dbo].[Professionnels]  WITH CHECK ADD  CONSTRAINT [FK_Professionnels_Localites_LocaliteCode] FOREIGN KEY([LocaliteCode])
REFERENCES [dbo].[Localites] ([CodePostal])
GO

ALTER TABLE [dbo].[Professionnels] CHECK CONSTRAINT [FK_Professionnels_Localites_LocaliteCode]
GO

