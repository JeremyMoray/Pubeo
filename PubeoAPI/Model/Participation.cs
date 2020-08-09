using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Participation {
        
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public Guid ParticipationId { get; set; }
        public Guid ParticulierId { get; set; }

        [ForeignKey("ParticulierId")]
        public virtual Particulier Particulier { get; set; }
        [Required]
        public Guid StickerId { get; set; }

        [ForeignKey("StickerId")]
        public virtual Sticker Sticker { get; set; }
    }
}