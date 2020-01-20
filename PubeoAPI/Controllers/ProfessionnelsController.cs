using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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

        public ProfessionnelsController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET : /Professionnels
        [HttpGet]
        public IEnumerable<ProfessionnelsDTO> GetProfessionnels()
        {
            var professionnels = new HashSet<ProfessionnelsDTO>();
            foreach (var professionnel in _context.Professionnels)
            {
                professionnels.Add(new ProfessionnelsDTO
                {
                    Id = professionnel.Id,
                    NomEntreprise = professionnel.NomEntreprise,
                    Adresse = professionnel.Adresse,
                    NumeroTel = professionnel.NumeroTel,
                    Mail = professionnel.Mail,
                    NumeroTVA = professionnel.NumeroTVA,
                    Stickers = GetStickersDTOs(professionnel)
                });
            }
            return professionnels;
        }

        // GET : /Professionels/AllProfessionnels
        [Route("AllProfessionnels")]
        public IEnumerable<Professionnel> GetAllProfessionnels()
        {
            var professionnels = _context.Professionnels
                                        .Include(x => x.Stickers)
                                        .Include(x => x.Localite).ToList();

            return professionnels;
        }

        // GET : /Professionnels/{companyname}
        [HttpGet("{nomentreprise}")]
        public async Task<IActionResult> GetProfessionnelByCompanyName([FromRoute] string nomentreprise)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var professionnel = await _context.Professionnels.SingleOrDefaultAsync(m => m.NomEntreprise.Equals(nomentreprise));

            if (professionnel == null){
                return NotFound();
            }
            else 
            {
                var user = new ProfessionnelsDTO{
                    NomEntreprise = nomentreprise,
                    Id = professionnel.Id
                };
                if (professionnel.Adresse != null) user.Adresse = professionnel.Adresse;
                if (professionnel.NumeroTel != null) user.NumeroTel = professionnel.NumeroTel;
                if (professionnel.Mail != null) user.Mail = professionnel.Mail;
                if (professionnel.NumeroTVA != null) user.NumeroTVA = professionnel.NumeroTVA;

                user.Stickers = GetStickersDTOs(professionnel);

                return Ok(professionnel);
            }
        }

        // PUT: /professionnels/{nomentreprise}
        [HttpPut("{nomentreprise}")]
        public async Task<IActionResult> PutProfessionnels([FromRoute] string nomentreprise, [FromBody] Professionnel professionnel)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if(professionnel == null && nomentreprise != professionnel.NomEntreprise)
            {
                return BadRequest();
            }

            var user = await _context.Professionnels.SingleOrDefaultAsync(p => p.NomEntreprise.Equals(nomentreprise));
            user = Modification(user, professionnel);
            _context.Entry(user).State = EntityState.Modified;
            try{
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if(!ProfessionnelExists(nomentreprise))
                    return NotFound();
                else {
                    throw;
                }

            }
            return NoContent();
        }

        //POST: /Professionnels
        [HttpPost]
        public async Task<IActionResult> PostProfessionnels([FromBody] Professionnel professionnel)
        {
            ScryptEncoder encoder = new ScryptEncoder();

            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

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

            return CreatedAtAction("GetProfessionnels", new { id = pro.Id }, pro);
        }

        //DELETE: /Professionnels/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteProfessionnels([FromRoute] Guid id)
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

        private bool ProfessionnelExists(string nomentreprise)
        {
            return _context.Professionnels.Any(p => p.NomEntreprise.Equals(nomentreprise));
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
            foreach(var sticker in targetPro.Stickers){
                retour.Stickers.Add(sticker);
            }
            return retour;
        }

        private ICollection<StickersDTO> GetStickersDTOs(Professionnel professionnel)
        {
            var stickers = new HashSet<StickersDTO>();
            foreach (var sticker in _context.Stickers)
            {
                if (sticker.Id != null && sticker.ProfessionnelId.Equals(professionnel.Id))
                {
                    var stickerDTO = new StickersDTO()
                    {
                        Id = sticker.Id,
                        Titre = sticker.Titre,
                        Description = sticker.Description,
                        NbUtilisationsRestantes = sticker.NbUtilisationsRestantes,
                        Hauteur = sticker.Hauteur,
                        Largeur = sticker.Largeur,
                        NomEntreprise = professionnel.NomEntreprise,
                    };
                    stickers.Add(stickerDTO);
                }
            }
            return stickers;
        }
    }

}