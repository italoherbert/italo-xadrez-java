package italo.xadrez.nucleo.peca;

import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.mat.Matriz;

public interface PecaManager {

    public Peca getPeca( int tipo );
    
    public boolean reiEmXeque( Jogo jogo, PecaIDUtil util, Matriz mat, int reiCor );
            
}
