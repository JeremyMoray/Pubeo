using System;
using System.Collections.Generic;

namespace PubeoAPI.DTO {
    public class ProfessionnelsDTO
    {
        public ProfessionnelsDTO() {
            Stickers = new HashSet<StickersDTO>();
        }
        public Guid Id { get; set; }
        public String NomEntreprise { get; set; }
        public String Adresse { get; set; }
        public String NumeroTel { get; set; }
        public String Mail { get; set; }
        public String NumeroTVA { get; set; }
        
        public ICollection<StickersDTO> Stickers { get; set; }
    }
}