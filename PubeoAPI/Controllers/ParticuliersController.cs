using System;
using System.Collections.Generic;
using System.Linq;
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
    public class ParticuliersController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        private readonly IMapper mapper;

        public ParticuliersController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        [HttpGet("GetCount")]
        public async Task<IActionResult> getCount()
        {
            return Ok(await _context.Particuliers.CountAsync());
        }

        // GET : /Particuliers
        [HttpGet]
        public IEnumerable<ParticuliersDTO> GetParticuliers()
        {
            var particuliers = new HashSet<ParticuliersDTO>();
            foreach (var particulier in _context.Particuliers)
            {
                particuliers.Add(new ParticuliersDTO
                {
                    Id = particulier.Id,
                    Nom = particulier.Nom,
                    Prenom = particulier.Prenom,
                    Adresse = particulier.Adresse,
                    Pseudo = particulier.Pseudo,
                    DateNaissance = particulier.DateNaissance,
                    NumeroTel = particulier.NumeroTel,
                    Mail = particulier.Mail,
                    // Participations = GetParticipationsDTOs(particulier),
                });
            }
            return particuliers;
        }

        // GET: /Particuliers/GetMe
        [HttpGet("GetMe")]
        public async Task<IActionResult> GetMe()
        {
            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;
            var particulier = await _context.Particuliers
                                    .Include(x => x.Localite)
                                    .Include(x => x.Vehicules)
                                    .SingleOrDefaultAsync(x => x.Mail == email);

            if(particulier == null) 
                return NotFound();

            return Ok(mapper.Map<ParticuliersDTO> (particulier));
        }

        // GET : /Particuliers/{id}
        [HttpGet("{id}")]
        public async Task<IActionResult> GetById([FromRoute] Guid id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var _particulier = await _context.Particuliers
                                        .Include(p => p.Localite)
                                        .SingleOrDefaultAsync(p => p.Id.Equals(id));

            if(_particulier == null) {
                return NotFound();
            }
            return Ok(_particulier);
        }

        // GET : /Particuliers/AllParticuliers
        [Route("AllParticuliers")]
        public IEnumerable<Particulier> GetAllParticuliers()
        {
            var particuliers = _context.Particuliers
                                    .Include(x => x.Vehicules)
                                    .Include(x => x.Participations).ToList();
            
            return particuliers;
        }

        // PUT: /Particuliers/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateParticuliers([FromRoute] Guid id, [FromBody] Particulier particulier)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);
            
            if(!_context.Particuliers.Any(p => p.Id.Equals(id)))
                return NotFound();

            if(await _context.Particuliers.AnyAsync(x => x.Mail == particulier.Mail && x.Id != id))
                return Conflict("Mail");

            if(await _context.Particuliers.AnyAsync(x => x.Pseudo == particulier.Pseudo && x.Id != id))
                return Conflict("Pseudo");

            if(particulier.LocaliteCode != null && !await _context.Localites.AnyAsync(x => x.CodePostal.Equals(particulier.LocaliteCode)))
                return NotFound();

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Id.Equals(id));
            
            user = Modification(user, particulier);
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

        // POST: /Particuliers
        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> PostParticuliers([FromBody] Particulier particulier)
        {
            ScryptEncoder encoder = new ScryptEncoder();

            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if(await _context.Particuliers.AnyAsync(x => x.Mail == particulier.Mail))
                return Conflict("Mail");

            if(await _context.Particuliers.AnyAsync(x => x.Pseudo == particulier.Pseudo))
                return Conflict("Pseudo");

            if(particulier.LocaliteCode != null && !await _context.Localites.AnyAsync(x => x.CodePostal.Equals(particulier.LocaliteCode)))
                return NotFound();

            var part = new Particulier{
                Nom = particulier.Nom,
                Prenom = particulier.Prenom,
                Adresse = particulier.Adresse,
                Pseudo = particulier.Pseudo,
                DateNaissance = particulier.DateNaissance,
                NumeroTel = particulier.NumeroTel,
                Mail = particulier.Mail,
                MotDePasse = encoder.Encode(particulier.MotDePasse),
                LocaliteCode = particulier?.LocaliteCode
            };
            await _context.Particuliers.AddAsync(part);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetParticuliers", new { id = part.Id }, part);
        }

        //DELETE: /Particuliers/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteParticuliers([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Id.Equals(id));
            if (user == null)
            {
                return NotFound();
            }

            _context.Particuliers.Remove(user);
            await _context.SaveChangesAsync();
            return Ok(user);
        }

        private Particulier Modification(Particulier initialPart, Particulier targetPart){
            ScryptEncoder encoder = new ScryptEncoder();
            var retour = initialPart;
            if(targetPart.Prenom != null) retour.Prenom = targetPart.Prenom;
            if(targetPart.Nom != null) retour.Nom = targetPart.Nom;
            if(targetPart.Adresse != null) retour.Adresse = targetPart.Adresse;
            if(targetPart.Pseudo != null) retour.Pseudo = targetPart.Pseudo;
            if(targetPart.DateNaissance != null) retour.DateNaissance = targetPart.DateNaissance;
            if(targetPart.MotDePasse != null) retour.MotDePasse = encoder.Encode(targetPart.MotDePasse);
            if(targetPart.NumeroTel != null) retour.NumeroTel = targetPart.NumeroTel;
            if(targetPart.Mail != null) retour.Mail = targetPart.Mail;
            if(targetPart.LocaliteCode != null) retour.LocaliteCode = targetPart.LocaliteCode;
            return retour;
        }
    }
}