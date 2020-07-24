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

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("[controller]")]
    public class StickersController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public StickersController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET: /Stickers
        [HttpGet]
        public IEnumerable<Sticker> GetStickers(){
            return _context.Stickers;
        }


        // GET: /Stickers/{id}
        [HttpGet("{id}")]
        public async Task<IActionResult> GetSticker([FromRoute] Guid id)
        {
            if(!ModelState.IsValid) return BadRequest(ModelState);

            var sticker = await _context.Stickers.SingleOrDefaultAsync(s => s.Id == id);

            if(sticker == null) return NotFound();

            return Ok(sticker);
        }

        // POST: /Stickers
        [HttpPost]
        public async Task<IActionResult> PostSticker([FromBody] StickersDTO stickers)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var sticker = await BondToProfessionnel(stickers);
            await _context.Stickers.AddAsync(sticker);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSticker", new { id = sticker.Id }, sticker);
        }

        // DELETE: /Stickers/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSticker([FromRoute] Guid id)
        {
            if(!ModelState.IsValid) return BadRequest(ModelState);

            var sticker = await _context.Stickers.SingleOrDefaultAsync(s => s.Id.Equals(id));
            if (sticker == null) return NotFound();

            _context.Stickers.Remove(sticker);
            await _context.SaveChangesAsync();
            return Ok(sticker);
        }

        private async Task<Sticker> BondToProfessionnel(StickersDTO stickers)
        {
            var stickerTemp = new Sticker();
            if(stickers.NomEntreprise != null)
            {
                stickerTemp.Id = stickers.Id;
                stickerTemp.Titre = stickers.Titre;
                stickerTemp.Description = stickers.Description;
                stickerTemp.Hauteur = stickers.Hauteur;
                stickerTemp.Largeur = stickers.Largeur;
                stickerTemp.NbUtilisationsRestantes = stickers.NbUtilisationsRestantes;
                var pro = await _context.Professionnels
                    .FirstOrDefaultAsync(p => p.NomEntreprise.Equals(stickers.NomEntreprise));
                stickerTemp.ProfessionnelId = pro.Id;
            }
            return stickerTemp; 
        }
    }
}