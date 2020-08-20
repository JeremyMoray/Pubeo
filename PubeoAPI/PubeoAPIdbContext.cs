using System;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.model;
using PubeoAPI.model.auth;

namespace PubeoAPI
{
    public class PubeoAPIdbContext : IdentityDbContext<User, Role, Guid> {

        public PubeoAPIdbContext()
        {}

        public PubeoAPIdbContext(DbContextOptions<PubeoAPIdbContext> options) : base(options)
        {
             this.Database.EnsureCreated();
        }

        public DbSet<Localite> Localites { get; set; }
        public DbSet<Participation> Participations { get; set; }
        public DbSet<Particulier> Particuliers { get; set; }
        public DbSet<Professionnel> Professionnels { get; set; }
        public DbSet<Sticker> Stickers { get; set; }
        public DbSet<Vehicule> Vehicules { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            
        }

        // protected override void OnModelCreating(ModelBuilder modelBuilder)
        // {
        //     modelBuilder.Entity<Vehicule>().HasKey(av => new { av.Marque, av.Modele });
        // }
    }
}
    