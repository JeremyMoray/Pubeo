using AutoMapper;
using PubeoAPI.model;

namespace PubeoAPI.DTO {
    public class AutoMapperProfile : Profile{

        public AutoMapperProfile(){

            CreateMap<Localite, LocaliteDTO>();
            CreateMap<LocaliteDTO, Localite>();
            CreateMap<Professionnel, ProfessionnelsDTO>();
            CreateMap<ProfessionnelsDTO, Professionnel>();
            CreateMap<Professionnel, ProfessionnelsSimpleDTO>();
            CreateMap<ProfessionnelsSimpleDTO, Professionnel>();
            CreateMap<Sticker, StickersDTO>();
            CreateMap<StickersDTO, Sticker>();
            CreateMap<Sticker, StickersDetailsDTO>();
            CreateMap<StickersDetailsDTO, Sticker>();
        }
    }
}