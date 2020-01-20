USE [pubeotest]
GO

/****** Object:  Table [dbo].[Particuliers]    Script Date: 30-12-19 02:48:45 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Particuliers](
	[Id] [uniqueidentifier] NOT NULL,
	[Nom] [nvarchar](100) NOT NULL,
	[Prenom] [nvarchar](100) NOT NULL,
	[Adresse] [nvarchar](300) NOT NULL,
	[Pseudo] [nvarchar](100) NOT NULL,
	[DateNaissance] [datetime2](7) NOT NULL,
	[NumeroTel] [nvarchar](max) NOT NULL,
	[Mail] [nvarchar](100) NOT NULL,
	[LocaliteCode] [nvarchar](450) NULL,
 CONSTRAINT [PK_Particuliers] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[Particuliers] ADD  CONSTRAINT [DF_Particuliers_Id]  DEFAULT (newsequentialid()) FOR [Id]
GO

ALTER TABLE [dbo].[Particuliers]  WITH CHECK ADD  CONSTRAINT [FK_Particuliers_Localites_LocaliteCode] FOREIGN KEY([LocaliteCode])
REFERENCES [dbo].[Localites] ([CodePostal])
GO

ALTER TABLE [dbo].[Particuliers] CHECK CONSTRAINT [FK_Particuliers_Localites_LocaliteCode]
GO

