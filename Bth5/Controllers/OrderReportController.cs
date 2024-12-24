using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Bth5.Database;
using Bth5.Models;

namespace Bth5.Controllers
{
    [Authorize]
    [Route("api/reports/orders")]
    [ApiController]
    public class OrderReportController : ControllerBase
    {
        private readonly AppDbContext _context;

        public OrderReportController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetAllOrderReport()
        {
            var reports = _context.OrderReport.ToList();
            return Ok(reports);
        }

        [HttpGet("{id}")]
        public IActionResult GetOrderReportById(int id)
        {
            var report = _context.OrderReport.FirstOrDefault(o => o.Identifier == id);
            if (report == null)
                return NotFound();
            return Ok(report);
        }

        [HttpPost]
        public IActionResult CreateOrderReport([FromBody] OrderReport report)
        {
            if (report == null)
                return BadRequest();

            report.Profit = report.Revenue - report.Cost;
            _context.OrderReport.Add(report);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetOrderReportById), new { id = report.Identifier }, report);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteOrderReport(int id)
        {
            var report = _context.OrderReport.FirstOrDefault(o => o.Identifier == id);
            if (report == null)
                return NotFound();

            _context.OrderReport.Remove(report);
            _context.SaveChanges();
            return NoContent();
        }
    }
}
