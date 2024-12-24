using System;
using System.Text.Json.Serialization;

namespace Bth4.Model
{
    public class OrderItem
    {
        public int Id { get; set; }

        public int OrderId { get; set; }

        public int ProductId { get; set; }

        public string ProductName { get; set; }

        public int Quantity { get; set; }

        public decimal UnitPrice { get; set; }

        public decimal TotalPrice { get; set; }

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow; // Giá trị mặc định

        public DateTime UpdatedAt { get; set; } = DateTime.UtcNow; // Giá trị mặc định

        [JsonIgnore]
        public virtual Order Order { get; set; } // Đánh dấu quan hệ với Order
    }
}