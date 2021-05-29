package italo.xadrez.media;

public class MediaCarregador extends Thread {
    
    private final AudioManager audioManager;
    private final ImagemManager imagemManager;

    private MediaCarregadorListener listener;

    public MediaCarregador(AudioManager audioManager, ImagemManager imagemManager) {
        this.audioManager = audioManager;
        this.imagemManager = imagemManager;
    }
    
    @Override
    public void run() {
        try {
            imagemManager.carregaAberturaImagem();
            
            if ( listener != null )
                listener.carregouImagemAbertura();
                                                            
            audioManager.carrega( 0, 50 ); 
            imagemManager.carrega( 50, 50 );
        } catch ( CarregandoRecursoException e ) {
            
        }
        if ( listener != null )
            listener.carregou();       
    }
    
    public void setCarregadorListener( MediaCarregadorListener listener ) {
        this.listener = listener;
    }
    
    public AudioManager getAudioManager() {
        return audioManager;
    }

    public ImagemManager getImagemManager() {
        return imagemManager;
    }        
    
}
