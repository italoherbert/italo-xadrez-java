package italo.xadrez.controller.media;

import italo.xadrez.Sistema;
import italo.xadrez.media.CarregandoRecursoListener;

public class CarregandoController implements CarregandoRecursoListener {
    
    private final Sistema sistema;
    
    public CarregandoController( Sistema sistema ) {
        this.sistema = sistema;
    }

    @Override
    public void carregouRecurso( int i, int total, int offset, int quantPorcent ) {
        double porcent = offset + ( ( (double)i ) / ( (double)total ) * quantPorcent );
        
        sistema.getGUI().getJanelaGUI().getCarregandoPNL().setPorcentagem( porcent );
        sistema.getGUI().getJanelaGUI().getCarregandoPNL().repaint();
    }

    @Override
    public void completado( int offset, int quantPorcent ) {
        sistema.getGUI().getJanelaGUI().getCarregandoPNL().setPorcentagem( offset + quantPorcent );
        sistema.getGUI().getJanelaGUI().getCarregandoPNL().repaint();
    }
    
}
