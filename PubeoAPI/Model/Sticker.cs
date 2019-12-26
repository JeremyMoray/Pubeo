using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.model {
    public class Sticker {
        public Sticker(){
            Participation = new HashSet<Participation>();
        }
        [Key]
        public int ID {get; set; }
        public String Titre { get; set; }
        public String Description { get; set; }
        public int Hauteur { get; set; }
        public int Largeur { get; set; }
        public int nbUtilisationsRestantes { get; set; } 
        public Professionnel professionnelID { get; set; }   
        public virtual ICollection<Participation> Participation { get; set; }     
    }
}