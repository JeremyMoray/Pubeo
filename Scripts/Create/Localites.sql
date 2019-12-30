USE [pubeotest]
GO

/****** Object:  Table [dbo].[Localites]    Script Date: 30-12-19 02:46:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Localites](
	[CodePostal] [nvarchar](450) NOT NULL,
	[Ville] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Localites] PRIMARY KEY CLUSTERED 
(
	[CodePostal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

