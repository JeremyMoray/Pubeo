using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Professionnel {

        public Professionnel() {
            Stickers = new HashSet<Sticker>();
        }
        [Key]
        public int Id { get; set; }
        
        [StringLength(100)]
        public String NomEntreprise { get; set; }

        [StringLength(300)]
        public String Adresse { get; set; }
        public String NumeroTel { get; set; }
        public String MotDePasse { get; set; }

        [StringLength(100)]
        public String Mail { get; set; }
        public String NumeroTVA { get; set; }
        public String LocaliteCode { get; set; }

        [ForeignKey("LocaliteCode")]
        public virtual Localite Localite { get; set; }
        public virtual ICollection<Sticker> Stickers { get; set; }

    }
}