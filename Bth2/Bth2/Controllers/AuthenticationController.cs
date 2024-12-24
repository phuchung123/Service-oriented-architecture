﻿using Bth2.Database;
using Bth2.Model;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace Bth2.Controllers;

[Route("api/[controller]")]
[ApiController]
public class AuthenticationController : ControllerBase
{
    private readonly AppDbContext _context;
    private readonly string _jwtSecret;

    public AuthenticationController(AppDbContext context, IConfiguration configuration)
    {
        _context = context;
        _jwtSecret = configuration["Jwt:Secret"];
    }

    [HttpPost("login")]
    public IActionResult Login([FromBody] User user)
    {
        var dbUser = _context.Users.SingleOrDefault(u => u.UserName == user.UserName);
        if (dbUser == null) return Unauthorized("User not found");

        // Verify password
        using (var sha256 = SHA256.Create())
        {
            var hashedPassword = Convert.ToBase64String(sha256.ComputeHash(Encoding.UTF8.GetBytes(user.Password)));
            if (dbUser.Password != hashedPassword) return Unauthorized("Invalid password");
        }

        // Generate token
        var tokenHandler = new JwtSecurityTokenHandler();
        var key = Encoding.UTF8.GetBytes(_jwtSecret);
        var tokenDescriptor = new SecurityTokenDescriptor
        {
            Subject = new ClaimsIdentity(new[] { new Claim(ClaimTypes.Name, dbUser.IdUser.ToString()) }),
            Expires = DateTime.UtcNow.AddHours(1),
            SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
        };

        var token = tokenHandler.CreateToken(tokenDescriptor);
        dbUser.Token = tokenHandler.WriteToken(token);

        _context.SaveChanges();
        return Ok(new { Token = dbUser.Token });
    }

    [HttpPost("register")]
    public IActionResult Register([FromBody] User user)
    {
        if (_context.Users.Any(u => u.UserName == user.UserName))
        {
            return BadRequest("User already exists.");
        }

        using (var sha256 = SHA256.Create())
        {
            user.Password = Convert.ToBase64String(sha256.ComputeHash(Encoding.UTF8.GetBytes(user.Password)));
        }

        _context.Users.Add(user);
        _context.SaveChanges();

        return Ok("User registered successfully.");
    }
}
