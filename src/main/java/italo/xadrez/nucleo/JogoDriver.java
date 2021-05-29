package italo.xadrez.nucleo;

import italo.xadrez.nucleo.move.MoveuListener;
import italo.xadrez.nucleo.peca.PecaIDUtil;

public interface JogoDriver {
    
    public Tabuleiro getTabuleiro();
    
    public PecaIDUtil getPecaIDUtil();
    
    public Jogo getJogo();
    
    public JogoManager getJogoManager();
    
    public MoveuListener getMoveuListener();
    
}
