package italo.xadrez.algoritmo.computador;

import italo.xadrez.Const;
import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.computador.to.Jogada;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.RoqueJogo;
import italo.xadrez.nucleo.mat.Matriz;
import italo.xadrez.nucleo.peca.Peca;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import java.util.LinkedList;
import java.util.List;

public class ArvoreJogadaBuilder {
    
    private final Sistema sistema;

    public ArvoreJogadaBuilder(Sistema sistema) {
        this.sistema = sistema;
    }
    
    public void constroiArvoreJogada( Jogada jogada, int c, int nivel, boolean max, ArvoreBuilderFinalizador finalizador ) {    
        if ( nivel <= 0 || finalizador.isFinalizar() )
            return;        
                                                        
        JogoManager jogoManager = sistema.getJogoManager();
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();
        Jogo jogo = sistema.getJogo();
                                
        jogo.getMatrizPecas().copia( jogo.getMatrizAux() );
        Matriz mat = jogo.getMatrizAux(); 
        this.carrega( mat, jogada ); 
        
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                if ( finalizador.isFinalizar() )
                    return;
                
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
                                                
                List<int[]> movs = new LinkedList();                                                
                
                peca.movimentosValidos( movs, jogo, jogoManager, pecaIDUtil, mat, i, j, dir );                                                                                                                                    
                for( int[] mov : movs ) {                                                            
                    int movI = mov[ 0 ];
                    int movJ = mov[ 1 ];
                                        
                    int pid2 = mat.getValor( movI, movJ );
                    if ( pid2 != Const.INT_NULO ) {
                        int tipo2 = pecaIDUtil.getPecaTipo( pid2 );
                        if ( tipo2 == ImagemManager.REI )
                            continue;                        
                    }                                                                                                  
                                                
                    mat.setValor( movI, movJ, mat.getValor( i, j ) );
                    mat.setValor( i, j, Const.INT_NULO );
                                                                                      
                    boolean empate = false;
                    boolean xequeMate;
                    boolean roqueJogada;
                    
                    int torrePID3 = Const.INT_NULO;                                        
                    int peso = 0;
                   
                    roqueJogada = jogoManager.verificaSeRoqueJogada( jogo, pecaIDUtil, mat, i, j, movI, movJ );
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

                    xequeMate = jogoManager.sofreuXequeMate( jogo, pecaIDUtil, mat, corOposta );
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
                        this.constroiArvoreJogada( jog, corOposta, nivel-1, !max, finalizador );                                        
                                        
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
                movs.clear();
            }
        }        
    }
    
    public int contaFolhasArvoreJogadas( Matriz mat, int c, int nivel, boolean max ) {    
        if ( nivel <= 0 )
            return 1;
                                                        
        int cont = 0;
        PecaIDUtil pecaIDUtil = sistema.getPecaIDUtil();        
        JogoManager jogoManager = sistema.getJogoManager();
        Jogo jogo = sistema.getJogo();
        
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                int pid = mat.getValor( i, j ); 
                if ( pid == Const.INT_NULO )
                    continue;
                
                int cor = pecaIDUtil.getPecaCor( pid );
                if ( cor != c )
                    continue;
                                                                
                int corOposta = pecaIDUtil.getPecaCorOposta( pid );
                int tipo = pecaIDUtil.getPecaTipo( pid );
                int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                                
                Peca peca = jogoManager.getPeca( tipo );
                                                
                List<int[]> movs = new LinkedList();
                
                peca.movimentosValidos( movs, jogo, jogoManager, pecaIDUtil, mat, i, j, dir );                                                                                                                                    
                for( int[] mov : movs ) {                                                            
                    int movI = mov[ 0 ];
                    int movJ = mov[ 1 ];
                                        
                    int pid2 = mat.getValor( movI, movJ );
                    if ( pid2 != Const.INT_NULO ) {
                        int tipo2 = pecaIDUtil.getPecaTipo( pid2 );
                        if ( tipo2 == ImagemManager.REI )
                            continue;                        
                    }                                                                                                  
                                                
                    mat.setValor( movI, movJ, mat.getValor( i, j ) );
                    mat.setValor( i, j, Const.INT_NULO );                                                                                                
                                                                                                                                                                                                                            
                    cont += this.contaFolhasArvoreJogadas( mat, corOposta, nivel-1, !max );                                        
                                        
                    mat.setValor( i, j, pid );
                    mat.setValor( movI, movJ, pid2 );                                                  
                }
                movs.clear();
            }
        }        
        
        return cont;
    }
                        
    public void carrega( Matriz mat, Jogada jogada ) {        
        LinkedList<Jogada> jogadasList = new LinkedList();
        
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
        jogadasList.clear();
    }
    
}
