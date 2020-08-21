using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace PubeoAPI.Migrations
{
    public partial class Pubeov134 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<byte[]>(
                name: "RowVersion",
                table: "Vehicules",
                rowVersion: true,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "RowVersion",
                table: "Vehicules");
        }
    }
}
