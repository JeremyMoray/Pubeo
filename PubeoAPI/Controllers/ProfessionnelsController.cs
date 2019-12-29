using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class ProfessionnelsController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public ProfessionnelsController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET : api/Professionnels
        [HttpGet]
        public async Task<IEnumerable<ProfessionnelsDTO>> GetProfessionnels()
        {
            var professionnels = new HashSet<ProfessionnelsDTO>();
            foreach(var professionnel in _context.Professionnels){
                professionnels.Add(new ProfessionnelsDTO{
                    Id = professionnel.Id,
                    NomEntreprise = professionnel.NomEntreprise,
                    Adresse = professionnel.Adresse,
                    NumeroTel = professionnel.NumeroTel,
                    Mail = professionnel.Mail,
                    NumeroTVA = professionnel.NumeroTVA,
                    Stickers = await GetStickersDTOs(professionnel)
                });
            }
            return professionnels;
        }

        // GET : api/Professionnels/{companyname}
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
                if (professionnel.Adresse != null)
                    user.Adresse = professionnel.Adresse;
                if (professionnel.NumeroTel != null)
                    user.NumeroTel = professionnel.NumeroTel;
                if (professionnel.Mail != null)
                    user.Mail = professionnel.Mail;
                if (professionnel.NumeroTVA != null)
                    user.NumeroTVA = professionnel.NumeroTVA;

                user.Stickers = await GetStickersDTOs(professionnel);

                return Ok(professionnel);
            }
        }

        // PUT: api/professionnels/{nomentreprise}
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
                throw;
            }
            return NoContent();
        }

        //POST: api/Professionnels
        [HttpPost]
        public async Task<IActionResult> PostProfessionnels([FromBody] Professionnel professionnel)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            await _context.Professionnels.AddAsync(professionnel);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetProfessionnel", new { id = professionnel.Id }, professionnel);
        }

        //DELETE: api/Professionnels/{id}
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

        private Professionnel Modification(Professionnel initialPro, Professionnel targetPro){
            var retour = initialPro;
            if(targetPro.NomEntreprise != null) retour.NomEntreprise = targetPro.NomEntreprise;
            if(targetPro.Adresse != null) retour.Adresse = targetPro.Adresse;
            if(targetPro.NumeroTel != null) retour.NumeroTel = targetPro.NumeroTel;
            if(targetPro.Mail != null) retour.Mail = targetPro.Mail;
            if(targetPro.NumeroTVA != null) retour.NumeroTVA = targetPro.NumeroTVA;
            foreach(var sticker in targetPro.Stickers){
                retour.Stickers.Add(sticker);
            }
            return retour;
        }

        private async Task<ICollection<StickersDTO>> GetStickersDTOs(Professionnel professionnel){
            var stickers = new HashSet<StickersDTO>();
            foreach(var sticker in _context.Stickers){
                if(sticker.Id!=null && sticker.Id.Equals(professionnel.Id)){
                    var stickerDTO = new StickersDTO();
                    stickerDTO.Id = sticker.Id;
                    stickerDTO.Titre = sticker.Titre;
                    stickerDTO.Description = sticker.Description;
                    stickerDTO.NbUtilisationsRestantes = sticker.NbUtilisationsRestantes;
                    stickerDTO.Hauteur = sticker.Hauteur;
                    stickerDTO.Largeur = sticker.Largeur;
                    stickers.Add(stickerDTO);
                }
            }
            return stickers;
        }
    }

}