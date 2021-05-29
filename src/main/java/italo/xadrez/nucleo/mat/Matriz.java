package italo.xadrez.nucleo.mat;

public interface Matriz {
        
    public void inicializa( MatrizInicializador ini );
    
    public int getJLen();
    
    public int getILen();
    
    public int getValor( int i, int j );
    
    public void setValor( int i, int j, int valor );
    
    public void copia( Matriz matCopia );
        
}
