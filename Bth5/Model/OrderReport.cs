using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Bth5.Models
{
    [Table("order_reports")]
    public class OrderReport
    {
        [Key]
        public int Identifier { get; set; }

        public int OrderIdentifier { get; set; }

        public decimal Revenue { get; set; }

        public decimal Cost { get; set; }

        public decimal Profit { get; set; }
    }
}