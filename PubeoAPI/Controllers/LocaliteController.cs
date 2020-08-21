using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;
using PubeoAPI.Repository;

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("[controller]")]
    public class LocaliteController : ControllerBase {

        private readonly PubeoAPIdbContext _context;
        
        public LocaliteController(PubeoAPIdbContext context, ILocalRepository localRepo)
        {
            _context = context;
        }

        // GET : /Localite/AllLocalites
        [Route("AllLocalites")]
        [HttpGet]
        public IEnumerable<Localite> GetAllLocalites(){
            
            var localites = _context.Localites
                                    .Include(x => x.Professionnels)
                                    .Include(x => x.Particuliers).ToList();
            return localites;
        }

        // GET : /Localite
        [HttpGet]
        public IEnumerable<Localite> GetLocalites(){
            return _context.Localites;
        }

        // GET : /Localite/{codePostal}
        [Authorize(Roles = "admin")]
        [HttpGet("{cp}")]
        public async Task<IActionResult> GetByPostalCode([FromRoute] string cp)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var localite = await _context.Localites.Include(x => x.Professionnels)
                                                   .Include(x => x.Particuliers)
                                                   .SingleOrDefaultAsync(l => l.CodePostal.Equals(cp));

            if (localite == null){
                return NotFound();
            }
            else
            {
                var loc = new Localite{
                    CodePostal = cp,
                    Ville = localite.Ville,
                    Particuliers = localite.Particuliers,
                    Professionnels = localite.Professionnels
                };
                // TODO : include the elements of the particuliers and the professionels
                return Ok(localite);
            }

        }

        [Authorize(Roles = "admin")]
        [HttpPost]
        public async Task<IActionResult> PostLocalite([FromBody] LocaliteDTO localiteDto)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var localite = new Localite();
            localite.CodePostal = localiteDto.CodePostal;
            localite.Ville = localiteDto.Ville;

            await _context.Localites.AddAsync(localite);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetLocalites",
                                   new { codePostal = localite.CodePostal },
                                   localite);

        }

    }

}