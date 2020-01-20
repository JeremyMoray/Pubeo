USE [pubeotest]
GO

/****** Object:  Table [dbo].[Vehicules]    Script Date: 30-12-19 02:49:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Vehicules](
	[Marque] [nvarchar](450) NOT NULL,
	[Modele] [nvarchar](450) NOT NULL,
 CONSTRAINT [PK_Vehicules] PRIMARY KEY CLUSTERED 
(
	[Marque] ASC,
	[Modele] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

