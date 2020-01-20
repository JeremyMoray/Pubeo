using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;
using PubeoAPI.Repository;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class LocaliteController : ControllerBase {

        private readonly ILocalRepository _localRepository;
        private readonly PubeoAPIdbContext _context;
        
        public LocaliteController(PubeoAPIdbContext context, ILocalRepository localRepo)
        {
            _context = context;
            _localRepository = localRepo;
        }

        [Route("LocaliteOnline")]
        public IEnumerable<LocaliteDTO> GetLocaliteOnline()
        {
            return _localRepository.GetLocalFromExternalSource();

        }

        [Route("AllLocalites")]
        public IEnumerable<Localite> GetAllLocalites(){
            
            var localites = _context.Localites
                                    .Include(x => x.Professionnels)
                                    .Include(x => x.Particuliers).ToList();
            return localites;
        }

        [HttpGet]
        public IEnumerable<Localite> GetLocalites(){
            return _context.Localites;
        }

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