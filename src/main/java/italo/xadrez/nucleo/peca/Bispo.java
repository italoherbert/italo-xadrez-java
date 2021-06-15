package italo.xadrez.nucleo.peca;

import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Bispo extends Peca {

    @Override
    public void movimentosValidos2( List<int[]> lista, Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                        
        int pid = mat.getValor( i, j );
        
        boolean fim = false;
        for( int k = 1; !fim; k++ ) {
            if ( i+k < 8 && j+k < 8 ) {
                int pid2 = mat.getValor( i+k, j+k );
                boolean mesmaCor = util.mesmaCor( pid, pid2 );
                if ( mesmaCor ) {
                    fim = true;
                } else {
                    lista.add( new int[] { i+k, j+k } );                            
                    fim = ( pid2 != Const.INT_NULO );                    
                }
            } else {
                fim = true;
            }
        }
        fim = false;
        for( int k = 1; !fim; k++ ) {
            if ( i-k >= 0 && j-k >= 0 ) {
                int pid2 = mat.getValor( i-k, j-k );
                boolean mesmaCor = util.mesmaCor( pid, pid2 );
                if ( mesmaCor ) {
                    fim = true;
                } else {
                    lista.add( new int[] { i-k, j-k } );                                                                    
                    fim = ( pid2 != Const.INT_NULO );                       
                }
            } else {
                fim = true;
            }
        }
        fim = false;
        for( int k = 1; !fim; k++ ) {
            if ( i+k < 8 && j-k >= 0 ) {
                int pid2 = mat.getValor( i+k, j-k );
                boolean mesmaCor = util.mesmaCor( pid, pid2 );
                if ( mesmaCor ) {
                    fim = true;
                } else {
                    lista.add( new int[] { i+k, j-k } );                                                                    
                    fim = ( pid2 != Const.INT_NULO ); 
                }
            } else {
                fim = true;
            } 
        }
        fim = false;
        for( int k = 1; !fim; k++ ) {
            if ( i-k >= 0 && j+k < 8 ) {
                int pid2 = mat.getValor( i-k, j+k );
                boolean mesmaCor = util.mesmaCor( pid, pid2 );
                if ( mesmaCor ) {
                    fim = true;
                } else {
                    lista.add( new int[] { i-k, j+k } );                                                                                                
                    fim = ( pid2 != Const.INT_NULO );                     
                }
            } else {
                fim = true;
            } 
        }                
    }

}
