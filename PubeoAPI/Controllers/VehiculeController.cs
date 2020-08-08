using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

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

        /*
        // GET : /Vehicule/AllByParticular
        [Route("AllByParticular")]
        [HttpGet]
        public async Task<IActionResult> GetAllByParticular([FromBody] Particulier particulier)
        {
            if(!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
        }
        */

        // POST : /Vehicule
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

        // PUT : /Vehicule
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

    }
}