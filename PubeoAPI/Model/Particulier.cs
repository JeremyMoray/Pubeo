using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.model {
    public class Particulier {
        public Particulier(){
            Participation = new HashSet<Participation>();
            AppartenanceVehicule = new HashSet<AppartenanceVehicule>();
        }
        [Key]
        public int ID { get; set; }
        public String Nom { get; set; }
        public String Prenom { get; set; }
        public String Adresse {get; set; }
        public String Pseudo { get; set; }
        public DateTime DateNaissance { get; set; }
        public String NumeroTel { get; set; }
        public String Mail { get; set; }
        public virtual ICollection<Participation> Participation { get; set; }
        public virtual ICollection<AppartenanceVehicule> AppartenanceVehicule { get; set; } 
    }
}