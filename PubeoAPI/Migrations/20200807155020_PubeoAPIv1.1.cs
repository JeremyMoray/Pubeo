using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace PubeoAPI.Migrations
{
    public partial class PubeoAPIv11 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "AppartenanceVehicules");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Vehicules",
                table: "Vehicules");

            migrationBuilder.DropColumn(
                name: "RowVersion",
                table: "Professionnels");

            migrationBuilder.AlterColumn<string>(
                name: "Modele",
                table: "Vehicules",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(450)");

            migrationBuilder.AlterColumn<string>(
                name: "Marque",
                table: "Vehicules",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(450)");

            migrationBuilder.AddColumn<Guid>(
                name: "Id",
                table: "Vehicules",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AddColumn<Guid>(
                name: "ParticulierId",
                table: "Vehicules",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AddColumn<string>(
                name: "MotDePasse",
                table: "Particuliers",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Vehicules",
                table: "Vehicules",
                column: "Id");

            migrationBuilder.CreateIndex(
                name: "IX_Vehicules_ParticulierId",
                table: "Vehicules",
                column: "ParticulierId");

            migrationBuilder.AddForeignKey(
                name: "FK_Vehicules_Particuliers_ParticulierId",
                table: "Vehicules",
                column: "ParticulierId",
                principalTable: "Particuliers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Vehicules_Particuliers_ParticulierId",
                table: "Vehicules");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Vehicules",
                table: "Vehicules");

            migrationBuilder.DropIndex(
                name: "IX_Vehicules_ParticulierId",
                table: "Vehicules");

            migrationBuilder.DropColumn(
                name: "Id",
                table: "Vehicules");

            migrationBuilder.DropColumn(
                name: "ParticulierId",
                table: "Vehicules");

            migrationBuilder.DropColumn(
                name: "MotDePasse",
                table: "Particuliers");

            migrationBuilder.AlterColumn<string>(
                name: "Modele",
                table: "Vehicules",
                type: "nvarchar(450)",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Marque",
                table: "Vehicules",
                type: "nvarchar(450)",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AddColumn<byte[]>(
                name: "RowVersion",
                table: "Professionnels",
                type: "rowversion",
                rowVersion: true,
                nullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK_Vehicules",
                table: "Vehicules",
                columns: new[] { "Marque", "Modele" });

            migrationBuilder.CreateTable(
                name: "AppartenanceVehicules",
                columns: table => new
                {
                    AppartenanceId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Marque = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    Modele = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    ParticulierId = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
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

            migrationBuilder.CreateIndex(
                name: "IX_AppartenanceVehicules_ParticulierId",
                table: "AppartenanceVehicules",
                column: "ParticulierId");

            migrationBuilder.CreateIndex(
                name: "IX_AppartenanceVehicules_Marque_Modele",
                table: "AppartenanceVehicules",
                columns: new[] { "Marque", "Modele" });
        }
    }
}
