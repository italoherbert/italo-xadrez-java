package italo.xadrez.nucleo.peca;

import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import java.util.LinkedList;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Torre extends Peca {

    @Override
    public List<int[]> movimentosValidos2( Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                
        LinkedList<int[]> lista = new LinkedList<>();
        
        int pid = mat.getValor( i, j );
        boolean fim = false;
        for( int k = i+1; !fim && k < 8; k++ ) {
            int pid2 = mat.getValor( k, j );
            boolean mesmaCor = util.mesmaCor( pid, pid2 );
            if ( mesmaCor ) {
                fim = true;
            } else {
                lista.add( new int[] { k, j } );                                                                                      
                fim = ( pid2 != Const.INT_NULO ); 
            }
        }        
        fim = false;
        for( int k = i-1; !fim && k >= 0; k-- ) {
            int pid2 = mat.getValor( k, j );
            boolean mesmaCor = util.mesmaCor( pid, pid2 );
            if ( mesmaCor ) {
                fim = true;
            } else {
                lista.add( new int[] { k, j } );                                                                                                      
                fim = ( pid2 != Const.INT_NULO ); 
            }            
        }
        fim = false;
        for( int k = j+1; !fim && k < 8; k++ ) {
            int pid2 = mat.getValor( i, k );
            boolean mesmaCor = util.mesmaCor( pid, pid2 );
            if ( mesmaCor ) {
                fim = true;
            } else {
                lista.add( new int[] { i, k } );                                                                                     
                fim = ( pid2 != Const.INT_NULO );                 
            }
        }
        fim = false;
        for( int k = j-1; !fim && k >= 0; k-- ) {
            int pid2 = mat.getValor( i, k );
            boolean mesmaCor = util.mesmaCor( pid, pid2 );
            if ( mesmaCor ) {
                fim = true;
            } else {
                lista.add( new int[] { i, k } );                                                                                     
                fim = ( pid2 != Const.INT_NULO ); 
            }
        } 
        
        return lista;
    }

}
