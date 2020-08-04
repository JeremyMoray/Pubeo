using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace PubeoAPI.Migrations
{
    public partial class concurrency : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<byte[]>(
                name: "RowVersion",
                table: "Professionnels",
                rowVersion: true,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "RowVersion",
                table: "Professionnels");
        }
    }
}
