using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class AppartenanceVehicule {

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid AppartenanceId { get; set; }
        public Guid ParticulierId { get; set; }

        [ForeignKey("ParticulierId")]
        public virtual Particulier Particulier { get; set; }

        [Column(Order=1)]
        public String Marque { get; set; }

        [Column(Order=2)]
        public String Modele { get; set; }

        [ForeignKey("Marque,Modele")]
        public virtual Vehicule Vehicule { get; set; }
    }
}