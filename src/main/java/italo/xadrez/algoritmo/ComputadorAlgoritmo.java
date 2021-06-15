package italo.xadrez.algoritmo;

import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.JogadaRaizGenerator;
import italo.xadrez.algoritmo.computador.MelhorJogadaCalculator;
import italo.xadrez.algoritmo.computador.to.Jogada;
import italo.xadrez.nucleo.move.ThreadSource;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.Tabuleiro;

public class ComputadorAlgoritmo implements ThreadSource {
        
    public final static int FACIL = 2;
    public final static int MEDIANO = 3;
    public final static int DIFICIL = 4;
                
    private final Sistema sistema;
        
    private final int jogadorCor;
    private int nivel = DIFICIL;

    private boolean jogando = false;
            
    private boolean esperando = false;
    
    private final JogadaRaizGenerator gen;
    private final MelhorJogadaCalculator melhorJogCalculator;
        
    public ComputadorAlgoritmo( Sistema sistema, int jogadorCor ) {
        this.sistema = sistema;
        this.jogadorCor = jogadorCor;
        
        this.gen = new JogadaRaizGenerator( sistema );
        this.melhorJogCalculator = new MelhorJogadaCalculator( sistema );
    }
    
    public void joga() {
        Jogo jogo = sistema.getJogo();
        if ( jogando || jogo.isFim() )
            return;
            
        jogando = true;
        
        Tabuleiro tabuleiro = sistema.getTabuleiro();
                
        Jogada jogadaRaiz = gen.geraJogadaRaiz( jogadorCor, nivel );        
        Jogada melhorJogada = melhorJogCalculator.melhorJogada( jogadaRaiz, jogadorCor );
        
        System.gc();
                
        int i1 = melhorJogada.getI1();
        int j1 = melhorJogada.getJ1();
        int i2 = melhorJogada.getI2();
        int j2 = melhorJogada.getJ2();
                         
        tabuleiro.setSelecionadaMatI( i1 );
        tabuleiro.setSelecionadaMatJ( j1 );         
                
        sistema.getMoveManager().move( sistema, i1, j1, i2, j2, this, () -> {
            sistema.getJogoCtrl().processaJogoStatus();                        
                                    
            melhorJogCalculator.trataUltimaPenultimaJogada( jogo, i2, j2 );
            
            tabuleiro.limpaSelecao();
            sistema.repaint();
            
            jogando = false;                
        } );          
        
        esperando = true;
        while( esperando ) {
            try {
                synchronized( this ) {
                    wait();
                }
            } catch ( InterruptedException e ) {
                
            }
        }        
    }   
    
    @Override
    public void libera() {
        synchronized( this ) {
            esperando = false;
            notifyAll();
        }
    }                                              
                        
    public boolean isJogando() {
        return jogando;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
            
}