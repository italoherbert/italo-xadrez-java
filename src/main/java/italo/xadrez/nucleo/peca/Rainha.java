package italo.xadrez.nucleo.peca;

import italo.xadrez.nucleo.Jogo;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Rainha extends Peca {
    
    private final Torre torre = new Torre();
    private final Bispo bispo = new Bispo();

    @Override
    public void movimentosValidos2( List<int[]> lista, Jogo jogo, PecaIDUtil util, Matriz mat, int i, int j, int direcao ) {                                        
        torre.movimentosValidos2( lista, jogo, util, mat, i, j, direcao );
        bispo.movimentosValidos2( lista, jogo, util, mat, i, j, direcao );                        
    }
    
}
