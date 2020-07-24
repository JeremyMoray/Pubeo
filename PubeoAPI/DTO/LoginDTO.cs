using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.DTO
{
      public class LoginDTO
    {
        [Required]
        public string Mail { get; set; }
        [Required]
        public string MotDePasse { get; set; }    
    }
}