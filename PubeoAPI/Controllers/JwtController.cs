using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using PubeoAPI.model;
using securityJWT.Options;

namespace PubeoAPI.Controllers
{
    [AllowAnonymous]
    [Route("[controller]")]
    [ApiController]
    public class JwtController : Controller
    {
        private readonly JwtIssuerOptions _jwtOptions;
        private readonly PubeoAPIdbContext _context;

        public JwtController(IOptions<JwtIssuerOptions> jwtOptions, PubeoAPIdbContext context)
        {
            _jwtOptions = jwtOptions.Value;
            _context = context;
        }        

        [HttpPost]
        public async Task<IActionResult> Login([FromBody] DTO.LoginDTO loginDTO)
        {
            if(!ModelState.IsValid) return BadRequest(ModelState);

            var authProfessionnels = _context.Professionnels;
            Professionnel professionnelFound = authProfessionnels.FirstOrDefault(pro => pro.Mail == loginDTO.Mail && pro.MotDePasse == loginDTO.MotDePasse);

            if(professionnelFound == null) {
                return Unauthorized();
            }
            
            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, professionnelFound.Mail),
                new Claim(JwtRegisteredClaimNames.Jti, await _jwtOptions.JtiGenerator()),
                new Claim(JwtRegisteredClaimNames.Iat, 
                        ToUnixEpochDate(_jwtOptions.IssuedAt).ToString(), 
                        ClaimValueTypes.Integer64),
            };

            // Create the JWT security token and encode it.
            JwtSecurityToken jwt = new JwtSecurityToken(
                issuer: _jwtOptions.Issuer,
                audience: _jwtOptions.Audience,
                claims: claims,
                notBefore: _jwtOptions.NotBefore,
                expires: _jwtOptions.Expiration,
                signingCredentials: _jwtOptions.SigningCredentials
            );
            var encodedJwt = new JwtSecurityTokenHandler().WriteToken(jwt);

            
            // Serialize and return the response
            var response = new
            {
                access_token = encodedJwt,
                expires_in = (int)_jwtOptions.ValidFor.TotalSeconds
            };

            return Ok(response);
        }

        /// <returns>Date converted to seconds since Unix epoch (Jan 1, 1970, midnight UTC).</returns>
        private static long ToUnixEpochDate(DateTime date)
            => (long)Math.Round((date.ToUniversalTime() - 
                                new DateTimeOffset(1970, 1, 1, 0, 0, 0, TimeSpan.Zero))
                                .TotalSeconds);           
    }    
}