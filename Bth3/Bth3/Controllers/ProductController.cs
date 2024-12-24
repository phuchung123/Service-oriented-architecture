using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Bth3.Database;
using Bth3.Models;

namespace Bth3.Controllers
{
    [Route("api/products")]
    [ApiController]
    [Authorize]
    public class ProductController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ProductController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetAll()
        {
            var allProducts = _context.Products.ToList();
            return Ok(allProducts);
        }

        [HttpGet("{id:int}")]
        public IActionResult GetById(int id)
        {
            var product = _context.Products.Find(id);
            return product is null ? NotFound() : Ok(product);
        }

        [HttpPost]
        public IActionResult Create([FromBody] Product newProduct)
        {
            if (newProduct == null)
                return BadRequest("Product data is required.");

            newProduct.CreatedDate = DateTime.UtcNow;
            newProduct.UpdatedDate = DateTime.UtcNow;

            _context.Products.Add(newProduct);
            _context.SaveChanges();

            return CreatedAtAction(nameof(GetById), new { id = newProduct.ProductId }, newProduct);
        }

        [HttpPut("{id:int}")]
        public IActionResult Update(int id, [FromBody] Product updatedProduct)
        {
            var existingProduct = _context.Products.Find(id);
            if (existingProduct == null) return NotFound();

            existingProduct.ProductName = updatedProduct.ProductName;
            existingProduct.ProductDescription = updatedProduct.ProductDescription;
            existingProduct.ProductPrice = updatedProduct.ProductPrice;
            existingProduct.ProductQuantity = updatedProduct.ProductQuantity;
            existingProduct.UpdatedDate = DateTime.UtcNow;

            _context.SaveChanges();
            return Ok(existingProduct);
        }

        [HttpDelete("{id:int}")]
        public IActionResult Delete(int id)
        {
            var product = _context.Products.Find(id);
            if (product == null) return NotFound();

            _context.Products.Remove(product);
            _context.SaveChanges();

            return NoContent();
        }
    }
}