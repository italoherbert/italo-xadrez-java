package italo.xadrez.algoritmo.computador;

import italo.xadrez.Const;
import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.to.Jogada;
import italo.xadrez.algoritmo.computador.to.MelhorJogada;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.mat.MatrizPecas;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MelhorJogadaCalculator {
    
    private final Sistema sistema;
    private final Minimax minimax = new Minimax();
    private final Random random = new Random();
    
    private int ultimaI = Const.INT_NULO;
    private int ultimaJ = Const.INT_NULO;
    private int penultimaI = Const.INT_NULO;
    private int penultimaJ = Const.INT_NULO;
    private int ultimaPID = Const.INT_NULO;
    
    public MelhorJogadaCalculator( Sistema sistema ) {
        this.sistema = sistema;
    }
    
    public void trataUltimaPenultimaJogada( Jogo jogo, int i2, int j2 ) {
        penultimaI = ultimaI;
        penultimaJ = ultimaJ;
        ultimaI = i2;
        ultimaJ = j2;
        
        ultimaPID = jogo.getMatrizPecas().getValor( i2, j2 );        
    }
    
    public Jogada melhorJogada( Jogada jogadaRaiz, int jogadorCor ) {                
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();

        Jogo jogo = sistema.getJogo();
        MatrizPecas mat = jogo.getMatrizPecas();
        int dir = pecaIDUtil.getPecaDirecaoPorCor( jogadorCor );
        
        MelhorJogada alpha = new MelhorJogada();
        alpha.setPeso( Integer.MIN_VALUE );
        
        MelhorJogada beta = new MelhorJogada();
        beta.setPeso( Integer.MAX_VALUE );
                
        MelhorJogada melhorJogada = minimax.minimax( jogadaRaiz, 0, alpha, beta, true );
                
        Jogada jogada = melhorJogada.getJogada();
                                                
        List<Jogada> lista = jogadaRaiz.getJogadas();
        
        for( Jogada jog : lista ) {
            int i1 = jog.getI1();
            int j1 = jog.getJ1();
            int i2 = jog.getI2();
            int j2 = jog.getJ2();
            
            boolean ehRoqueJogada = jogoManager.verificaSeRoqueJogada( jogo, pecaIDUtil, mat, i1, j1, i2, j2 );
            if ( jogada.getPeso() == jog.getPeso() && ehRoqueJogada )
                return jog;
        }

        List<Jogada> lista2 = new ArrayList();
        for( Jogada jog : lista ) {
            int i1 = jog.getI1();
            int j1 = jog.getJ1();
            int i2 = jog.getI2();
            int j2 = jog.getJ2();
            
            if ( jog.getPeso() < jogada.getPeso() )
                continue;

            if ( i2 == penultimaI && j2 == penultimaJ )
                continue;                                        

            int pid = mat.getValor( i1, j1 );
            int tipo = pecaIDUtil.getPecaTipo( pid );
            if ( pid == ultimaPID && tipo != ImagemManager.PEAO )
                continue;

            if ( jog.getPeso() == jogada.getPeso() ) {
                if ( dir == 1 ) {
                    if ( jog.getI2() > jog.getI1() )
                        lista2.add( jog );                    
                } else {
                    if ( jog.getI2() < jog.getI1() )
                        lista2.add( jog );                        
                }                
            }
        }        
        
        if ( lista2.isEmpty() ) {
            for( Jogada jog : lista ) {                
                int i2 = jog.getI2();
                int j2 = jog.getJ2();
                if ( i2 == penultimaI && j2 == penultimaJ )
                    continue;                                 
                
                if ( jog.getPeso() == jogada.getPeso() )
                    lista2.add( jog ); 
            }
        }
        
        int size = lista2.size();
        if ( size > 1 ) {
            int n = random.nextInt( size-1 );
            jogada = lista2.get( n );                        
        } else if ( size == 1 ) {
            jogada = lista2.get( 0 );
        } else {
            size = lista.size();
            if ( size > 1 ) {
                int n = random.nextInt( size-1 );
                jogada = lista.get( n );
            }
        }                   
        
        return jogada;
    }
    
}
