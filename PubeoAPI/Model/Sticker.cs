using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Sticker {
        public Sticker(){
            Participations = new HashSet<Participation>();
        }
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id {get; set; }
        [Required(AllowEmptyStrings = false)]
        public String Titre { get; set; }

        [StringLength(300)]
        public String Description { get; set; }
        [Required]
        public int Hauteur { get; set; }
        [Required]
        public int Largeur { get; set; }
        [Required]
        public int NbUtilisationsRestantes { get; set; } 
        public Guid ProfessionnelId { get; set; }

        [ForeignKey("ProfessionnelId")]
        public virtual Professionnel Professionnel { get; set; }   
        public virtual ICollection<Participation> Participations { get; set; }     
    }
}