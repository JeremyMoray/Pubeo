using System.Collections.Generic;
using PubeoAPI.DTO;
using PubeoAPI.model;

namespace PubeoAPI.Repository 
{
    public interface ILocalRepository
    {
        IEnumerable<LocaliteDTO> GetLocalFromExternalSource();
    }
}