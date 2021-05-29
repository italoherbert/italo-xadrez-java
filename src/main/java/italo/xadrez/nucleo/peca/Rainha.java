package italo.xadrez.nucleo.peca;

import italo.xadrez.nucleo.Jogo;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Rainha extends Peca {
    
    private final Torre torre = new Torre();
    private final Bispo bispo = new Bispo();

    @Override
    public List<int[]> movimentosValidos2( Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                        
        List<int[]> lista1 = torre.movimentosValidos2( jogo, util, mat, i, j, direcao );
        List<int[]> lista2 = bispo.movimentosValidos2( jogo, util, mat, i, j, direcao );        
        
        lista1.addAll( lista2 );
        
        return lista1;
    }
    
}
