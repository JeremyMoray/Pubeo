namespace PubeoAPI.model {
    public class AppartenanceVehicule {
        public int ParticulierID { get; set; }
        public string Marque { get; set; }
        public string Modele { get; set; }
        public virtual Particulier Particulier { get; set; }
        public virtual Vehicule Vehicule { get; set; }
    }
}