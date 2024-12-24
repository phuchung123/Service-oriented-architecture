using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace Bth2.Middleware;

public class Jwt
{
    private readonly RequestDelegate _next;

    public Jwt(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        var token = context.Request.Headers["Authorization"].FirstOrDefault()?.Split(" ").Last();

        if (!string.IsNullOrEmpty(token))
        {
            try
            {
                // Decode token and extract information
                var tokenHandler = new JwtSecurityTokenHandler();
                var jwtToken = tokenHandler.ReadToken(token) as JwtSecurityToken;
                if (jwtToken != null)
                {
                    var userId = jwtToken.Claims.FirstOrDefault(claim => claim.Type == ClaimTypes.Name)?.Value;
                    if (userId != null)
                    {
                        context.Items["User"] = userId;
                    }
                }
            }
            catch
            {
                // Do nothing if token is invalid
            }
        }

        await _next(context);
    }
}
