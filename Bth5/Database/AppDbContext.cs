using Microsoft.EntityFrameworkCore;
using Bth5.Models;

namespace Bth5.Database
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }

        public DbSet<OrderReport> OrderReport { get; set; }
        public DbSet<ProductReport> ProductReport { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Ánh xạ bảng order_reports
            modelBuilder.Entity<OrderReport>(entity =>
            {
                entity.Property(e => e.OrderIdentifier).HasColumnName("order_id");
                entity.Property(e => e.Revenue).HasColumnName("total_revenue");
                entity.Property(e => e.Cost).HasColumnName("total_cost");
                entity.Property(e => e.Profit).HasColumnName("total_profit");
            });

            modelBuilder.Entity<ProductReport>(entity =>
            {
                entity.Property(e => e.ProductIdentifier).HasColumnName("product_id");
                entity.Property(e => e.QuantitySold).HasColumnName("total_sold");
                entity.Property(e => e.OrderReportIdentifier).HasColumnName("order_report_id");
                entity.Property(e => e.Cost).HasColumnName("cost");
                entity.Property(e => e.Revenue).HasColumnName("revenue");
                entity.Property(e => e.Profit).HasColumnName("profit");
            });
        }
    }
}