using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.model {
    public class Localite 
    {
        public Localite(){
            Particuliers = new HashSet<Particulier>();
            Professionnels = new HashSet<Professionnel>();
        }
        [Key]
        public String CodePostal { get; set; }
        public String Ville { get; set; }
        public virtual ICollection<Particulier> Particuliers { get; set; }
        public virtual ICollection<Professionnel> Professionnels { get; set; }
    }
}