using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PubeoAPI.DTO;
using PubeoAPI.model;

namespace PubeoAPI.Controllers {

    [ApiController]
    [Route("[controller]")]
    public class ProfessionnelsController : ControllerBase
    {
        private readonly PubeoAPIdbContext _context;

        public ProfessionnelsController(PubeoAPIdbContext context)
        {
            _context = context;
        }

        // GET : api/Professionnels
        [HttpGet]
        public async Task<IEnumerable<ProfessionnelsDTO>> GetProfessionnels()
        {
            var professionnels = new HashSet<ProfessionnelsDTO>();
            foreach(var professionnel in _context.Professionnels){
                professionnels.Add(new ProfessionnelsDTO{
                    Id = professionnel.Id,
                    NomEntreprise = professionnel.NomEntreprise,
                    Adresse = professionnel.Adresse,
                    NumeroTel = professionnel.NumeroTel,
                    Mail = professionnel.Mail,
                    NumeroTVA = professionnel.NumeroTVA,
                    Stickers = await GetStickersDTOs(professionnel)
                });
            }
            return professionnels;
        }

        private async Task<ICollection<StickersDTO>> GetStickersDTOs(Professionnel professionnel){
            var stickers = new HashSet<StickersDTO>();
            foreach(var sticker in _context.Stickers){
                if(sticker.ProfessionnelId!=null && sticker.ProfessionnelId.Equals(professionnel.Id)){
                    var stickerDTO = new StickersDTO();
                    stickerDTO.Id = sticker.Id;
                    stickerDTO.Titre = sticker.Titre;
                    stickerDTO.Description = sticker.Description;
                    stickerDTO.NbUtilisationsRestantes = sticker.NbUtilisationsRestantes;
                    stickerDTO.Hauteur = sticker.Hauteur;
                    stickerDTO.Largeur = sticker.Largeur;
                    stickers.Add(stickerDTO);
                }
            }
            return stickers;
        }
    }

}