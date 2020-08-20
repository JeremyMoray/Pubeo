
using System;
using Microsoft.AspNetCore.Identity;

namespace PubeoAPI.model.auth {
    public class User : IdentityUser<Guid>
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string IdentificationKey { get; set; }

    }
}