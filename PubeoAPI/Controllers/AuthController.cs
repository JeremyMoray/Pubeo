using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using AutoMapper;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;
using PubeoAPI.model;
using PubeoAPI.model.auth;
using PubeoAPI.Resources;
using securityJWT.Options;

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("v{version:apiVersion}/[controller]")]
    [ApiVersion("1")]
    [ApiVersion("2")]
    public class AuthController : ControllerBase {

        private readonly IMapper _mapper;
        private readonly UserManager<User> _userManager;
        private readonly RoleManager<Role> _roleManager;
        private readonly JwtIssuerOptions _jwtOptions;

        public AuthController(IMapper mapper, UserManager<User> userManager, RoleManager<Role> roleManager, IOptions<JwtIssuerOptions> jwtOptions)
        {
            _mapper = mapper;
            _userManager = userManager;
            _roleManager = roleManager;
            _jwtOptions = jwtOptions.Value;
        }

        [Authorize(Roles = "admin")]
        [Route("TestSession")]
        [HttpGet]
        public bool TestSession()
        {
            return true;
        }

        [Authorize(Roles = "admin")]
        [HttpGet("AllMemberList")]
        public IEnumerable<User> GetAllMember()
        {
            return _userManager.Users;
        }

        [Authorize(Roles = "admin")]
        [HttpPost("AddMember")]
        public async Task<IActionResult> AddMember(AddUserResource userResource)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var user = _mapper.Map<AddUserResource, User>(userResource);

            var createResult = await _userManager.CreateAsync(user, userResource.Password);

            if(createResult.Succeeded) return Created(string.Empty, string.Empty);

            return Problem(createResult.Errors.First().Description, null, 500);
        }

        [AllowAnonymous]
        [HttpPost("LogIn")]
        public async Task<IActionResult> LogIn(UserLoginResource userResource)
        {
            var user = _userManager.Users.SingleOrDefault(u => u.UserName == userResource.Email);
            if(user is null)
            {
                return NotFound("User not found");
            }

            var LoginResult = await _userManager.CheckPasswordAsync(user, userResource.Password);

            if (LoginResult)
            {
                //return Ok();
                var roles = await _userManager.GetRolesAsync(user);
                return Ok(GenerateToken(user, roles));
            }

            return BadRequest("Email or password incorrect.");        
        }

        [Authorize(Roles = "admin")]
        [HttpPost("AddRoles")]
        public async Task<IActionResult> CreateRole([FromBody] string roleName)
        {
            if(string.IsNullOrWhiteSpace(roleName))
            {
                return BadRequest("Role name should be provided.");
            }

            var newRole = new Role
            {
                Name = roleName
            };

            var roleResult = await _roleManager.CreateAsync(newRole);

            if (roleResult.Succeeded)
            {
                return Ok();
            }

            return Problem(roleResult.Errors.First().Description, null, 500);                
        }

        [Authorize(Roles = "admin")]
        [HttpPost("User/{userEmail}/Role")]
        public async Task<IActionResult> AddMemberToRole(string userEmail, [FromBody] string roleName)
        {
            var user = _userManager.Users.SingleOrDefault(u => u.UserName == userEmail);

            var result = await _userManager.AddToRoleAsync(user, roleName);

            if (result.Succeeded)
            {
                return Ok();
            }

            return Problem(result.Errors.First().Description, null, 500);
        }


        private async Task<string> GenerateToken(User user, IList<string> roles)
        {
            var claims = new List<Claim>
            {
                new Claim(JwtRegisteredClaimNames.Sub, user.Id.ToString()),
                new Claim(ClaimTypes.Name, user.UserName),
                new Claim(JwtRegisteredClaimNames.Jti, await _jwtOptions.JtiGenerator()),
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, 
                        ToUnixEpochDate(_jwtOptions.IssuedAt).ToString(), 
                        ClaimValueTypes.Integer64),
            };

            var roleClaims = roles.Select(r => new Claim(ClaimTypes.Role, r));
            claims.AddRange(roleClaims);

            JwtSecurityToken jwt = new JwtSecurityToken(
                issuer: _jwtOptions.Issuer,
                audience: _jwtOptions.Audience,
                claims: claims,
                notBefore: _jwtOptions.NotBefore,
                expires: _jwtOptions.Expiration,
                signingCredentials: _jwtOptions.SigningCredentials
            );

            return new JwtSecurityTokenHandler().WriteToken(jwt);
        }

        private static long ToUnixEpochDate(DateTime date)
            => (long)Math.Round((date.ToUniversalTime() - 
                                new DateTimeOffset(1970, 1, 1, 0, 0, 0, TimeSpan.Zero))
                                .TotalSeconds);      

    }
}