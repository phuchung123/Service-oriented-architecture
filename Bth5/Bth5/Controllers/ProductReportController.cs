using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Bth5.Database;
using Bth5.Models;

namespace Bth5.Controllers
{
    [Authorize]
    [Route("api/reports/products")]
    [ApiController]
    public class ProductReportController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ProductReportController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetAllProductReports()
        {
            var reports = _context.ProductReport.ToList();
            return Ok(reports);
        }

        [HttpGet("{id}")]
        public IActionResult GetProductReportById(int id)
        {
            var report = _context.ProductReport.FirstOrDefault(p => p.Identifier == id);
            if (report == null)
                return NotFound();
            return Ok(report);
        }

        [HttpPost]
        public IActionResult CreateProductReport([FromBody] ProductReport report)
        {
            if (report == null)
                return BadRequest();

            report.Profit = report.Revenue - report.Cost;
            _context.ProductReport.Add(report);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetProductReportById), new { id = report.Identifier }, report);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteProductReport(int id)
        {
            var report = _context.ProductReport.FirstOrDefault(p => p.Identifier == id);
            if (report == null)
                return NotFound();

            _context.ProductReport.Remove(report);
            _context.SaveChanges();
            return NoContent();
        }
    }
}
