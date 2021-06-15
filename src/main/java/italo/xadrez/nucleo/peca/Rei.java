package italo.xadrez.nucleo.peca;

import italo.xadrez.nucleo.RoqueJogo;
import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Rei extends Peca {
    
    @Override
    public void movimentosValidos2( List<int[]> lista, Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                        
        int pid = mat.getValor( i, j );
        for( int k = -1; k <= 1; k++ ) {
            for( int n = -1; n <= 1; n++ ) {
                if ( k == 0 && n == 0 )
                    continue;
                
                if ( i+k >= 0 && i+k < 8 && j+n >= 0 && j+n < 8 ) {
                    int pid2 = mat.getValor( i + k, j + n );
                    if ( pid2 == Const.INT_NULO || !util.mesmaCor( pid, pid2 ) )
                        lista.add( new int[] { i+k, j+n } );                
                }
            }
        }
        
        int cor = util.getPecaCor( pid );                
        RoqueJogo roque = jogo.getRoqueJogo( cor );    
        
        if ( roque.isRoqueEsq( util, mat, roque.getI(), 1 ) )
            lista.add( new int[] { roque.getI(), 1 } );        
        if ( roque.isRoqueDir( util, mat, roque.getI(), 6 ) )
            lista.add( new int[] { roque.getI(), 6 } );                
    }
                    
}
