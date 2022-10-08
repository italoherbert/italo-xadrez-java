package italo.xadrez.gui.carregando;

import italo.xadrez.gui.desenho.Desenho;

public interface BarraProgressoDesenho extends Desenho {
    
    public double getPorcentagem();
    
    public void setPorcentagem( double porcentagem );
    
}
