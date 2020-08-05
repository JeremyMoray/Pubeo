using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace PubeoAPI.Migrations
{
    public partial class APIv1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "RowVersion",
                table: "Professionnels");

            migrationBuilder.AddColumn<string>(
                name: "MotDePasse",
                table: "Particuliers",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "MotDePasse",
                table: "Particuliers");

            migrationBuilder.AddColumn<byte[]>(
                name: "RowVersion",
                table: "Professionnels",
                type: "rowversion",
                rowVersion: true,
                nullable: true);
        }
    }
}
