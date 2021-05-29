package italo.xadrez.algoritmo;

import italo.xadrez.Const;
import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.ThreadSourceAlgoritmo;
import italo.xadrez.nucleo.move.ThreadSource;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.Tabuleiro;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import italo.xadrez.nucleo.mat.Matriz;

public class JogadorAlgoritmo implements ThreadSource, ThreadSourceAlgoritmo {
    
    private final Sistema sistema;
    private final ComputadorAlgoritmo computador;
    private boolean jogando = false;
    
    public JogadorAlgoritmo( Sistema sistema, ComputadorAlgoritmo computador ) {
        this.sistema = sistema;
        this.computador = computador;
    }
    
    public void joga( int mouseX, int mouseY ) {
        Jogo jogo = sistema.getJogo();
        if ( jogando || computador.isJogando() || jogo.isFim() )
            return;
        
        JogoManager jogoManager = sistema.getJogoManager();
        Tabuleiro tabuleiro = sistema.getTabuleiro();   
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();
                
        int matI = jogoManager.getMatrizI( tabuleiro, mouseY );
        int matJ = jogoManager.getMatrizJ( tabuleiro, mouseX );
        Matriz mat = sistema.getJogo().getMatrizPecas();
        
        int selecionadaMatI = tabuleiro.getSelecionadaMatI();
        int selecionadaMatJ = tabuleiro.getSelecionadaMatJ();
            
        if ( tabuleiro.getSelecionadaMatI() != Tabuleiro.NULO ) {                        
            int[][] movs = jogoManager.movimentosValidos( jogo, pecaIDUtil, mat, selecionadaMatI, selecionadaMatJ );        
            
            if ( jogoManager.ehMovePosic( movs, matI, matJ ) ) {                                
                int i1 = selecionadaMatI;
                int j1 = selecionadaMatJ;
                int i2 = matI;
                int j2 = matJ;
                                
                jogando = true;
                
                sistema.getMoveManager().move( sistema, i1, j1, i2, j2, this, () -> {
                    sistema.getJogoCtrl().processaJogoStatus();

                    tabuleiro.limpaSelecao();
                    sistema.repaint();
                    
                    jogando = false;
                    if ( !jogo.isFim() )
                        computador.joga();                    
                } );                  
                
            } else {                
                tabuleiro.limpaSelecao();
                if ( matI != selecionadaMatI || matJ != selecionadaMatJ ) {
                    tabuleiro.setSelecionadaMatI( matI );
                    tabuleiro.setSelecionadaMatJ( matJ );
                }
                sistema.repaint();
            }             
        } else if( matI != Const.INT_NULO ) {
            if ( mat.getValor( matI, matJ ) == Const.INT_NULO ) {
                tabuleiro.limpaSelecao();
            } else {
                tabuleiro.setSelecionadaMatI( matI );
                tabuleiro.setSelecionadaMatJ( matJ );
            }
            sistema.repaint();
        }        
    }

    @Override
    public void libera() {
        
    }

    @Override
    public boolean isJogando() {
        return jogando;
    }
    
}
