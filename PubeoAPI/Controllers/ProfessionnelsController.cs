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
    public class ProfessionnelsController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;
        private readonly IMapper mapper;

        public ProfessionnelsController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        // GET : /Professionnels
        [HttpGet]
        public IEnumerable<ProfessionnelsDTO> GetAll()
        {
            var professionnelsDetails = _context.Professionnels
                                            .Include(x => x.Localite)
                                            .Include(x => x.Stickers);
            var professionnels = mapper.Map<List<ProfessionnelsDTO>> (professionnelsDetails);
            return professionnels;
        }

        // GET : /Professionnels/GetByNomEntreprise/{companyname}
        [HttpGet("GetByNomEntreprise/{nomentreprise}")]
        public async Task<IActionResult> GetByNomEntreprise([FromRoute] string nomentreprise)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

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

        // PUT: /Professionnels/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromRoute] Guid id, [FromBody] Professionnel professionnel)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(!ProfessionnelExists(id))
                    return NotFound();
            
            if(await _context.Professionnels.AnyAsync(x => x.Mail == professionnel.Mail && x.Id != id))
                return Conflict();

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Id.Equals(id));

            user = Modification(user, professionnel);
            _context.Entry(user).State = EntityState.Modified;

            await _context.SaveChangesAsync();

            return NoContent();
        }

        //POST: /Professionnels
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Professionnel professionnel)
        {
            ScryptEncoder encoder = new ScryptEncoder();

            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(await _context.Professionnels.AnyAsync(x => x.Mail == professionnel.Mail))
                return Conflict();

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

            return CreatedAtAction("GetAll", new { id = pro.Id }, pro);
        }

        //DELETE: /Professionnels/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.Id.Equals(id));
            if (user == null)
            {
                return NotFound();
            }

            _context.Professionnels.Remove(user);
            await _context.SaveChangesAsync();
            return Ok(user);
        }

        private bool ProfessionnelExists(Guid id)
        {
            return _context.Professionnels.Any(p => p.Id.Equals(id));
        }

        private Professionnel Modification(Professionnel initialPro, Professionnel targetPro){
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