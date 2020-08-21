using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model
{
    public class Vehicule
    {

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }
        public String Marque { get; set; }
        public String Modele { get; set; }
        public Guid ParticulierId { get; set; }

        [ForeignKey("ParticulierId")]
        public virtual Particulier Particulier { get; set; }

        [Timestamp]
        public byte[] RowVersion { get; set; }
    }
}