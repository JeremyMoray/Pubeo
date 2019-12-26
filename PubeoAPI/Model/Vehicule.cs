using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Vehicule {
        public Vehicule(){
            AppartenanceVehicule = new HashSet<AppartenanceVehicule>();
        }
        [Key]
        [Column(Order=1)]
        public String Marque { get; set; }
        [Key]
        [Column(Order=2)]
        public String Modele { get; set; }
        public virtual ICollection<AppartenanceVehicule> AppartenanceVehicule { get; set; }
    }
}