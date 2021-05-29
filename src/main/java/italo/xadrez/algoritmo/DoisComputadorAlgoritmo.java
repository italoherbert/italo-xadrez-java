package italo.xadrez.algoritmo;

import italo.xadrez.Sistema;
import italo.xadrez.nucleo.Jogo;

public class DoisComputadorAlgoritmo implements Runnable {
        
    private final Sistema sistema;
    
    public DoisComputadorAlgoritmo( Sistema sistema ) {
        this.sistema = sistema;
    }        
            
    @Override
    public void run() {        
        sistema.getJogo().setUsuarioVersusComputador( false ); 
                                  
        while( !sistema.getJogo().isFim() ) {
            if ( sistema.getJogo().getStatus() == Jogo.PAUSA ) {
                try { 
                    Thread.sleep( 30 );
                } catch (InterruptedException ex) {
                    
                }
                sistema.getGUI().getJanelaGUI().getJogoPNL().repaint();
            } else {
                if ( sistema.getJogo().getVezJogador() == Jogo.COR_JOGADOR1 )
                    sistema.getComputador1().joga(); 
                else sistema.getComputador2().joga();            
            }
        }
        
        sistema.getJogo().compAlgoritmoFinalizou();
    }
    
}
