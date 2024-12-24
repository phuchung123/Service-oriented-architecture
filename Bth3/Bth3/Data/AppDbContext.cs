﻿using Microsoft.EntityFrameworkCore;
using Bth3.Models;

namespace Bth3.Database
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Product> Products { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            ConfigureProductEntity(modelBuilder);
        }

        private void ConfigureProductEntity(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Product>()
                .Property(p => p.CreatedDate)
                .HasDefaultValueSql("GETDATE()");

            modelBuilder.Entity<Product>()
                .Property(p => p.UpdatedDate)
                .HasDefaultValueSql("GETDATE()");
        }
    }
}