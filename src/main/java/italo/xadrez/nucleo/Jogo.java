package italo.xadrez.nucleo;

import italo.xadrez.Const;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.mat.MatrizPecas;
import italo.xadrez.nucleo.mat.MatrizInicializador;

public class Jogo implements MatrizInicializador {
        
    public final static int MAT_J_LEN = 8;
    public final static int MAT_I_LEN = 8;
    
    public final static int QUANT_JOGADAS_PEOES_PESO_1 = 15;
            
    public final static int COR_JOGADOR1 = ImagemManager.BRANCO;
    public final static int COR_JOGADOR2 = ImagemManager.PRETO;
       
    public final static int EMPATE = -2;
    public final static int NAO_FIM_DO_JOGO = -1;
    public final static int PAUSA = -3;
    public final static int SAIR = -4;
    public final static int CLIQUE_OU_TECLE = -5;
    
    private int status = NAO_FIM_DO_JOGO;    
    
    private int vitorias = 0;
    private int derrotas = 0;
    private int quantJogadas = 0;
    
    private int vezJogador = COR_JOGADOR1;
    
    private final RoqueJogo roqueJogador1 = new RoqueJogo( this, COR_JOGADOR1 );
    private final RoqueJogo roqueJogador2 = new RoqueJogo( this, COR_JOGADOR2 );
    
    private final MatrizPecas matrizPecas = new MatrizPecas( MAT_I_LEN, MAT_J_LEN );
    private final MatrizPecas matrizAux = new MatrizPecas( MAT_I_LEN, MAT_J_LEN );
    private final MatrizPecas matrizDesenho = new MatrizPecas( MAT_I_LEN, MAT_J_LEN );
        
    private boolean usuarioVersusComputador = true;
        
    private JogoListener listener;
                    
    public void inicializa() {
        vitorias = 0;
        derrotas = 0;           
        usuarioVersusComputador = true;  
        
        roqueJogador1.reinicia();
        roqueJogador2.reinicia();
    }
    
    @Override
    public void inicializa( int[][] matriz ) {
        for( int i = 0; i < matriz.length; i++ )
            for( int j = 0; j < matriz[i].length; j++ )
                matriz[ i ][ j ] = Const.INT_NULO;        
        
        matriz[ 7 ][ 0 ] = ImagemManager.BRANCO + ImagemManager.TORRE;
        matriz[ 7 ][ 1 ] = ImagemManager.BRANCO + ImagemManager.CAVALO;
        matriz[ 7 ][ 2 ] = ImagemManager.BRANCO + ImagemManager.BISPO;
        matriz[ 7 ][ 3 ] = ImagemManager.BRANCO + ImagemManager.RAINHA;
        matriz[ 7 ][ 4 ] = ImagemManager.BRANCO + ImagemManager.REI;
        matriz[ 7 ][ 5 ] = ImagemManager.BRANCO + ImagemManager.BISPO;
        matriz[ 7 ][ 6 ] = ImagemManager.BRANCO + ImagemManager.CAVALO;
        matriz[ 7 ][ 7 ] = ImagemManager.BRANCO + ImagemManager.TORRE;
        matriz[ 6 ][ 0 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 1 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 2 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 3 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 4 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 5 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 6 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        matriz[ 6 ][ 7 ] = ImagemManager.BRANCO + ImagemManager.PEAO;
        
        matriz[ 1 ][ 0 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 1 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 2 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 3 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 4 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 5 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 6 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 1 ][ 7 ] = ImagemManager.PRETO + ImagemManager.PEAO;
        matriz[ 0 ][ 0 ] = ImagemManager.PRETO + ImagemManager.TORRE;
        matriz[ 0 ][ 1 ] = ImagemManager.PRETO + ImagemManager.CAVALO;
        matriz[ 0 ][ 2 ] = ImagemManager.PRETO + ImagemManager.BISPO;
        matriz[ 0 ][ 3 ] = ImagemManager.PRETO + ImagemManager.RAINHA;
        matriz[ 0 ][ 4 ] = ImagemManager.PRETO + ImagemManager.REI;
        matriz[ 0 ][ 5 ] = ImagemManager.PRETO + ImagemManager.BISPO;
        matriz[ 0 ][ 6 ] = ImagemManager.PRETO + ImagemManager.CAVALO;
        matriz[ 0 ][ 7 ] = ImagemManager.PRETO + ImagemManager.TORRE;
    }
            
    public void reinicia() {
        status = NAO_FIM_DO_JOGO;        
        vezJogador = COR_JOGADOR1;
        quantJogadas = 0;
                                       
        roqueJogador1.reinicia();
        roqueJogador2.reinicia();
                                
        matrizPecas.inicializa( this ); 
        matrizPecas.copia( matrizDesenho ); 
    }
    
    public void pausaContinua() {
        status = ( status == PAUSA ? NAO_FIM_DO_JOGO : PAUSA );
    }
    
    public void abandonarPartida() {
        status = SAIR;
    }
    
    public void cliqueOuTecle() {
        status = CLIQUE_OU_TECLE;
    }
    
    public void compAlgoritmoFinalizou() { 
        if ( listener != null )
            listener.compAlgoritmoFinalizou();
    }
        
    public void venceu( int vencedorStatus ) {
        status = vencedorStatus;
        
        if ( status == COR_JOGADOR1 ) {           
            vitorias++;
            if ( listener != null )
                listener.brancasVenceram();
        } else if ( status == COR_JOGADOR2 ) {
            derrotas++;
            if ( listener != null )
                listener.pretasVenceram();
        }
    }
    
    public void empate() {
        if ( listener != null )
            listener.empatou();
        status = EMPATE;
    }
    
    public void moveu( int corJogador ) {
        if ( corJogador == COR_JOGADOR1 ) {
            if ( listener != null )
                listener.brancasMoveu();            
        } else {
            if ( listener != null )
                listener.pretasMoveu();
        }
    }
    
    public void capturou() {
        if ( listener != null )
            listener.capturou();
    }
    
    public void xeque() {
        if ( listener != null )
            listener.xeque();
    }
    
    public void vezDoOutro() {        
        vezJogador = ( vezJogador == COR_JOGADOR1 ? COR_JOGADOR2 : COR_JOGADOR1 );
    }
        
    public void incQuantJogadas() {
        quantJogadas++;
    }
        
    public boolean isFim() {
        return status != NAO_FIM_DO_JOGO && status != PAUSA;
    }

    public void setJogoListener( JogoListener listener ) {
        this.listener = listener;
    }
    
    public MatrizPecas getMatrizPecas() {
        return matrizPecas;
    }

    public MatrizPecas getMatrizAux() {
        return matrizAux;
    }

    public MatrizPecas getMatrizDesenho() {
        return matrizDesenho;
    }
            
    public RoqueJogo getRoqueJogo( int corJogador ) {
        if ( corJogador == Jogo.COR_JOGADOR1 )
            return roqueJogador1;
        return roqueJogador2;
    }
                                                   
    public int getStatus() {
        return status;
    }
                      
    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getVezJogador() {
        return vezJogador;
    }

    public void setVezJogador(int vezJogador) {
        this.vezJogador = vezJogador;
    }

    public boolean isUsuarioVersusComputador() {
        return usuarioVersusComputador;
    }

    public void setUsuarioVersusComputador(boolean usuarioVersusComputador) {
        this.usuarioVersusComputador = usuarioVersusComputador;
    }    

}
