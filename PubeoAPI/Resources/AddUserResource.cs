using System.ComponentModel.DataAnnotations;

namespace PubeoAPI.Resources
{
      public class AddUserResource
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string IdentificationKey { get; set; }
        public string Password { get; set; }
    }
}