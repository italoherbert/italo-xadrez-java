package italo.xadrez.ctrl;

import italo.xadrez.Sistema;
import italo.xadrez.media.AudioManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import italo.xadrez.nucleo.mat.Matriz;

public class JogoCtrl {

    private final Sistema sistema;
    
    public JogoCtrl( Sistema sistema ) {
        this.sistema = sistema;
    }
        
    public void processaJogoStatus() {
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();        
        Jogo jogo = sistema.getJogo();
        
        Matriz mat = jogo.getMatrizPecas();
        
        boolean venceu = jogoManager.sofreuXequeMate( jogo, pecaIDUtil, mat, Jogo.COR_JOGADOR2 );
        
        if ( jogo.getStatus() != Jogo.SAIR ) {
            if ( venceu ) {
                jogo.venceu( Jogo.COR_JOGADOR1 );            
            } else {
                boolean perdeu = jogoManager.sofreuXequeMate( jogo, pecaIDUtil, mat, Jogo.COR_JOGADOR1 );
                if ( perdeu )
                    jogo.venceu( Jogo.COR_JOGADOR2 );

                if ( !perdeu ) {
                    int outroJogador = pecaIDUtil.getCorOposta( jogo.getVezJogador() );
                    boolean empate = jogoManager.verificaSeEmpate( jogo, pecaIDUtil, mat, outroJogador );

                    if ( empate ) {
                        jogo.empate();
                    } else {
                        boolean xeque = jogoManager.reiEmXeque( jogo, pecaIDUtil, mat, Jogo.COR_JOGADOR1 );
                        if ( !xeque )
                            xeque = jogoManager.reiEmXeque( jogo, pecaIDUtil, mat, Jogo.COR_JOGADOR2 );

                        if ( xeque )
                            jogo.xeque();
                    }
                }
            }

            jogo.vezDoOutro();
        }               
    }
    
    public void paraTelaMenu() {
        sistema.getMediaManager().getAudioManager().stop( AudioManager.JOGO_FUNDO );        
        sistema.getMediaManager().getAudioManager().play( AudioManager.ABERTURA_FUNDO, -1 );
        
        sistema.getGUI().getJanelaGUI().showMenuPNL();
    }
        
}
