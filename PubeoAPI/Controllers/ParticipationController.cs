using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using AutoMapper;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;
using System.Net;

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("v{version:apiVersion}/[controller]")]
    [ApiVersion("1")]
    [ApiVersion("2")]
    public class ParticipationController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;
        private readonly IMapper mapper;

        public ParticipationController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        [Authorize(Roles = "admin")]
        // GET: /Participation/GetAllStickersByParticulierId/{particulierId}
        [HttpGet("GetAllStickersByParticulierId/{particulierId}")]
        public async Task<IActionResult> GetAllStickersByParticulierId([FromRoute] Guid particulierId)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var particulier = await _context.Particuliers
                .Include(x => x.Participations)
                    .ThenInclude(x => x.Sticker)
                .SingleOrDefaultAsync(x => x.Id == particulierId);
            
            if(particulier == null) 
                return NotFound();

            List<Participation> participations = particulier.Participations.ToList();
            List<StickersDTO> stickers = mapper.Map<List<StickersDTO>> (participations.Select(x => x.Sticker));

            return Ok(stickers);
        }

        [HttpGet("GetAllMyStickers")]
        public async Task<IActionResult> GetAllMyStickers()
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                    return NotFound();

            var particulier = await _context.Particuliers
                .Include(x => x.Participations)
                    .ThenInclude(x => x.Sticker)
                .SingleOrDefaultAsync(x => x.Id == user.Id);
            
            if(particulier == null) 
                return NotFound();

            List<Participation> participations = particulier.Participations.ToList();
            List<StickersDTO> stickers = mapper.Map<List<StickersDTO>> (participations.Select(x => x.Sticker));

            return Ok(stickers);
        }

        [Authorize(Roles = "admin")]
        // GET: /Participation/GetAllParticuliersByStickerId/{stickerId}
        [HttpGet("GetAllParticuliersByStickerId/{stickerId}")]
        public async Task<IActionResult> GetAllParticuliersByStickerId([FromRoute] Guid stickerId)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var sticker = await _context.Stickers
                .Include(x => x.Participations)
                    .ThenInclude(x => x.Particulier)
                .SingleOrDefaultAsync(x => x.Id == stickerId);
            
            if(sticker == null)
                return NotFound();

            List<Participation> participations = sticker.Participations.ToList();
            List<ParticulierSimpleDTO> particuliers = mapper.Map<List<ParticulierSimpleDTO>> (participations.Select(x => x.Particulier));

            return Ok(particuliers);
        }

        // POST: /Participation
        [Authorize(Roles = "admin")]
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Participation participation)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            if(!await _context.Particuliers.AnyAsync(x => x.Id == participation.ParticulierId) || !await _context.Stickers.AnyAsync(x => x.Id == participation.StickerId))
                return NotFound();

            if(await _context.Participations.AnyAsync(x => x.ParticulierId == participation.ParticulierId && x.StickerId == participation.StickerId))
                return Conflict();

            var validParticipation = new Participation{
                ParticulierId = participation.ParticulierId,
                StickerId = participation.StickerId
            };
            await _context.Participations.AddAsync(validParticipation);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpPost("AddStickerToMyAccount")]
        public async Task<IActionResult> AddStickerToMyAccount([FromBody] Participation participation)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                return NotFound();
            
            if(user.Id != participation.ParticulierId)
                return StatusCode((int)HttpStatusCode.Forbidden);

            if(await _context.Participations.AnyAsync(x => x.ParticulierId == participation.ParticulierId && x.StickerId == participation.StickerId))
                return Conflict();

            if(!await _context.Particuliers.AnyAsync(x => x.Id == participation.ParticulierId) || !await _context.Stickers.AnyAsync(x => x.Id == participation.StickerId))
                return NotFound();

            var validParticipation = new Participation{
                ParticulierId = participation.ParticulierId,
                StickerId = participation.StickerId
            };
            await _context.Participations.AddAsync(validParticipation);
            await _context.SaveChangesAsync();

            return Ok();
        }

        // DELETE: /Participation/{id}
        [Authorize(Roles = "admin")]
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);

            var participation = await _context.Participations.SingleOrDefaultAsync(x => x.ParticipationId.Equals(id));
            if (participation == null)
                return NotFound();

            _context.Participations.Remove(participation);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // DELETE: /Participation/DeleteMySticker/{stickerId}
        [HttpDelete("DeleteMySticker/{stickerId}")]
        public async Task<IActionResult> DeleteMySticker([FromRoute] Guid stickerId)
        {
            if(!ModelState.IsValid)
                return BadRequest(ModelState);
                
            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;
            var user = await _context.Particuliers.SingleOrDefaultAsync(x => x.Mail.Equals(email));

            if(user == null)
                return NotFound();

            var participation = await _context.Participations.SingleOrDefaultAsync(x => x.StickerId.Equals(stickerId) && x.ParticulierId.Equals(user.Id));
            
            if (participation == null)
                return NotFound();

            _context.Participations.Remove(participation);
            await _context.SaveChangesAsync();
            return Ok();
        }
    }
}