using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Particulier {
        public Particulier(){
            Participations = new HashSet<Participation>();
            AppartenanceVehicules = new HashSet<AppartenanceVehicule>();
        }
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }

        [StringLength(100)]
        public String Nom { get; set; }

        [StringLength(100)]
        public String Prenom { get; set; }

        [StringLength(300)]
        public String Adresse {get; set; }

        [StringLength(100)]
        public String Pseudo { get; set; }
        public DateTime DateNaissance { get; set; }
        public String NumeroTel { get; set; }

        [StringLength(100)]
        public String Mail { get; set; }
        public String LocaliteCode { get; set; }

        [ForeignKey("LocaliteCode")]
        public Localite Localite { get; set; }
        public virtual ICollection<Participation> Participations { get; set; }
        public virtual ICollection<AppartenanceVehicule> AppartenanceVehicules { get; set; } 
    }
}