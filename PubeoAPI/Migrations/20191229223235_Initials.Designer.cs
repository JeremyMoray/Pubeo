﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using PubeoAPI;

namespace PubeoAPI.Migrations
{
    [DbContext(typeof(PubeoAPIdbContext))]
    [Migration("20191229223235_Initials")]
    partial class Initials
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.1.0")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("PubeoAPI.model.AppartenanceVehicule", b =>
                {
                    b.Property<Guid>("AppartenanceId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Marque")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Modele")
                        .HasColumnType("nvarchar(450)");

                    b.Property<Guid>("ParticulierId")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("AppartenanceId");

                    b.HasIndex("ParticulierId");

                    b.HasIndex("Marque", "Modele");

                    b.ToTable("AppartenanceVehicules");
                });

            modelBuilder.Entity("PubeoAPI.model.Localite", b =>
                {
                    b.Property<string>("CodePostal")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Ville")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("CodePostal");

                    b.ToTable("Localites");
                });

            modelBuilder.Entity("PubeoAPI.model.Participation", b =>
                {
                    b.Property<Guid>("ParticipationId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("ParticulierId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("StickerId")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("ParticipationId");

                    b.HasIndex("ParticulierId");

                    b.HasIndex("StickerId");

                    b.ToTable("Participations");
                });

            modelBuilder.Entity("PubeoAPI.model.Particulier", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Adresse")
                        .HasColumnType("nvarchar(300)")
                        .HasMaxLength(300);

                    b.Property<DateTime>("DateNaissance")
                        .HasColumnType("datetime2");

                    b.Property<string>("LocaliteCode")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Mail")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("Nom")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("NumeroTel")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Prenom")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("Pseudo")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.HasKey("Id");

                    b.HasIndex("LocaliteCode");

                    b.ToTable("Particuliers");
                });

            modelBuilder.Entity("PubeoAPI.model.Professionnel", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Adresse")
                        .HasColumnType("nvarchar(300)")
                        .HasMaxLength(300);

                    b.Property<string>("LocaliteCode")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Mail")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("MotDePasse")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("NomEntreprise")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("NumeroTVA")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("NumeroTel")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.HasIndex("LocaliteCode");

                    b.ToTable("Professionnels");
                });

            modelBuilder.Entity("PubeoAPI.model.Sticker", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Description")
                        .HasColumnType("nvarchar(300)")
                        .HasMaxLength(300);

                    b.Property<int>("Hauteur")
                        .HasColumnType("int");

                    b.Property<int>("Largeur")
                        .HasColumnType("int");

                    b.Property<int>("NbUtilisationsRestantes")
                        .HasColumnType("int");

                    b.Property<Guid>("ProfessionnelId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Titre")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.HasIndex("ProfessionnelId");

                    b.ToTable("Stickers");
                });

            modelBuilder.Entity("PubeoAPI.model.Vehicule", b =>
                {
                    b.Property<string>("Marque")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Modele")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Marque", "Modele");

                    b.ToTable("Vehicules");
                });

            modelBuilder.Entity("PubeoAPI.model.AppartenanceVehicule", b =>
                {
                    b.HasOne("PubeoAPI.model.Particulier", "Particulier")
                        .WithMany("AppartenanceVehicules")
                        .HasForeignKey("ParticulierId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PubeoAPI.model.Vehicule", "Vehicule")
                        .WithMany("AppartenanceVehicules")
                        .HasForeignKey("Marque", "Modele");
                });

            modelBuilder.Entity("PubeoAPI.model.Participation", b =>
                {
                    b.HasOne("PubeoAPI.model.Particulier", "Particulier")
                        .WithMany("Participations")
                        .HasForeignKey("ParticulierId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PubeoAPI.model.Sticker", "Sticker")
                        .WithMany("Participations")
                        .HasForeignKey("StickerId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("PubeoAPI.model.Particulier", b =>
                {
                    b.HasOne("PubeoAPI.model.Localite", "Localite")
                        .WithMany("Particuliers")
                        .HasForeignKey("LocaliteCode");
                });

            modelBuilder.Entity("PubeoAPI.model.Professionnel", b =>
                {
                    b.HasOne("PubeoAPI.model.Localite", "Localite")
                        .WithMany("Professionnels")
                        .HasForeignKey("LocaliteCode");
                });

            modelBuilder.Entity("PubeoAPI.model.Sticker", b =>
                {
                    b.HasOne("PubeoAPI.model.Professionnel", "Professionnel")
                        .WithMany("Stickers")
                        .HasForeignKey("ProfessionnelId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });
#pragma warning restore 612, 618
        }
    }
}