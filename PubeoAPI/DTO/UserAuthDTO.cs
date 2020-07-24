using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.DTO
{
      public class UserAuthDTO
    {
        [Required]
        public string NomEntreprise { get; set; }
        [Required]
        public string Mail { get; set; }
        [Required]
        public string MotDePasse { get; set; }    
    }
}