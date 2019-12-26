using System;
using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.model {
    public class Professionnel {
        [Key]
        public int ID { get; set; }
        public String nomEntreprise { get; set; }
        public String NumeroTel { get; set; }
        public String MotDePasse { get; set; }
        public String Mail { get; set; }
        public String NumeroTVA { get; set; }
        public Localite localiteID { get; set; } 

    }
}