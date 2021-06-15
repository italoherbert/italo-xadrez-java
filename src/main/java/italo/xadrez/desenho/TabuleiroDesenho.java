package italo.xadrez.desenho;

import italo.xadrez.Const;
import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.Tabuleiro;
import static italo.xadrez.nucleo.Tabuleiro.NULO;
import italo.xadrez.nucleo.mat.Matriz;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class TabuleiroDesenho implements Desenho, Tabuleiro {
    
    private final int tabuleiroMargem = 30;
    private final int tabuleiroBorda = 20;
        
    private final Color celulaSelectColor = new Color( 100, 100, 255 );
                    
    private int selecionadaMatI = NULO;
    private int selecionadaMatJ = NULO;
    
    private int selecionadaDeslocX = 0;
    private int selecionadaDeslocY = 0;
    
    private int tx;
    private int ty;
    private int tw;
    private int th;
    private int cw;
    private int ch;
    
    private final JogoDesenhoDriver drv;

    public TabuleiroDesenho( JogoDesenhoDriver drv ) {
        this.drv = drv;
    }

    @Override
    public void desenha(Graphics2D g2d, Tela tela) {        
        Sistema sistema = drv.getSistema();
        
        JogoManager jogoManager = sistema.getJogoManager();
        ImagemManager imagemManager = sistema.getMediaManager().getImagemManager();
        Jogo jogo = sistema.getJogo();
        
        Matriz mat = jogo.getMatrizDesenho();
        
        List<int[]> movsLista = new LinkedList();
        
        int w = tela.getLargura();
        int h = tela.getAltura();                             
        
        int placarH = drv.getAlturaPlacar();
        
        tw = w - 2*tabuleiroMargem - 2*tabuleiroBorda;
        th = h - placarH - 2*tabuleiroBorda;
                        
        cw = tw / 8;
        ch = th / 8;
        
        tw = cw * 8;
        th = ch * 8;
        
        tx = ( w - tw ) / 2;
        ty = placarH;                
        
        g2d.setColor( Color.GRAY );
        g2d.fillRect( tx-tabuleiroBorda, ty-tabuleiroBorda, tw+2*tabuleiroBorda, th+2*tabuleiroBorda ); 
        
        int cor = Const.INT_NULO;
        if ( selecionadaMatI != NULO ) {           
            int pid = mat.getValor( selecionadaMatI, selecionadaMatJ );
            cor = imagemManager.getPecaCor( pid );

            jogoManager.movimentosValidos( movsLista, jogo, imagemManager, mat, selecionadaMatI, selecionadaMatJ );        
        }        
        
        boolean gray = true;
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {                
                Color color = null;

                boolean ehCapPosic = jogoManager.ehMovePosic( movsLista, i, j );
                if ( jogo.isUsuarioVersusComputador() ) {
                    if ( cor == Jogo.COR_JOGADOR1 && 
                            ( ( i == selecionadaMatI && j == selecionadaMatJ ) || ehCapPosic ) ) {
                        color = celulaSelectColor;                                        
                    }
                }
                
                if ( color == null )
                    color = ( gray ? Color.DARK_GRAY : Color.LIGHT_GRAY );
                
                g2d.setColor( color );
                g2d.fillRect( tx+(j*cw), ty+(i*ch), cw, ch );
                
                int pid = mat.getValor( i, j );
                if ( pid != Const.INT_NULO ) {
                    if ( ehCapPosic && cor == Jogo.COR_JOGADOR1 && jogo.isUsuarioVersusComputador() )
                        pid = imagemManager.converteParaVermelhoPecaID( pid );
                            
                    if ( i != selecionadaMatI || j != selecionadaMatJ )
                        this.desenhaImagem( g2d, pid, i, j, 0, 0 );                    
                }                                                
                gray = !gray;
            }
            gray = !gray;
        }        
        
        if ( cor == Jogo.COR_JOGADOR1 && jogo.isUsuarioVersusComputador() ) {
            g2d.setStroke( new BasicStroke( 2.0f ) );
            g2d.setColor( Color.BLACK );
            for( int i = 0; i < 8; i++ ) {
                for( int j = 0; j < 8; j++ ) {
                    boolean ehCapPosic = jogoManager.ehMovePosic(movsLista, i, j );
                    if ( ( i == selecionadaMatI && j == selecionadaMatJ ) || ehCapPosic ) {
                        g2d.drawRect( tx+(j*cw), ty+(i*ch), cw, ch );                          
                    }
                }
            }        
        }
        
        if ( selecionadaMatI != NULO ) {
            int pid = mat.getValor( selecionadaMatI, selecionadaMatJ );
            this.desenhaImagem( g2d, pid, selecionadaMatI, selecionadaMatJ, selecionadaDeslocX, selecionadaDeslocY );                    
        }        
    }        
            
    private void desenhaImagem( Graphics2D g2d, int pid, int i, int j, int deslocX, int deslocY ) {
        if ( pid == Const.INT_NULO )
            return;
        
        ImagemManager imagemManager = drv.getSistema().getMediaManager().getImagemManager();        
        BufferedImage imagem = imagemManager.getImagem( pid );
                        
        g2d.drawImage( imagem, 
            tx+(j*cw)+5 + deslocX, 
            ty+(i*ch)+5 + deslocY, 
            cw-10, 
            ch-10, 
            null 
        );                
    }
            
    @Override
    public void limpaSelecao() {
        selecionadaMatI = NULO;
        selecionadaMatJ = NULO;
        selecionadaDeslocX = 0;
        selecionadaDeslocY = 0;
    }
        
    @Override
    public void setSelecionadaDesloc( int deslocX, int deslocY ) {
        selecionadaDeslocX = deslocX;
        selecionadaDeslocY = deslocY;
    }

    @Override
    public boolean isTabuleiroPosic(int x, int y) {
        return ( x >= tx && x < tx+tw && y >= ty && y < ty+th );
    }    

    @Override
    public int getTX() {
        return tx;
    }

    @Override
    public int getTY() {
        return ty;
    }

    @Override
    public int getTW() {
        return tw;
    }

    @Override
    public int getTH() {
        return th;
    }

    @Override
    public int getCelulaW() {
        return cw;
    }

    @Override
    public int getCelulaH() {
        return ch;
    }

    @Override
    public int getSelecionadaMatI() {
        return selecionadaMatI;
    }

    @Override
    public void setSelecionadaMatI(int selecionadaMatI) {
        this.selecionadaMatI = selecionadaMatI;
    }

    @Override
    public int getSelecionadaMatJ() {
        return selecionadaMatJ;
    }

    @Override
    public void setSelecionadaMatJ(int selecionadaMatJ) {
        this.selecionadaMatJ = selecionadaMatJ;            
    }
    
}
