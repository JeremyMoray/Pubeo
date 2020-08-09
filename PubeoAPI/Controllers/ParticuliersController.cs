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

            var _particulier = await _context.Particuliers.SingleOrDefaultAsync(p => p.Id.Equals(id));

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
            {
                return BadRequest(ModelState);
            }
            if(particulier == null && id != particulier.Id)
            {
                return BadRequest();
            }

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Id.Equals(particulier));
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
    }
}