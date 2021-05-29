package italo.xadrez.nucleo.move;

import italo.xadrez.Const;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoDriver;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.RoqueJogo;
import italo.xadrez.nucleo.Tabuleiro;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import italo.xadrez.nucleo.mat.Matriz;

public class MoveManager {
    
    public void move( JogoDriver drv, int i1, int j1, int i2, int j2, ThreadSource source, MovimentoConcluidoListener listener ) {                        
        Jogo jogo = drv.getJogo();
        JogoManager jogoManager = drv.getJogoManager();
        Tabuleiro tabuleiro = drv.getTabuleiro();
        PecaIDUtil pecaIDUtil = drv.getPecaIDUtil();  
        MoveuListener moveuListener = drv.getMoveuListener();
        
        Matriz mat = jogo.getMatrizPecas();
        
        int pid = mat.getValor( i1, j1 );        
        int pid2 = mat.getValor( i2, j2 );
                
        Movedor movedor = new Movedor();
        movedor.setJogoManager( jogoManager );
        movedor.setTabuleiro( tabuleiro );            
        movedor.setJogo( jogo );
        movedor.setPecaIDUtil( pecaIDUtil );
        movedor.setMoveuListener( moveuListener );                
                
        movedor.addMovimento( new Movimento( i1, j1, i2, j2 ) );
        
        if ( pid != Const.INT_NULO ) {
            int tipo = pecaIDUtil.getPecaTipo( pid );
            int cor = pecaIDUtil.getPecaCor( pid );
            
            RoqueJogo roque = jogo.getRoqueJogo( cor );
            if ( tipo == ImagemManager.REI ) {
                if ( jogoManager.verificaSeRoqueJogada( roque, pecaIDUtil, mat, i2, j2, cor ) ) {
                    int i3 = roque.getI();
                    int i4 = roque.getI();
                    int j3, j4;
                    if ( j2 == 1 ) {
                        j3 = 0;
                        j4 = 2;
                    } else {
                        j3 = 7;
                        j4 = 5; 
                    }
                    movedor.addMovimento( new Movimento( i3, j3, i4, j4 ) );
                }
            }
        }
                  
        movedor.setMovimentoConcluidoListener( () -> {                                                                                                                                                       
            if ( pid != Const.INT_NULO ) {
                int tipo = pecaIDUtil.getPecaTipo( pid );
                int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                int cor = pecaIDUtil.getPecaCor( pid );

                if ( tipo == ImagemManager.PEAO )
                    if ( dir == 1 ? i2 == 7 : i2 == 0 )
                        mat.setValor( i2, j2, pecaIDUtil.transformaEmRainha( pid ) );                                                                            

                RoqueJogo roque = jogo.getRoqueJogo( cor );
                switch ( tipo ) {
                    case ImagemManager.REI:
                        roque.setMoveuRei( true );
                        break;
                    case ImagemManager.TORRE:
                        if ( j1 == 0 )
                            roque.setMoveuTorreEsq( true );
                        else roque.setMoveuTorreDir( true );
                        break;
                }     
                
                if ( pid2 == Const.INT_NULO ) {
                    jogo.moveu( jogo.getVezJogador() ); 
                } else {
                    jogo.capturou();
                }
            }
            
            jogo.getMatrizPecas().copia( jogo.getMatrizDesenho() );             
            jogo.incQuantJogadas();
                        
            if ( listener != null )
                listener.concluido();
            
            source.libera();
        } );
        movedor.start();                 
    }
    
}
