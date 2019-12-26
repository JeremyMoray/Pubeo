namespace PubeoAPI.model {
    public class Participation {
        public int ParticulierID { get; set; }
        public int StickerID { get; set; }
        public virtual Particulier Particulier { get; set; }
        public virtual Sticker Sticker { get; set; }
    }
}