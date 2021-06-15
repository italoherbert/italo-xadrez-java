package italo.xadrez.algoritmo.computador;

import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.to.Jogada;

public class JogadaRaizGenerator implements Runnable, ArvoreBuilderFinalizador {

    public final static long TEMPO_JOGADA_TOLERANCIA = 12000;

    private final ArvoreJogadaBuilder arvoreBuilder;
    private final long tempoTolerancia = TEMPO_JOGADA_TOLERANCIA;
    
    private boolean parar = false;
    private boolean finalizar = false;
        
    public JogadaRaizGenerator( Sistema sistema ) {
        this.arvoreBuilder = new ArvoreJogadaBuilder( sistema );
    }
    
    public Jogada geraJogadaRaiz( int jogadorCor, int nivel ) {
        Jogada jogadaRaiz = null;
        
        int n = nivel;
        boolean executou = false;
        while( !executou && n > 0 ) {
            Thread t = new Thread( this );
            t.start();
        
            jogadaRaiz = new Jogada();
            jogadaRaiz.setPeso( 0 ); 
                      
            finalizar = false;
                        
            this.arvoreBuilder.constroiArvoreJogada( jogadaRaiz, jogadorCor, n, true, this );            
            this.parar = true;
            
            if ( !this.finalizar ) {                
                executou = true;
            } else {
                n--;
            }
        }    
        return jogadaRaiz;
    }
    
    @Override
    public void run() {
        finalizar = false;
        parar = false;
                
        long ms = System.currentTimeMillis();
        long ms2 = ms;
        while( !parar && (ms2 - ms) <= tempoTolerancia ) {
            try {
                Thread.sleep( 10 ); 
            } catch( InterruptedException e ) {
                
            }
            ms2 = System.currentTimeMillis();
        }
     
        finalizar = ( ms2 - ms ) > tempoTolerancia;
    }

    @Override
    public boolean isFinalizar() {
        return finalizar;
    }
    
}
