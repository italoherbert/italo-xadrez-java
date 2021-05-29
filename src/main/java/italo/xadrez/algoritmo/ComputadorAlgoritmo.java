package italo.xadrez.algoritmo;

import italo.xadrez.Const;
import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.Jogada;
import italo.xadrez.algoritmo.computador.MelhorJogada;
import italo.xadrez.nucleo.move.ThreadSource;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.RoqueJogo;
import italo.xadrez.nucleo.Tabuleiro;
import italo.xadrez.nucleo.peca.Peca;
import java.util.List;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import java.util.LinkedList;
import java.util.Random;
import italo.xadrez.nucleo.mat.Matriz;
import java.util.ArrayList;

public class ComputadorAlgoritmo implements ThreadSource {
        
    public final static int FACIL = 2;
    public final static int MEDIANO = 3;
    public final static int DIFICIL = 4;
    
    private final Sistema sistema;
    private final LinkedList<Jogada> jogadasList = new LinkedList();
    private final Random random = new Random();
        
    private final int jogadorCor;
    private int nivel = DIFICIL;

    private boolean jogando = false;
    private int ultimaPID = Const.INT_NULO;
    private int mesmaPecaCont = 0;
    
    private boolean esperando = false;
        
    public ComputadorAlgoritmo( Sistema sistema, int jogadorCor ) {
        this.sistema = sistema;
        this.jogadorCor = jogadorCor;
    }
    
    public void joga() {
        Jogo jogo = sistema.getJogo();
        if ( jogando || jogo.isFim() )
            return;
            
        jogando = true;
        
        Tabuleiro tabuleiro = sistema.getTabuleiro();
                
        Jogada jogadaRaiz = new Jogada();
        jogadaRaiz.setPeso( 0 ); 
        
        this.executaJogadas( jogadaRaiz, jogadorCor, nivel, true );                        
        
        Jogada melhorJogada = this.melhorJogada( jogadaRaiz, jogadorCor );
        
        System.gc();
                
        int i1 = melhorJogada.getI1();
        int j1 = melhorJogada.getJ1();
        int i2 = melhorJogada.getI2();
        int j2 = melhorJogada.getJ2();
        
        int cor = sistema.getPecaIDUtil().getPecaCor( jogo.getMatrizPecas().getValor( i1, j1 ) );
                 
        tabuleiro.setSelecionadaMatI( i1 );
        tabuleiro.setSelecionadaMatJ( j1 );         
                
        sistema.getMoveManager().move( sistema, i1, j1, i2, j2, this, () -> {
            sistema.getJogoCtrl().processaJogoStatus();                        
                                    
            Matriz mat = jogo.getMatrizPecas();                            

            if ( ultimaPID == mat.getValor( i2, j2 ) )
                mesmaPecaCont++;
            else mesmaPecaCont = 0;

            ultimaPID = mat.getValor( i2, j2 );
            
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
        
    public Jogada melhorJogada( Jogada raiz, int jogadorCor ) {
        MelhorJogada melhorJogada = this.minimax( raiz, 0, true );
        
        int dir = sistema.getPecaIDUtil().getPecaDirecaoPorCor( jogadorCor );
        
        Jogada jogada = melhorJogada.getJogada();
                        
        List<Jogada> lista = raiz.getJogadas();

        List<Jogada> lista2 = new ArrayList();
        for( Jogada jog : lista ) {
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
        
        if ( lista2.isEmpty() )
            for( Jogada jog : lista )
                if ( jog.getPeso() == jogada.getPeso() )
                    lista2.add( jog );                
        
        int size = lista2.size();
        if ( size > 1 ) {
            int n = random.nextInt( size-1 );
            jogada = lista2.get( n );            
        }                    
        
        return jogada;
    }
                
    public MelhorJogada minimax( Jogada jogada, int nivel, boolean max ) {                
        if ( jogada.getJogadas().isEmpty() ) {
            MelhorJogada mj = new MelhorJogada();
            mj.setJogada( jogada ); 
            mj.setPeso( jogada.getPeso() );        
            return mj;
        }
        
        if ( max ) {
            int melhorPeso = Integer.MIN_VALUE;
            Jogada melhorJogada = null;
            for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, !max );                                                                                    
                if ( melhor.getPeso() > melhorPeso ) {
                    melhorJogada = jog;
                    melhorPeso = melhor.getPeso();                
                }
                
                if ( nivel == 0 )
                    jog.setPeso( melhor.getPeso() );
            }            
            MelhorJogada mj = new MelhorJogada();
            mj.setJogada( melhorJogada ); 
            mj.setPeso( melhorPeso );        
            return mj;
        } else {
            int melhorPeso = Integer.MAX_VALUE;
            Jogada melhorJogada = null;
             for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, !max );                                                                                    
                if ( melhor.getPeso() < melhorPeso ) {
                    melhorJogada = jog;
                    melhorPeso = melhor.getPeso();                
                }                
            }            
            MelhorJogada mj = new MelhorJogada();
            mj.setJogada( melhorJogada ); 
            mj.setPeso( melhorPeso );        
            return mj;
        }        
    }
    
    public void executaJogadas( Jogada jogada, int c, int nivel, boolean max ) {       
        if ( nivel <= 0 )
            return;
                        
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();
        Jogo jogo = sistema.getJogo();
                                
        jogo.getMatrizPecas().copia( jogo.getMatrizAux());
        Matriz mat = jogo.getMatrizAux(); 
        this.carrega( mat, jogada ); 
        
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                int pid = mat.getValor( i, j ); 
                if ( pid == Const.INT_NULO )
                    continue;
                
                int cor = pecaIDUtil.getPecaCor( pid );
                if ( cor != c )
                    continue;
                                                
                RoqueJogo roque = jogo.getRoqueJogo( cor );
                
                int corOposta = pecaIDUtil.getPecaCorOposta( pid );
                int tipo = pecaIDUtil.getPecaTipo( pid );
                int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                                
                Peca peca = jogoManager.getPeca( tipo );
                                                
                int[][] movs = peca.movimentosValidos( jogo, jogoManager, pecaIDUtil, mat, i, j, dir );                
                for( int[] mov : movs ) {                                                            
                    int movI = mov[ 0 ];
                    int movJ = mov[ 1 ];
                                        
                    int pid2 = mat.getValor( movI, movJ );
                    if ( pid2 != Const.INT_NULO ) {
                        int tipo2 = pecaIDUtil.getPecaTipo( pid2 );
                        if ( tipo2 == ImagemManager.REI )
                            continue;                        
                    }
                                        
                    int peso = 0; 
                    
                    if ( jogoManager.verificaSeRoqueJogada( roque, pecaIDUtil, mat, movI, movJ, cor ) )
                        peso = 3;                    
                                                
                    mat.setValor( movI, movJ, mat.getValor( i, j ) );
                    mat.setValor( i, j, Const.INT_NULO );
                          
                    boolean empate = false;
                    boolean xequeMate = jogoManager.sofreuXequeMate( jogo, pecaIDUtil, mat, corOposta );
                    if ( xequeMate ) {
                        peso = nivel * 50;                                            
                    } else {                        
                        empate = jogoManager.verificaSeEmpate( jogo, pecaIDUtil, mat, corOposta );
                        if ( empate )
                            peso = -11;                         
                    }
                            
                    
                    if ( pid2 != Const.INT_NULO && !xequeMate && !empate ) {                                            
                        int tipo2 = pecaIDUtil.getPecaTipo( pid2 );
                        int cor2 = pecaIDUtil.getPecaCor( pid2 );                                                                                                                                                                                      

                        int peso2 = 0;
                        if ( cor2 == corOposta ) {
                            switch( tipo2 ) {
                                case ImagemManager.PEAO:
                                    peso2 = 2;
                                    break;                            
                                case ImagemManager.CAVALO:
                                    peso2 = 3;
                                    break;
                                case ImagemManager.BISPO:
                                    peso2 = 4;
                                    break;
                                case ImagemManager.TORRE:
                                    peso2 = 6;
                                    break;                                
                                case ImagemManager.RAINHA:
                                    peso2 = 10;
                                    break;                                                                                                                                
                            }
                        }

                        if ( peso2 > peso )
                            peso = peso2;                                                                                                                                                           
                    }
                    
                    if ( jogada.getPeso() + peso == 0 && peso == 0 ) {
                        if ( tipo == ImagemManager.REI || pid == ultimaPID ) {
                            peso = -1;
                        }
                    }
                                                            
                    Jogada jog = new Jogada();                      
                    jog.setI1( i );
                    jog.setJ1( j );
                    jog.setI2( movI );
                    jog.setJ2( movJ ); 
                    jog.setPeso( jogada.getPeso() + ( max ? peso : -peso ) ); 
                    jogada.addJogada( jog );                                        
                    
                    if ( !xequeMate && !empate )
                        this.executaJogadas( jog, corOposta, nivel-1, !max );
                                        
                    mat.setValor( i, j, pid );
                    mat.setValor( movI, movJ, pid2 );                
                }                
            }
        }
    }
                    
    public void carrega( Matriz mat, Jogada jogada ) {        
        Jogada jog = jogada;
        while( jog.getParente() != null ) {
            jogadasList.addFirst( jog );            
            jog = jog.getParente();
        } 
        
        while( !jogadasList.isEmpty() ) {
            jog = jogadasList.pop();
                    
            mat.setValor( jog.getI2(), jog.getJ2(), mat.getValor( jog.getI1(), jog.getJ1() ) );
            mat.setValor( jog.getI1(), jog.getJ1(), Const.INT_NULO );
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