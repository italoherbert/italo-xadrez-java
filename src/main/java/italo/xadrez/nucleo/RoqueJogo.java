package italo.xadrez.nucleo;

import italo.xadrez.Const;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.mat.Matriz;
import italo.xadrez.nucleo.peca.PecaIDUtil;

public class RoqueJogo {
    
    private boolean moveuRei = false;
    private boolean moveuTorreEsq = false;
    private boolean moveuTorreDir = false;

    private final int i;
    private final int corJogador;
    
    public RoqueJogo( Jogo jogo, int cor ) {
        this.corJogador = cor;
        this.i = ( cor == ImagemManager.PRETO ? 0 : 7 );                           
        
        this.reinicia();
    }
    
    public final void reinicia() {
        this.moveuRei = false;
        this.moveuTorreDir = false;
        this.moveuTorreEsq = false;
    }
    
    public int getI() {
        return i;
    }

    public boolean isRoqueEsq(  PecaIDUtil util, Matriz mat ) {              
        return !moveuRei && !moveuTorreEsq && 
                util.getPecaTipo( mat.getValor( i, 0 ) ) == ImagemManager.TORRE &&
                mat.getValor( i, 1 ) == Const.INT_NULO &&
                mat.getValor( i, 2 ) == Const.INT_NULO && 
                mat.getValor( i, 3 ) == Const.INT_NULO;
    }

    public boolean isRoqueDir( PecaIDUtil util, Matriz mat ) {
        return !moveuRei && !moveuTorreDir && 
                util.getPecaTipo( mat.getValor( i, 7 ) ) == ImagemManager.TORRE &&
                mat.getValor( i, 5 ) == Const.INT_NULO &&
                mat.getValor( i, 6 ) == Const.INT_NULO;
    }

    public int getCorJogador() {
        return corJogador;
    }

    public boolean isMoveuRei() {
        return moveuRei;
    }

    public void setMoveuRei(boolean moveuRei) {
        this.moveuRei = moveuRei;
    }

    public boolean isMoveuTorreEsq() {
        return moveuTorreEsq;
    }

    public void setMoveuTorreEsq(boolean moveuTorreEsq) {
        this.moveuTorreEsq = moveuTorreEsq;
    }

    public boolean isMoveuTorreDir() {
        return moveuTorreDir;
    }

    public void setMoveuTorreDir(boolean moveuTorreDir) {
        this.moveuTorreDir = moveuTorreDir;
    }
    
}
