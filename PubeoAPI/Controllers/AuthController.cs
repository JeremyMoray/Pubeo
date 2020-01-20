using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class AuthController : ControllerBase {
        private string _base;
        private readonly PubeoAPIdbContext _context;
        private readonly UserManager<Professionnel> _userManager;
        private readonly SignInManager<Professionnel> _signInManager;

        public AuthController(IConfiguration configuration, PubeoAPIdbContext context, UserManager<Professionnel> userManager, SignInManager<Professionnel> signInManager)
        {
            _base = configuration["Uri:Base"];
            _context = context;
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpPost]
        public async Task<IActionResult> Register([FromBody]Professionnel professionnel)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var newUser = new Professionnel{
                // UserName = professionnel.NomEntreprise,
                // Email = professionnel.Mail,
                Adresse = professionnel.Adresse,
                NumeroTel = professionnel.NumeroTel,
                NumeroTVA = professionnel.NumeroTVA,
                LocaliteCode = professionnel?.LocaliteCode
            };
            var result = await _userManager.CreateAsync(newUser, professionnel.MotDePasse);
            StringBuilder uri = new StringBuilder(_base)
                .Append("/Professionnels/")
                .Append(professionnel.NomEntreprise);

            if(result.Succeeded)
            {
                // await _signInManager.SignInAsync(newUser, true);
                return Created(new Uri(uri.ToString()), newUser);
            }
            else {
                return BadRequest();
            }
        }
    }
}