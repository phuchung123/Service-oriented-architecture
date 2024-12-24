using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Bth4.Model
{
    public class Order
    {
        [Key] // Đánh dấu Id là khóa chính
        public int Id { get; set; }

        [Required] // Thuộc tính bắt buộc
        public string CustomerName { get; set; }

        [EmailAddress] // Kiểm tra định dạng email
        public string CustomerEmail { get; set; }

        [Range(0, double.MaxValue)] // Giới hạn giá trị của TotalAmount
        public decimal TotalAmount { get; set; }

        public string Status { get; set; }

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow; // Giá trị mặc định

        public DateTime UpdatedAt { get; set; } = DateTime.UtcNow; // Giá trị mặc định

        public virtual ICollection<OrderItem> OrderItems { get; set; } = new List<OrderItem>(); // Khởi tạo danh sách
    }
}