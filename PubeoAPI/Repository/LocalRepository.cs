using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using PubeoAPI.DTO;
using PubeoAPI.model;
using RestSharp;

namespace PubeoAPI.Repository 
{
    public class LocalRepository: ILocalRepository {
        
        public IEnumerable<LocaliteDTO> GetLocalFromExternalSource()
        {
            var client = new RestClient($"http://geoservices.wallonie.be/geolocalisation/rest/getListeCommunes/");
            var request  = new RestRequest(Method.GET) {RequestFormat = DataFormat.Json};
            var response = client.Execute(request);
            if(response.IsSuccessful)
            {
                // Test
                var content = JsonConvert.DeserializeObject<JToken>(response.Content);

                var cps = content["communes.cps[1]"].Value<string>();
                var nom = content["communes.nom"].Value<string>();

                var localites = new HashSet<LocaliteDTO>();

                localites.Add(new LocaliteDTO
                {
                    CodePostal = cps,
                    Ville = nom
                });

                return localites;

            }
            else {
                return null;
            }               
        }
    }
}