using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Bth5.Models
{
    [Table("reports_products")]
    public class ProductReport
    {
        public int Identifier { get; set; }
        public int OrderReportIdentifier { get; set; }
        public int ProductIdentifier { get; set; }
        public int QuantitySold { get; set; }
        public decimal Revenue { get; set; }
        public decimal Cost { get; set; }
        public decimal Profit { get; set; }
    }
}