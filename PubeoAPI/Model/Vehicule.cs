using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Vehicule {
        public Vehicule(){
            AppartenanceVehicules = new HashSet<AppartenanceVehicule>();
        }

        [Column(Order=1)]
        public String Marque { get; set; }

        [Column(Order=2)]
        public String Modele { get; set; }
        public virtual ICollection<AppartenanceVehicule> AppartenanceVehicules { get; set; }
    }
}