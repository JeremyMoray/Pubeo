using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model
{
    public class ParticuliersUpdateDTO
    {
        public ParticuliersUpdateDTO()
        {
            Participations = new HashSet<Participation>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String Nom { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String Prenom { get; set; }

        [StringLength(300)]
        [Required(AllowEmptyStrings = false)]
        public String Adresse { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String Pseudo { get; set; }

        [DataType(DataType.Date)]
        [Required]
        public DateTime DateNaissance { get; set; }

        [Required(AllowEmptyStrings = false)]
        public String NumeroTel { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String Mail { get; set; }
        public String MotDePasse { get; set; }

        public String LocaliteCode { get; set; }

        [ForeignKey("LocaliteCode")]
        public virtual Localite Localite { get; set; }

        public virtual ICollection<Participation> Participations { get; set; }

        public virtual ICollection<Vehicule> Vehicules { get; set; }
    }
}