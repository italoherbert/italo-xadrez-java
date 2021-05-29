package italo.xadrez.nucleo.peca;

import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public abstract class Peca {                     
        
    public abstract List<int[]> movimentosValidos2( Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao );
            
    public int[][] movimentosValidos( Jogo jogo, JogoManager manager, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                        
        List<int[]> lista = this.movimentosValidos2( jogo, util, mat, i, j, direcao );
                
        int pid = mat.getValor( i, j );                         
        int c = util.getPecaCor( pid );
                     
        int len = lista.size();        
        for( int k = 0; k < len; k++ ) {
            int[] p = lista.get( k );

            int pid2 = mat.getValor( p[0], p[1] );

            mat.setValor( p[0], p[1], mat.getValor( i, j ) );
            mat.setValor( i, j, Const.INT_NULO );
                        
            if ( manager.reiEmXeque( jogo, util, mat, c ) ) {                
                lista.remove( k );
                k--;
                len--;
            }
            
            mat.setValor( i, j, pid );
            mat.setValor( p[0], p[1], pid2 );
        }         
        
        return lista.toArray( new int[ len ][] );
    }
               
}
