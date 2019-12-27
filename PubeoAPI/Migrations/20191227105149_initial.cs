using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace PubeoAPI.Migrations
{
    public partial class initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Localites",
                columns: table => new
                {
                    CodePostal = table.Column<string>(nullable: false),
                    Ville = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Localites", x => x.CodePostal);
                });

            migrationBuilder.CreateTable(
                name: "Vehicules",
                columns: table => new
                {
                    Marque = table.Column<string>(nullable: false),
                    Modele = table.Column<string>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Vehicules", x => new { x.Marque, x.Modele });
                });

            migrationBuilder.CreateTable(
                name: "Particuliers",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nom = table.Column<string>(maxLength: 100, nullable: true),
                    Prenom = table.Column<string>(maxLength: 100, nullable: true),
                    Adresse = table.Column<string>(maxLength: 300, nullable: true),
                    Pseudo = table.Column<string>(maxLength: 100, nullable: true),
                    DateNaissance = table.Column<DateTime>(nullable: false),
                    NumeroTel = table.Column<string>(nullable: true),
                    Mail = table.Column<string>(maxLength: 100, nullable: true),
                    LocaliteCode = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Particuliers", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Particuliers_Localites_LocaliteCode",
                        column: x => x.LocaliteCode,
                        principalTable: "Localites",
                        principalColumn: "CodePostal",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Professionnels",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomEntreprise = table.Column<string>(maxLength: 100, nullable: true),
                    Adresse = table.Column<string>(maxLength: 300, nullable: true),
                    NumeroTel = table.Column<string>(nullable: true),
                    MotDePasse = table.Column<string>(nullable: true),
                    Mail = table.Column<string>(maxLength: 100, nullable: true),
                    NumeroTVA = table.Column<string>(nullable: true),
                    LocaliteCode = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Professionnels", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Professionnels_Localites_LocaliteCode",
                        column: x => x.LocaliteCode,
                        principalTable: "Localites",
                        principalColumn: "CodePostal",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "AppartenanceVehicules",
                columns: table => new
                {
                    AppartenanceId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ParticulierId = table.Column<int>(nullable: false),
                    Marque = table.Column<string>(nullable: true),
                    Modele = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AppartenanceVehicules", x => x.AppartenanceId);
                    table.ForeignKey(
                        name: "FK_AppartenanceVehicules_Particuliers_ParticulierId",
                        column: x => x.ParticulierId,
                        principalTable: "Particuliers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_AppartenanceVehicules_Vehicules_Marque_Modele",
                        columns: x => new { x.Marque, x.Modele },
                        principalTable: "Vehicules",
                        principalColumns: new[] { "Marque", "Modele" },
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Stickers",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Titre = table.Column<string>(nullable: true),
                    Description = table.Column<string>(maxLength: 300, nullable: true),
                    Hauteur = table.Column<int>(nullable: false),
                    Largeur = table.Column<int>(nullable: false),
                    NbUtilisationsRestantes = table.Column<int>(nullable: false),
                    ProfessionnelId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Stickers", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Stickers_Professionnels_ProfessionnelId",
                        column: x => x.ProfessionnelId,
                        principalTable: "Professionnels",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Participations",
                columns: table => new
                {
                    ParticipationId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ParticulierId = table.Column<int>(nullable: false),
                    StickerId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Participations", x => x.ParticipationId);
                    table.ForeignKey(
                        name: "FK_Participations_Particuliers_ParticulierId",
                        column: x => x.ParticulierId,
                        principalTable: "Particuliers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Participations_Stickers_StickerId",
                        column: x => x.StickerId,
                        principalTable: "Stickers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_AppartenanceVehicules_ParticulierId",
                table: "AppartenanceVehicules",
                column: "ParticulierId");

            migrationBuilder.CreateIndex(
                name: "IX_AppartenanceVehicules_Marque_Modele",
                table: "AppartenanceVehicules",
                columns: new[] { "Marque", "Modele" });

            migrationBuilder.CreateIndex(
                name: "IX_Participations_ParticulierId",
                table: "Participations",
                column: "ParticulierId");

            migrationBuilder.CreateIndex(
                name: "IX_Participations_StickerId",
                table: "Participations",
                column: "StickerId");

            migrationBuilder.CreateIndex(
                name: "IX_Particuliers_LocaliteCode",
                table: "Particuliers",
                column: "LocaliteCode");

            migrationBuilder.CreateIndex(
                name: "IX_Professionnels_LocaliteCode",
                table: "Professionnels",
                column: "LocaliteCode");

            migrationBuilder.CreateIndex(
                name: "IX_Stickers_ProfessionnelId",
                table: "Stickers",
                column: "ProfessionnelId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "AppartenanceVehicules");

            migrationBuilder.DropTable(
                name: "Participations");

            migrationBuilder.DropTable(
                name: "Vehicules");

            migrationBuilder.DropTable(
                name: "Particuliers");

            migrationBuilder.DropTable(
                name: "Stickers");

            migrationBuilder.DropTable(
                name: "Professionnels");

            migrationBuilder.DropTable(
                name: "Localites");
        }
    }
}
