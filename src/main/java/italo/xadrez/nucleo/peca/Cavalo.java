package italo.xadrez.nucleo.peca;

import italo.xadrez.nucleo.Jogo;
import java.util.LinkedList;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Cavalo extends Peca {

    @Override
    public List<int[]> movimentosValidos2( Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                
        LinkedList<int[]> lista = new LinkedList<>();
        
        int pid = mat.getValor( i, j );
        
        if ( i - 2 >= 0 && j - 1 >= 0 ) {
            int pid2 = mat.getValor( i-2, j-1 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i-2, j-1 } );
        }
        if ( i - 2 >= 0 && j + 1 < 8 ) {
            int pid2 = mat.getValor( i-2, j+1 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i-2, j+1 } );
        }
        if ( i + 2 < 8 && j - 1 >= 0 ) {
            int pid2 = mat.getValor( i+2, j-1 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i+2, j-1 } );
        }
        if ( i + 2 < 8 && j + 1 < 8 ) {
            int pid2 = mat.getValor( i+2, j+1 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i+2, j+1 } );
        }
        if ( j - 2 >= 0 && i - 1 >= 0 ) {
            int pid2 = mat.getValor( i-1, j-2 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i-1, j-2 } );
        }
        if ( j - 2 >= 0 && i + 1 < 8 ) {
            int pid2 = mat.getValor( i+1, j-2 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i+1, j-2 } );
        }
        if ( j + 2 < 8 && i - 1 >= 0 ) {
            int pid2 = mat.getValor( i-1, j+2 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i-1, j+2 } );
        }
        if ( j + 2 < 8 && i + 1 < 8 ) {
            int pid2 = mat.getValor( i+1, j+2 );
            if ( !util.mesmaCor( pid, pid2 ) )
                lista.add( new int[] { i+1, j+2 } );
        }        
        
        return lista;
    }
       
}
