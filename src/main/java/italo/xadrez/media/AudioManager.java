package italo.xadrez.media;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
    
    public final static int ABERTURA_FUNDO = 0;
    public final static int JOGO_FUNDO = 1;
    
    public final static int BRANCAS_MOVEU = 2;
    public final static int PRETAS_MOVEU = 3;
    public final static int CAPTURA = 4;
    public final static int XEQUE = 5;

    public final static int BRANCAS_VENCERAM = 6;
    public final static int PRETAS_VENCERAM = 7;
    public final static int EMPATOU = 8;
    
    private final String[] audioPaths = { 
        "/audio/abertura-fundo.mid",
        "/audio/jogo-fundo.mid",
        
        "/audio/brancas-moveu.wav",
        "/audio/pretas-moveu.wav",
        "/audio/captura.wav",
        "/audio/xeque.wav",
        
        "/audio/brancas-venceram.wav",
        "/audio/pretas-venceram.wav",
        "/audio/empatou.wav"                
    };
    
    private final AudioInputStream[] audioStreams = new AudioInputStream[ audioPaths.length ];
    private final Clip[] clips = new Clip[ audioPaths.length ];
        
    private CarregandoRecursoListener listener;
        
    public void carrega( int offset, int quantPorcent ) throws CarregandoRecursoException {
        for( int i = 0; i < audioPaths.length; i++ ) {           
            try {             
                InputStream in = this.getClass().getResourceAsStream( audioPaths[ i ] );
                BufferedInputStream bufIn = new BufferedInputStream( in );
                audioStreams[ i ] = AudioSystem.getAudioInputStream( bufIn );

                clips[ i ] = AudioSystem.getClip();
                clips[ i ].open( audioStreams[ i ] ); 
            } catch ( UnsupportedAudioFileException | LineUnavailableException | IOException ex ) {
                throw new CarregandoRecursoException( audioPaths[ i ] );
            }
            
            if ( listener != null )
                listener.carregouRecurso( i+1, audioPaths.length, offset, quantPorcent );
        }
        
        if ( listener != null )
            listener.completado( offset, quantPorcent );
    }
    
    public void play( int i ) {
        this.play( i, 0 ); 
    }
    
    public void play( int i, int loop ) {
        clips[ i ].loop( loop );            
        if( clips[ i ].isRunning() ) 
            clips[ i ].stop();
        clips[ i ].setFramePosition( 0 );
        clips[ i ].start();        
    }
    
    public void stop( int i ) {
        if ( clips[ i ].isRunning() )
            clips[ i ].stop();
       
    }
    
    public void setCarregandoAudioListener( CarregandoRecursoListener listener ) {
        this.listener = listener;
    }
    
}
