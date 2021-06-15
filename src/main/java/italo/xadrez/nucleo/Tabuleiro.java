package italo.xadrez.nucleo;

public interface Tabuleiro {
    
    public final static int NULO = -1;    
    
    public int getTX();
    
    public int getTY();
    
    public int getTW();
    
    public int getTH();
    
    public int getCelulaW();
    
    public int getCelulaH();
        
    public void limpaSelecao();
    
    public boolean isTabuleiroPosic( int x, int y );
                    
    public void setSelecionadaDesloc( int deslocX, int deslocY );
    
    public int getSelecionadaMatI();
    
    public int getSelecionadaMatJ();
    
    public void setSelecionadaMatI( int matI );
    
    public void setSelecionadaMatJ( int matJ );    
    
}
