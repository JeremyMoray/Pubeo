using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class AuthController : ControllerBase {
        private readonly PubeoAPIdbContext _context;

        public AuthController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        [Route("AllProfessionnels")]
        public IEnumerable<Professionnel> GetAllProfessionnels()
        {
            var professionnels = _context.Professionnels;

            return professionnels;
        }

    }
}