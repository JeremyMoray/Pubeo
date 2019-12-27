using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.model {
    public class Participation {
        [Key]
        public int ParticipationId { get; set; }
        public int ParticulierId { get; set; }
        
        [ForeignKey("ParticulierId")]
        public virtual Particulier Particulier { get; set; }
        public int StickerId { get; set; }

        [ForeignKey("StickerId")]
        public virtual Sticker Sticker { get; set; }
    }
}