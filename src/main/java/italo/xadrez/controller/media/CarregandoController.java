package italo.xadrez.controller.media;

import italo.xadrez.Sistema;
import italo.xadrez.gui.carregando.CarregandoPNL;
import italo.xadrez.media.CarregandoRecursoListener;

public class CarregandoController implements CarregandoRecursoListener {
    
    private final Sistema sistema;
    
    public CarregandoController( Sistema sistema ) {
        this.sistema = sistema;
    }

    @Override
    public void carregouRecurso( int i, int total, int offset, int quantPorcent ) {
        double porcent = offset + ( ( (double)i ) / ( (double)total ) * quantPorcent );
        
        CarregandoPNL pnl = sistema.getGUI().getJanelaGUI().getBarraProgressoDesenhoPNL();        
        pnl.getBarraProgressoDesenho().setPorcentagem( porcent );
        pnl.repaint();
    }

    @Override
    public void completado( int offset, int quantPorcent ) {
        CarregandoPNL pnl = sistema.getGUI().getJanelaGUI().getBarraProgressoDesenhoPNL();        
        pnl.getBarraProgressoDesenho().setPorcentagem( offset + quantPorcent );
        pnl.repaint();
    }
    
}
