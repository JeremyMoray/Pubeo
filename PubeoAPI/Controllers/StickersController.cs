using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using AutoMapper;
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
        private readonly IMapper mapper;

        public StickersController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        // GET: /Stickers
        [HttpGet]
        public async Task<IActionResult> GetAll(){
            var stickersDetails = await _context.Stickers.ToListAsync();
            return Ok(mapper.Map<List<StickersDTO>> (stickersDetails));
        }

        // GET: /Stickers/{id}
        [HttpGet("{id}")]
        public async Task<IActionResult> GetSticker([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            var sticker = await _context.Stickers
                                    .Include(x => x.Professionnel)
                                        .ThenInclude(x => x.Localite)
                                    .Include(x => x.Participations)
                                    .SingleOrDefaultAsync(s => s.Id == id);

            if(sticker == null)
                return NotFound();

            return Ok(mapper.Map<StickersDetailsDTO> (sticker));
        }

        // GET: /Stickers/GetAllByProfessionnelId/{professionnelId}
        [HttpGet("GetAllByProfessionnelId/{professionnelId}")]
        public async Task<IActionResult> GetAllByProfessionnelId([FromRoute] Guid professionnelId)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var stickers = await _context.Stickers
                                    .Include(x => x.Participations)
                                    .ToListAsync();
            var stickersList = stickers.FindAll(s => s.ProfessionnelId == professionnelId);

            return Ok(mapper.Map<List<StickersDetailsDTO>> (stickersList));
        }

        // POST: /Stickers
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Sticker sticker)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(!await _context.Professionnels.AnyAsync(x => x.Id == sticker.ProfessionnelId))
                return NotFound();

            var validSticker = new Sticker{
                Titre = sticker.Titre,
                Description = sticker.Description,
                Hauteur = sticker.Hauteur,
                Largeur = sticker.Largeur,
                NbUtilisationsRestantes = sticker.NbUtilisationsRestantes,
                ProfessionnelId = sticker.ProfessionnelId
            };
            
            await _context.Stickers.AddAsync(validSticker);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSticker", new { id = validSticker.Id }, validSticker);
        }

        // PUT: /Stickers/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromRoute] Guid id, [FromBody] Sticker sticker)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(!await _context.Professionnels.AnyAsync(x => x.Id == sticker.ProfessionnelId))
                return NotFound();

            var initialSticker = await _context.Stickers.SingleOrDefaultAsync(x => x.Id == id);

            initialSticker = Modification(initialSticker, sticker);
            _context.Entry(initialSticker).State = EntityState.Modified;

            await _context.SaveChangesAsync();

            return NoContent();
        }

        // DELETE: /Stickers/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete([FromRoute] Guid id)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var sticker = await _context.Stickers.SingleOrDefaultAsync(s => s.Id.Equals(id));
            if (sticker == null) 
                return NotFound();

            _context.Stickers.Remove(sticker);
            await _context.SaveChangesAsync();
            return Ok();
        }

        private Sticker Modification(Sticker initialSticker, Sticker targetSticker){
            var retour = initialSticker;
            retour.Titre = targetSticker.Titre;
            if(targetSticker.Description != null) retour.Description = targetSticker.Description;
            retour.Hauteur = targetSticker.Hauteur;
            retour.Largeur = targetSticker.Largeur;
            retour.NbUtilisationsRestantes = targetSticker.NbUtilisationsRestantes;
            return retour;
        }
    }
}