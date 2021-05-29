package italo.xadrez.nucleo.move;

import italo.xadrez.Const;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.Tabuleiro;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import java.util.ArrayList;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class Movedor extends Thread {
    
    private JogoManager jogoManager;
    private PecaIDUtil pecaIDUtil;
    private Jogo jogo;
    private Tabuleiro tabuleiro;
    
    private final List<Movimento> movimentos = new ArrayList();
    
    private MoveuListener moveuListener = null;
    private MovimentoConcluidoListener movimentoConcluidoListener = null;
    
    @Override
    public void run() {
        Matriz mat = jogo.getMatrizPecas();
        for( Movimento m : movimentos ) {
            int i1 = m.getI1();
            int j1 = m.getJ1();
            int i2 = m.getI2();
            int j2 = m.getJ2();
            
            tabuleiro.setSelecionadaMatI( i1 );
            tabuleiro.setSelecionadaMatJ( j1 );
            
            this.move( jogoManager, tabuleiro, i1, j1, i2, j2 );
            
            mat.setValor( i2, j2, mat.getValor( i1, j1 ) );
            mat.setValor( i1, j1, Const.INT_NULO );
            
            mat.copia( jogo.getMatrizDesenho() ); 
        }
         
        if ( movimentoConcluidoListener != null )
            movimentoConcluidoListener.concluido();
    }
         
    public void move( JogoManager jogoManager, Tabuleiro tabuleiro, int i1, int j1, int i2, int j2 ) {
        int x1 = jogoManager.getX( tabuleiro, j1 ); 
        int y1 = jogoManager.getY( tabuleiro, i1 ); 
        int x2 = jogoManager.getX( tabuleiro, j2 );
        int y2 = jogoManager.getY( tabuleiro, i2 );
                
        double r = Math.sqrt( Math.pow( x2-x1, 2 ) + Math.pow( y2-y1, 2 ) );
                
        for( double k = 0; k <= r; k++ ) {
            double ang = Math.atan2( y2 - y1, x2 - x1 );
            int deslocX = (int)Math.round( k * Math.cos( ang ) );
            int deslocY = (int)Math.round( k * Math.sin( ang ) );
                        
            try {
                Thread.sleep( 5 );
            } catch ( InterruptedException e ) {
                
            }            
            tabuleiro.setSelecionadaDesloc( deslocX, deslocY );
            
            if ( moveuListener != null ) 
                moveuListener.moveu();
        }         
    }
    
    public void addMovimento( Movimento mov ) {
        movimentos.add( mov );
    }
    
    public void setMoveuListener( MoveuListener listener ) {
        this.moveuListener = listener;
    }

    public void setMovimentoConcluidoListener( MovimentoConcluidoListener listener ) {
        this.movimentoConcluidoListener = listener;
    }
    
    public JogoManager getJogoManager() {
        return jogoManager;
    }

    public void setJogoManager(JogoManager jogoManager) {
        this.jogoManager = jogoManager;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public PecaIDUtil getPecaIDUtil() {
        return pecaIDUtil;
    }

    public void setPecaIDUtil(PecaIDUtil pecaIDUtil) {
        this.pecaIDUtil = pecaIDUtil;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

}
