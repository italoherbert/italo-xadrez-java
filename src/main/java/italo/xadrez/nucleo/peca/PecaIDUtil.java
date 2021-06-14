package italo.xadrez.nucleo.peca;

public interface PecaIDUtil {
        
    public boolean mesmaCor( int pecaID1, int pecaID2 );
    
    public int getPecaDirecaoPorPID( int pecaID );

    public int getPecaDirecaoPorCor( int cor );
    
    public int getPecaTipo( int pecaID );
    
    public int getPecaCor( int pecaID );
    
    public int getPecaCorOposta( int pecaID );
    
    public int getCorOposta( int cor );
    
    public int converteParaVermelhoPecaID( int pecaID );
    
    public int transformaEmRainha( int pecaID );
    
}
