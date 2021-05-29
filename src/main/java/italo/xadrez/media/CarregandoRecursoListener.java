package italo.xadrez.media;

public interface CarregandoRecursoListener {
    
    public void carregouRecurso( int i, int total, int offset, int quantPorcent );
    
    public void completado( int offset, int quantPorcent );
    
}
