using System;

namespace PubeoAPI.DTO {

    public class StickersDTO
    {
        public Guid Id { get; set; }
        public String Titre { get; set; }
        public String Description { get; set; }
        public int Hauteur { get; set; }
        public int Largeur { get; set; }
        public int NbUtilisationsRestantes { get; set; }
        public Guid ProfessionnelId { get; set; }
    }
}