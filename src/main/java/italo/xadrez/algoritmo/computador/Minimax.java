package italo.xadrez.algoritmo.computador;

import italo.xadrez.algoritmo.computador.to.MelhorJogada;
import italo.xadrez.algoritmo.computador.to.Jogada;

public class Minimax {
    
    public MelhorJogada minimax( Jogada jogada, int nivel, MelhorJogada alpha, MelhorJogada beta, boolean maximizador ) {                
        if ( jogada.getJogadas().isEmpty() ) {
            MelhorJogada mj = new MelhorJogada();
            mj.setJogada( jogada ); 
            mj.setPeso( jogada.getPeso() );        
            return mj;
        }
        
        if ( maximizador ) {
            MelhorJogada v = new MelhorJogada();
            v.setPeso( Integer.MIN_VALUE ); 
            v.setJogada( jogada.getJogadas().get( 0 ) );
            
            for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, alpha, beta, !maximizador );                                                                                                                                    
                if ( melhor.getPeso() > v.getPeso() ) {                    
                    MelhorJogada mj = new MelhorJogada();
                    mj.setJogada( jog ); 
                    mj.setPeso( melhor.getPeso() );
                    v = mj;                                                                                           
                }                                              
                
                if ( nivel == 0 ) {
                    jog.setPeso( melhor.getPeso() );
                } else {                                                                                                      
                    if ( v.getPeso() > alpha.getPeso() )
                        alpha = v;
                    
                    if ( v.getPeso() >= beta.getPeso() )
                        break;
                }
            }                                
            return v;
        } else {
            MelhorJogada v = new MelhorJogada();
            v.setPeso( Integer.MAX_VALUE ); 
            v.setJogada( jogada.getJogadas().get( 0 ) );

            for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, alpha, beta, !maximizador );                                                                                                                                                                                   
                                
                if ( melhor.getPeso() < v.getPeso() ) {                    
                    MelhorJogada mj = new MelhorJogada();
                    mj.setJogada( jog ); 
                    mj.setPeso( melhor.getPeso() );
                    v = mj;                                                                           
                }                                         

                if ( v.getPeso() < beta.getPeso() )
                    beta = v;
                
                if ( v.getPeso() <= alpha.getPeso() )
                    break;                                
            }                               
            return v;
        }        
    }
    
}
