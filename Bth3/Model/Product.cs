using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Bth3.Models
{
    [Table("products")]
    public class Product
    {
        [Key]
        public int ProductId { get; set; }

        [Required]
        [StringLength(255)]
        public string ProductName { get; set; }

        public string ProductDescription { get; set; }

        [Required]
        public decimal ProductPrice { get; set; }

        [Required]
        public int ProductQuantity { get; set; }

        [Column("created_at")]
        public DateTime CreatedDate { get; set; }

        [Column("updated_at")]
        public DateTime UpdatedDate { get; set; }
    }
}
