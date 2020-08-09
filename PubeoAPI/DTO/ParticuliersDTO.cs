using System;
using System.Collections.Generic;

namespace PubeoAPI.DTO
{
    public class ParticuliersDTO
    {
        public ParticuliersDTO() {

        }
        public Guid Id { get; set; }
        public String Nom { get; set; }
        public String Prenom { get; set; }
        public String Adresse { get; set; }
        public String Pseudo { get; set; }
        public DateTime DateNaissance { get; set; }
        public String NumeroTel { get; set; }
        public String Mail { get; set; }

        // TODO : add relationships
        public ICollection<ParticipationDTO> Participations { get; set; }
        public ICollection<AppartenanceVehiculeDTO> AppartenanceVehicules { get; set; }

    }
}