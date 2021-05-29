package italo.xadrez.media;

public class MediaManager {
    
    private final ImagemManager imagemManager = new ImagemManager();
    private final AudioManager audioManager = new AudioManager();

    public MediaCarregador criaCarregador() {
        return new MediaCarregador( audioManager, imagemManager );
    }
    
    public ImagemManager getImagemManager() {
        return imagemManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }        
    
}
