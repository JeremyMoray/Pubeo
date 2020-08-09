using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.AspNetCore.Identity;

namespace PubeoAPI.model {
    public class Professionnel {

        public Professionnel() {
            Stickers = new HashSet<Sticker>();
        }
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String NomEntreprise { get; set; }

        [StringLength(300)]
        [Required(AllowEmptyStrings = false)]
        public String Adresse { get; set; }
        [Required(AllowEmptyStrings = false)]
        public String NumeroTel { get; set; }
        public String MotDePasse { get; set; }

        [StringLength(100)]
        [Required(AllowEmptyStrings = false)]
        public String Mail { get; set; }
        [Required(AllowEmptyStrings = false)]
        public String NumeroTVA { get; set; }
        public String LocaliteCode { get; set; }

        [ForeignKey("LocaliteCode")]
        public virtual Localite Localite { get; set; }
        public virtual ICollection<Sticker> Stickers { get; set; }

    }
}