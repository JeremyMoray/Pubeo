using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class StickersController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public StickersController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        //CRUD stickers
        
    }
}