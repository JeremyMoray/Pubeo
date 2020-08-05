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
using Scrypt;

namespace PubeoAPI.Controllers {

    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    [Route("[controller]")]
    public class ParticuliersController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public ParticuliersController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET : /Particuliers
        [HttpGet]
        public IEnumerable<ParticuliersDTO> GetParticuliers()
        {
            var particuliers = new HashSet<ParticuliersDTO>();
            foreach (var particulier in _context.Particuliers)
            {
                particuliers.Add(new ParticuliersDTO
                {
                    Id = particulier.Id,
                    Nom = particulier.Nom,
                    Prenom = particulier.Prenom,
                    Adresse = particulier.Adresse,
                    Pseudo = particulier.Pseudo,
                    DateNaissance = particulier.DateNaissance,
                    NumeroTel = particulier.NumeroTel,
                    Mail = particulier.Mail,
                    // Participations = GetParticipationsDTOs(particulier),
                    // AppartenanceVehicules = GetAppartenanceVehiculesDTOs(particulier)
                });
            }
            return particuliers;
        }
    }
}