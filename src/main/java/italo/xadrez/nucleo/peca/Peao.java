package italo.xadrez.nucleo.peca;

import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import java.util.LinkedList;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Peao extends Peca {

    @Override
    public List<int[]> movimentosValidos2( Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                
        LinkedList<int[]> lista = new LinkedList<>();
        
        int pid = mat.getValor( i, j );        
        
        if ( i + direcao >= 0 && i + direcao < 8 ) {
            int pid2 = mat.getValor( i + direcao, j );
            if ( pid2 == Const.INT_NULO ) {
                lista.add( new int[] { i + direcao, j } );                                    
                
                if ( direcao == 1 ? i == 1 : i == 6 ) {
                    pid2 = mat.getValor( i + ( 2*direcao ), j );
                    if ( pid2 == Const.INT_NULO )
                        lista.add( new int[] { i + (2*direcao), j } );                
                }                                
            }                                    
            
            if ( j-1 >= 0 ) {
                pid2 = mat.getValor( i + direcao, j-1 );
                if ( pid2 != Const.INT_NULO && !util.mesmaCor( pid, pid2 ) )
                    lista.add( new int[] { i + direcao, j-1 } );
            }

            if ( j+1 < 8 ) {
                pid2 = mat.getValor( i + direcao, j+1 );
                if ( pid2 != Const.INT_NULO && !util.mesmaCor( pid, pid2 ) )
                    lista.add( new int[] { i + direcao, j+1 } );
            }
        }                       
        
        return lista;
    }
    
}
