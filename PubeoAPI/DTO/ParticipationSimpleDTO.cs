using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PubeoAPI.DTO {
    public class ParticipationSimpleDTO {
        
        public Guid ParticipationId { get; set; }
        public Guid ParticulierId { get; set; }
    }
}