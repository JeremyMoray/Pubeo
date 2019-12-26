using System;
using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.model {
    public class Localite 
    {
        [Key]
        public int CodePostal { get; set; }
        public String Ville { get; set; }
    }
}