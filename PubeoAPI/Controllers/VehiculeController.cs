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
    public class VehiculeController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public VehiculeController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET : /Vehicule
        [Authorize(Roles = "admin")]
        [HttpGet("{id}")]
        public async Task<IActionResult> GetVehicule([FromRoute] Guid id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var vehicule = await _context.Vehicules.SingleOrDefaultAsync(v => v.Id.Equals(id));

            if (vehicule == null) {
                return NotFound();
            }
            else 
            {
                return Ok(vehicule);
            }
        }

        //GET : /Vehicule/GetAllMyVehicules
        [Route("GetAllMyVehicules")]
        [HttpGet]
        public async Task<IActionResult> GetAllMyVehicules()
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null) 
                return NotFound();

            var vehicules = await _context.Vehicules.ToListAsync();

            var myVehicules = vehicules.FindAll(v => v.ParticulierId.Equals(user.Id));
            
            if(myVehicules == null) 
                return NotFound();
            
            return Ok(myVehicules);
        }

        // POST : /Vehicule
        [Authorize(Roles = "admin")]
        [HttpPost]
        public async Task<IActionResult> PostVehicule([FromBody] Vehicule vehicule) {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Vehicules.Add(vehicule);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetVehicule", new { id = vehicule.Id }, vehicule);
        }

        [HttpPost("AddVehiculeToMyAccount")]
        public async Task<IActionResult> AddVehiculeToMyAccount([FromBody] Vehicule vehicule) {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                    return NotFound();

            if(user.Id != vehicule.ParticulierId)
                return Forbid();

            _context.Vehicules.Add(vehicule);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetVehicule", new { id = vehicule.Id }, vehicule);
        }

        // PUT : /Vehicule
        [Authorize(Roles = "admin")]
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateVehicule([FromRoute] Guid id ,[FromBody] Vehicule vehicule)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if (id != vehicule.Id)
            {
                return BadRequest();
            }

            _context.Entry(vehicule).State = EntityState.Modified;

            try {
                await _context.SaveChangesAsync();
            }
            catch(DbUpdateConcurrencyException)
            {
                if(!VehiculeExists(id)) {
                    return NotFound();
                }
                else {
                    throw;
                }
            }

            return NoContent();

        }

        [HttpPut("UpdateMyVehicule/{id}")]
        public async Task<IActionResult> UpdateMyVehicule([FromRoute] Guid id, [FromBody] Vehicule vehicule)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                return NotFound();

            if (id != vehicule.Id)
                return BadRequest();

            if(user.Id != vehicule.Id)
                return Forbid();

            _context.Entry(vehicule).State = EntityState.Modified;

            try {
                await _context.SaveChangesAsync();
            }
            catch(DbUpdateConcurrencyException)
            {
                if(!VehiculeExists(id)) {
                    return NotFound();
                }
                else {
                    throw;
                }
            }

            return NoContent();

        }

        private bool VehiculeExists(Guid id)
        {
            return _context.Vehicules.Any(e => e.Id == id);
        }      

        // DELETE : /Vehicule/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteVehicule([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var vehicule = await _context.Vehicules.SingleOrDefaultAsync(v => v.Id == id);
            if (vehicule == null)
            {
                return NotFound();
            }

            _context.Vehicules.Remove(vehicule);
            await _context.SaveChangesAsync();

            return Ok(vehicule);
        }
        
        [HttpDelete("DeleteMyVehicule/{id}")]
        public async Task<IActionResult> DeleteMyVehicule([FromRoute] Guid id)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var email = User.Claims.SingleOrDefault(x => x.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").Value;

            var user = await _context.Particuliers.SingleOrDefaultAsync(p => p.Mail.Equals(email));

            if(user == null)
                return NotFound();

            var vehicule = await _context.Vehicules.SingleOrDefaultAsync(v => v.Id == id);
            
            if (vehicule == null)
                return NotFound();

            if(user.Id != vehicule.ParticulierId)
                return Forbid();

            _context.Vehicules.Remove(vehicule);
            await _context.SaveChangesAsync();

            return Ok(vehicule);
        }
    }
}