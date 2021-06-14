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
import italo.xadrez.nucleo.mat.MatrizPecas;
import java.util.ArrayList;

public class ComputadorAlgoritmo implements ThreadSource {
        
    public final static int FACIL = 2;
    public final static int MEDIANO = 3;
    public final static int DIFICIL = 4;
    
    public final static int LIMIAR_MAX_FILHOS = 10000;
        
    private final Sistema sistema;
    private final LinkedList<Jogada> jogadasList = new LinkedList();
    private final Random random = new Random();
        
    private final int jogadorCor;
    private int nivel = DIFICIL;

    private boolean jogando = false;
    private int ultimaI = Const.INT_NULO;
    private int ultimaJ = Const.INT_NULO;
    private int penultimaI = Const.INT_NULO;
    private int penultimaJ = Const.INT_NULO;
    private int ultimaPID = Const.INT_NULO;    
    
    private boolean esperando = false;
    private boolean calculoMelhorJogadaMuitoLongo = false;
        
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
                
        Jogada melhorJogada = this.melhorJogada();
        
        System.gc();
                
        int i1 = melhorJogada.getI1();
        int j1 = melhorJogada.getJ1();
        int i2 = melhorJogada.getI2();
        int j2 = melhorJogada.getJ2();
                         
        tabuleiro.setSelecionadaMatI( i1 );
        tabuleiro.setSelecionadaMatJ( j1 );         
                
        sistema.getMoveManager().move( sistema, i1, j1, i2, j2, this, () -> {
            sistema.getJogoCtrl().processaJogoStatus();                        
                                    
            penultimaI = ultimaI;
            penultimaJ = ultimaJ;
            ultimaI = i2;
            ultimaJ = j2;
            
            ultimaPID = jogo.getMatrizPecas().getValor( i2, j2 );
            
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
                    
    public Jogada melhorJogada() {                
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();

        Jogo jogo = sistema.getJogo();
        MatrizPecas mat = jogo.getMatrizPecas();
        int dir = pecaIDUtil.getPecaDirecaoPorCor( jogadorCor );
        
        MelhorJogada alpha = new MelhorJogada();
        alpha.setPeso( Integer.MIN_VALUE );
        
        MelhorJogada beta = new MelhorJogada();
        beta.setPeso( Integer.MAX_VALUE );
        
        Jogada raiz = this.jogadaRaiz();        

        MelhorJogada melhorJogada = this.minimax( raiz, 0, alpha, beta, true );
                
        Jogada jogada = melhorJogada.getJogada();
                                                
        List<Jogada> lista = raiz.getJogadas();
        
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
    
    public Jogada jogadaRaiz() {                
        Jogada jogadaRaiz = null; 
         
        this.calculoMelhorJogadaMuitoLongo = true;
        int n = nivel;
        while ( this.calculoMelhorJogadaMuitoLongo ) {
            jogadaRaiz = new Jogada();
            jogadaRaiz.setPeso( 0 );
            
            this.calculoMelhorJogadaMuitoLongo = false;
            this.executaJogadas( jogadaRaiz, jogadorCor, n, true, 1 );             
            n--;
        }
        
        return jogadaRaiz;
    }
                
    public MelhorJogada minimax( Jogada jogada, int nivel, MelhorJogada alpha, MelhorJogada beta, boolean maximizador ) {                
        if ( jogada.getJogadas().isEmpty() ) {
            MelhorJogada mj = new MelhorJogada();
            mj.setJogada( jogada ); 
            mj.setPeso( jogada.getPeso() );        
            return mj;
        }
        
        if ( maximizador ) {
            MelhorJogada v = new MelhorJogada();
            v.setPeso( Integer.MIN_VALUE ); 
            v.setJogada( jogada.getJogadas().get( 0 ) );
            
            for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, alpha, beta, !maximizador );                                                                                                                                    
                if ( melhor.getPeso() > v.getPeso() ) {                    
                    MelhorJogada mj = new MelhorJogada();
                    mj.setJogada( jog ); 
                    mj.setPeso( melhor.getPeso() );
                    v = mj;                                                                                           
                }                                              
                
                if ( nivel == 0 ) {
                    jog.setPeso( melhor.getPeso() );
                } else {                                                                                                      
                    if ( v.getPeso() > alpha.getPeso() )
                        alpha = v;
                    
                    if ( v.getPeso() >= beta.getPeso() )
                        break;
                }
            }                                
            return v;
        } else {
            MelhorJogada v = new MelhorJogada();
            v.setPeso( Integer.MAX_VALUE ); 
            v.setJogada( jogada.getJogadas().get( 0 ) );

            for( Jogada jog : jogada.getJogadas() ) {
                MelhorJogada melhor = this.minimax( jog, nivel+1, alpha, beta, !maximizador );                                                                                                                                                                                   
                                
                if ( melhor.getPeso() < v.getPeso() ) {                    
                    MelhorJogada mj = new MelhorJogada();
                    mj.setJogada( jog ); 
                    mj.setPeso( melhor.getPeso() );
                    v = mj;                                                                           
                }                                         

                if ( v.getPeso() < beta.getPeso() )
                    beta = v;
                
                if ( v.getPeso() <= alpha.getPeso() )
                    break;                                
            }                               
            return v;
        }        
    }
    
    public void executaJogadas( Jogada jogada, int c, int nivel, boolean max, int quant ) {    
        if ( nivel <= 0 || calculoMelhorJogadaMuitoLongo )
            return;
                                                        
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();
        Jogo jogo = sistema.getJogo();
                                
        jogo.getMatrizPecas().copia( jogo.getMatrizAux() );
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
                if ( ( quant * movs.length ) > LIMIAR_MAX_FILHOS ) {
                    calculoMelhorJogadaMuitoLongo = true;
                    return;                              
                }
                
                for( int[] mov : movs ) {                                                            
                    int movI = mov[ 0 ];
                    int movJ = mov[ 1 ];
                                        
                    int pid2 = mat.getValor( movI, movJ );
                    if ( pid2 != Const.INT_NULO ) {
                        int tipo2 = pecaIDUtil.getPecaTipo( pid2 );
                        if ( tipo2 == ImagemManager.REI )
                            continue;                        
                    }
                    
                    boolean roqueJogada = jogoManager.verificaSeRoqueJogada( jogo, pecaIDUtil, mat, i, j, movI, movJ );
                    int torrePID3 = Const.INT_NULO;
                                        
                    int peso = 0;                                                           
                                                
                    mat.setValor( movI, movJ, mat.getValor( i, j ) );
                    mat.setValor( i, j, Const.INT_NULO );
                    
                    if ( roqueJogada ) {
                        roque.setMoveuRei( true );                        
                        int roqueI = roque.getI();
                        if ( movJ == 1 ) {
                            roque.setMoveuTorreEsq( true ); 
                            
                            torrePID3 = mat.getValor( roqueI, 0 );
                            mat.setValor( roqueI, 2, torrePID3 );
                            mat.setValor( roqueI, 0, Const.INT_NULO ); 
                        } else {
                            roque.setMoveuTorreDir( true ); 

                            torrePID3 = mat.getValor( roqueI, 7 );
                            mat.setValor( roqueI, 5, torrePID3 );
                            mat.setValor( roqueI, 7, Const.INT_NULO ); 
                        }
                    }
                                              
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
                    
                    if ( jogada.getPeso() + peso == 0 && peso == 0 )
                        if ( tipo == ImagemManager.REI )
                            peso = -1;                                                                            
                                                                                                                                            
                    Jogada jog = new Jogada();                      
                    jog.setI1( i );
                    jog.setJ1( j );
                    jog.setI2( movI );
                    jog.setJ2( movJ ); 
                    jog.setPeso( jogada.getPeso() + ( max ? peso : -peso ) ); 
                    jogada.addJogada( jog );                                        
                    
                    if ( !xequeMate && !empate )
                        this.executaJogadas( jog, corOposta, nivel-1, !max, quant * movs.length );                                        
                                        
                    mat.setValor( i, j, pid );
                    mat.setValor( movI, movJ, pid2 );                
                    
                    if ( roqueJogada ) {
                        roque.setMoveuRei( false ); 

                        int roqueI = roque.getI();
                        if ( movJ == 1 ) {
                            roque.setMoveuTorreEsq( false );                             
                            mat.setValor( roqueI, 0, torrePID3 ); 
                            mat.setValor( roqueI, 2, Const.INT_NULO );
                        } else {
                            roque.setMoveuTorreDir( false ); 
                            mat.setValor( roqueI, 7, torrePID3 );
                            mat.setValor( roqueI, 5, Const.INT_NULO ); 
                        }
                    }
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