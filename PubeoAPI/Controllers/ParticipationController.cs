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

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("[controller]")]
    public class ParticipationController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;
        private readonly IMapper mapper;

        public ParticipationController(PubeoAPIdbContext context, IMapper mapper)
        {
            _context = context;
            this.mapper = mapper;
        }

        // GET: /Participation/GetAllStickersByParticulierId/{particulierrId}
        [HttpGet("GetAllStickersByParticulierId/{particulierrId}")]
        public async Task<IActionResult> GetAllStickersByParticulierId([FromRoute] Guid particulierrId)
        {
            if(!ModelState.IsValid) 
                return BadRequest(ModelState);

            var particulier = await _context.Particuliers
                .Include(x => x.Participations)
                    .ThenInclude(x => x.Sticker)
                .SingleOrDefaultAsync(x => x.Id == particulierrId);
            
            if(particulier == null) 
                return NotFound();

            List<Participation> participations = particulier.Participations.ToList();
            List<StickersDTO> stickers = mapper.Map<List<StickersDTO>> (participations.Select(x => x.Sticker));

            return Ok(stickers);
        }

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
    }
}