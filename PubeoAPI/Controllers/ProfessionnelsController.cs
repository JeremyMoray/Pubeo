using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using AutoMapper;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;
using Scrypt;

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("[controller]")]
    public class ProfessionnelsController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;
        private readonly IMapper mapper;

        public ProfessionnelsController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        [Authorize(Roles = "admin")]
        [HttpGet("GetCount")]
        public async Task<IActionResult> getCount()
        {
            return Ok(await _context.Professionnels.CountAsync());
        }

        // GET : /Professionnels
        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var professionnelsDetails = await _context.Professionnels
                                            .Include(x => x.Localite)
                                            .Include(x => x.Stickers)
                                            .ToListAsync();
            var professionnels = mapper.Map<List<ProfessionnelsSimpleDTO>> (professionnelsDetails);
            return Ok(professionnels);
        }

        // GET: /Professionnels/GetMe
        [HttpGet("GetMe")]
        public async Task<IActionResult> GetMe()
        {
            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;
            var professionnel = await _context.Professionnels
                                    .Include(x => x.Localite)
                                    .Include(x => x.Stickers)
                                    .SingleOrDefaultAsync(x => x.Mail == email);

            if(professionnel == null) 
                return NotFound();

            return Ok(mapper.Map<ProfessionnelsDTO> (professionnel));
        }

        // GET: /Professionnels/{id}
        [HttpGet("{id}")]
        public async Task<IActionResult> GetProfessionnel([FromRoute] Guid id)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var professionnel = await _context.Professionnels
                                    .Include(x => x.Localite)
                                    .Include(x => x.Stickers)
                                    .SingleOrDefaultAsync(x => x.Id == id);

            if(professionnel == null) 
                return NotFound();

            return Ok(mapper.Map<ProfessionnelsDTO> (professionnel));
        }

        // GET : /Professionnels/GetByNomEntreprise/{companyname}
        [HttpGet("GetByNomEntreprise/{nomentreprise}")]
        public async Task<IActionResult> GetByNomEntreprise([FromRoute] string nomentreprise)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var professionnel = await _context.Professionnels
                                        .Include(x => x.Localite)
                                        .Include(x => x.Stickers)
                                        .SingleOrDefaultAsync(m => m.NomEntreprise.Equals(nomentreprise));

            if (professionnel == null){
                return NotFound();
            }
            else 
            {
                var user = mapper.Map<ProfessionnelsDTO> (professionnel);

                return Ok(user);
            }
        }

        // POST: /Professionnels
        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> Create([FromBody] Professionnel professionnel)
        {
            ScryptEncoder encoder = new ScryptEncoder();

            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(await _context.Professionnels.AnyAsync(x => x.Mail == professionnel.Mail))
                return Conflict("Mail");

            if(await _context.Professionnels.AnyAsync(x => x.NomEntreprise == professionnel.NomEntreprise))
                return Conflict("NomEntreprise");

            if(professionnel.LocaliteCode != null && !await _context.Localites.AnyAsync(x => x.CodePostal.Equals(professionnel.LocaliteCode)))
                return NotFound();

            var pro = new Professionnel{
                NomEntreprise = professionnel.NomEntreprise,
                Mail = professionnel.Mail,
                Adresse = professionnel.Adresse,
                NumeroTel = professionnel.NumeroTel,
                MotDePasse = encoder.Encode(professionnel.MotDePasse),
                NumeroTVA = professionnel.NumeroTVA,
                LocaliteCode = professionnel?.LocaliteCode
            };
            await _context.Professionnels.AddAsync(pro);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetProfessionnel", new { id = pro.Id }, pro);
        }

        // PUT: /Professionnels/{id}
        [Authorize(Roles = "admin")]
        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromRoute] Guid id, [FromBody] ProfessionnelsUpdateDTO professionnel)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(!ProfessionnelExists(id))
                    return NotFound();
            
            if(await _context.Professionnels.AnyAsync(x => x.Mail == professionnel.Mail && x.Id != id))
                return Conflict("Mail");

            if(await _context.Professionnels.AnyAsync(x => x.NomEntreprise == professionnel.NomEntreprise && x.Id != id))
                return Conflict("NomEntreprise");

            if(professionnel.LocaliteCode != null && !await _context.Localites.AnyAsync(x => x.CodePostal.Equals(professionnel.LocaliteCode)))
                return NotFound();

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Id.Equals(id));

            user = Modification(user, professionnel);
            _context.Entry(user).State = EntityState.Modified;

            try {
                await _context.SaveChangesAsync();
            }
            catch(DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // PUT: /Professionnels/UpdateMyAccount
        [HttpPut("UpdateMyAccount")]
        public async Task<IActionResult> UpdateMyAccount([FromBody] ProfessionnelsUpdateDTO professionnel)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                    return NotFound();

            if(await _context.Professionnels.AnyAsync(x => x.Mail == professionnel.Mail && x.Id != user.Id))
                return Conflict("Mail");

            if(await _context.Professionnels.AnyAsync(x => x.NomEntreprise == professionnel.NomEntreprise && x.Id != user.Id))
                return Conflict("NomEntreprise");

            if(professionnel.LocaliteCode != null && !await _context.Localites.AnyAsync(x => x.CodePostal.Equals(professionnel.LocaliteCode)))
                return NotFound();

            user = Modification(user, professionnel);
            _context.Entry(user).State = EntityState.Modified;

            try {
                await _context.SaveChangesAsync();
            }
            catch(DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // DELETE: /Professionnels/{id}
        [HttpDelete("{id}")]
        [Authorize(Roles = "admin")]
        public async Task<IActionResult> Delete([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Id.Equals(id));
            if (user == null)
                return NotFound();

            _context.Professionnels.Remove(user);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // DELETE: /Professionnels/DeleteMyAccount
        [HttpDelete("DeleteMyAccount")]
        public async Task<IActionResult> DeleteMyAccount()
        {
            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Mail.Equals(email));
            if (user == null)
                return NotFound();

            _context.Professionnels.Remove(user);
            await _context.SaveChangesAsync();
            return Ok();
        }

        private bool ProfessionnelExists(Guid id)
        {
            return _context.Professionnels.Any(p => p.Id.Equals(id));
        }

        private Professionnel Modification(Professionnel initialPro, ProfessionnelsUpdateDTO targetPro){
            ScryptEncoder encoder = new ScryptEncoder();
            var retour = initialPro;
            if(targetPro.NomEntreprise != null) retour.NomEntreprise = targetPro.NomEntreprise;
            if(targetPro.Adresse != null) retour.Adresse = targetPro.Adresse;
            if(targetPro.MotDePasse != null) retour.MotDePasse = encoder.Encode(targetPro.MotDePasse);
            if(targetPro.NumeroTel != null) retour.NumeroTel = targetPro.NumeroTel;
            if(targetPro.Mail != null) retour.Mail = targetPro.Mail;
            if(targetPro.NumeroTVA != null) retour.NumeroTVA = targetPro.NumeroTVA;
            if(targetPro.LocaliteCode != null) retour.LocaliteCode = targetPro.LocaliteCode;
            return retour;
        }
    }

}