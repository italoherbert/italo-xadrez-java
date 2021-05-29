package italo.xadrez.gui.desenho;

public interface TecladoGUIListener {
    
    public final static int ENTER = 1;
    public final static int ESC = 2;
    
    public void teclaPressionada( int tecla );
    
    public void qualquerTeclaPressionada();
    
}
