package italo.xadrez.media;

import italo.xadrez.Const;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import static italo.xadrez.Const.INT_NULO;
import javax.swing.JPanel;

public class ImagemManager implements PecaIDUtil {
        
    public final static int PEAO = 0; 
    public final static int TORRE = 1; 
    public final static int CAVALO = 2; 
    public final static int BISPO = 3; 
    public final static int RAINHA = 4; 
    public final static int REI = 5; 
    
    public final static int BRANCO = 0;
    public final static int PRETO = 6;
    public final static int VERMELHO = 12;
    
    public final static int ABERTURA = 18;
    
    private final String[] imagensPaths = {        
        "/img/peao-branco.png",
        "/img/torre-branca.png",
        "/img/cavalo-branco.png",
        "/img/bispo-branco.png",
        "/img/rainha-branca.png",
        "/img/rei-branco.png",
        
        "/img/peao-preto.png",
        "/img/torre-preta.png",
        "/img/cavalo-preto.png",
        "/img/bispo-preto.png",
        "/img/rainha-preta.png",
        "/img/rei-preto.png",

        "/img/peao-vermelho.png",
        "/img/torre-vermelha.png",
        "/img/cavalo-vermelho.png",
        "/img/bispo-vermelho.png",
        "/img/rainha-vermelha.png",
        "/img/rei-vermelho.png",
        
        "/img/abertura.png"
    };
        
    private final BufferedImage[] imagens = new BufferedImage[ imagensPaths.length ];          
    
    private CarregandoRecursoListener listener = null;
    
    public void carregaAberturaImagem() throws CarregandoRecursoException {
        MediaTracker mt = new MediaTracker( new JPanel() );
        try {
            mt.addImage( imagens[ ABERTURA ] = ImageIO.read( this.getClass().getResourceAsStream( imagensPaths[ ABERTURA ] ) ), 0 );
            try {
                mt.waitForID( 0 );                  
            } catch ( InterruptedException ex ) {
                throw new CarregandoRecursoException( imagensPaths[ ABERTURA ] );
            }  
        } catch ( IOException e ) {
            throw new CarregandoRecursoException( imagensPaths[ ABERTURA ] );            
        }        
    }
    
    public void carrega( int offset, int quantPorcent ) throws CarregandoRecursoException {
        MediaTracker mt = new MediaTracker( new JPanel() );
        for( int i = 0; i < imagens.length-1; i++ ) {
            imagens[ i ] = this.carregaImagem( imagensPaths[i], mt, i );        
            
            if ( listener != null )
                listener.carregouRecurso( i+1, imagens.length-1, offset, quantPorcent );
        }
        
        if ( listener != null )
            listener.completado( offset, quantPorcent );
    }
    
    public BufferedImage carregaImagem( String imagemPath, MediaTracker mt, int id ) throws CarregandoRecursoException {               
        BufferedImage imagem = null;
        try {
            mt.addImage( imagem = ImageIO.read( this.getClass().getResourceAsStream( imagemPath ) ), id );
            try {
                mt.waitForID( id );                  
            } catch ( InterruptedException ex ) {
                throw new CarregandoRecursoException( imagemPath );
            }  
        } catch ( IOException e ) {
            throw new CarregandoRecursoException( imagemPath );            
        }
        
        return imagem;
    }
        
    public BufferedImage getImagem( int pecaID ) {                                
        return imagens[ pecaID ];        
    }
       
    @Override
    public boolean mesmaCor( int pecaID1, int pecaID2 ) {
        if ( pecaID1 == Const.INT_NULO || pecaID2 == Const.INT_NULO )
            return false;
        
        return this.getPecaCor( pecaID1 ) == this.getPecaCor( pecaID2 );
    }

    @Override
    public int getPecaCorOposta( int pecaID ) {
        int cor = this.getPecaCor( pecaID );
        if ( cor == PRETO )
            return BRANCO;
        if ( cor == BRANCO )
            return PRETO;
        return Const.INT_NULO;
    }
    
    @Override
    public int getPecaDirecaoPorPID( int pecaID ) {
        int cor = this.getPecaCor( pecaID );
        return this.getPecaDirecaoPorCor( cor );
    }
    
    public int getPecaDirecaoPorCor( int cor ) {
        return ( cor == PRETO ? 1 : -1 );
    }
    
    @Override
    public int getPecaTipo( int pecaID ) {
        int cor = this.getPecaCor( pecaID );        
        return pecaID - cor;
    }
    
    @Override
    public int getPecaCor( int pecaID ) {                
        if ( pecaID >= VERMELHO )
            return VERMELHO ;
        if ( pecaID >= PRETO )
            return PRETO;        
        if ( pecaID >= BRANCO )
            return BRANCO;        
        return INT_NULO;
    }
    
    @Override
    public int converteParaVermelhoPecaID( int pecaID ) {
        int tipo = this.getPecaTipo( pecaID );
        return VERMELHO + tipo;
    }
    
    @Override
    public int transformaEmRainha( int pid ) {
        int cor = this.getPecaCor( pid );
        return cor + RAINHA;
    }
        
    public void setCarregandoImagemListener( CarregandoRecursoListener listener ) {
        this.listener = listener;
    }
    
    public static String getCorString( int cor ) {
        switch( cor ) {
            case BRANCO: return "BRANCO";
            case PRETO: return "PRETO";
        }
        return "COR NULA";
    }
    
    public static String getTipoString( int tipo ) {
        switch( tipo ) {
            case PEAO: return "PEAO";
            case TORRE: return "TORRE";
            case CAVALO: return "CAVALO";
            case BISPO: return "BISPO";
            case RAINHA: return "RAINHA";
            case REI: return "REI";            
        }
        return "PEÇA NULA";
    }
    
}
