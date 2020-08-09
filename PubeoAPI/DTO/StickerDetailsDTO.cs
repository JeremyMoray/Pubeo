using System;
using System.Collections.Generic;
using PubeoAPI.model;

namespace PubeoAPI.DTO {

    public class StickersDetailsDTO
    {
        public Guid Id { get; set; }
        public String Titre { get; set; }
        public String Description { get; set; }
        public int Hauteur { get; set; }
        public int Largeur { get; set; }
        public int NbUtilisationsRestantes { get; set; }
        public ProfessionnelsSimpleDTO Professionnel { get; set; }   
        public ICollection<ParticipationSimpleDTO> Participations { get; set; } 
    }
}