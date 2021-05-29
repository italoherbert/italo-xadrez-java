package italo.xadrez.media;

public class CarregandoRecursoException extends Exception {
        
    private final String recursoPath;
    
    public CarregandoRecursoException( String recursoPath ) {
        this.recursoPath = recursoPath;
    }
    
    public String getRecursoPath() {
        return recursoPath;
    }
    
}
