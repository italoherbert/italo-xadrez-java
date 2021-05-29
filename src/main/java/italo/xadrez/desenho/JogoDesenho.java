package italo.xadrez.desenho;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import italo.xadrez.nucleo.Tabuleiro;
import java.awt.Color;
import java.awt.Graphics2D;

public class JogoDesenho implements Desenho, JogoDesenhoDriver {             
            
    private final PlacarDesenho placarDesenho;
    private final TabuleiroDesenho tabuleiroDesenho;
    private final TextoDesenho textoDesenho;
        
    private final Sistema sistema;
    
    public JogoDesenho(Sistema sistema) {
        this.sistema = sistema;
        
        this.placarDesenho = new PlacarDesenho( this );
        this.tabuleiroDesenho = new TabuleiroDesenho( this );
        this.textoDesenho = new TextoDesenho( this );
    }
    
    @Override
    public void desenha( Graphics2D g2d, Tela tela ) {
        int w = tela.getLargura();
        int h = tela.getAltura();
        
        g2d.setColor( Color.BLACK );
        g2d.fillRect( 0, 0, w, h );
        
        placarDesenho.desenha( g2d, tela );                
        tabuleiroDesenho.desenha( g2d, tela );        
        textoDesenho.desenha( g2d, tela ); 
    }

    @Override
    public int getAlturaPlacar() {
        return placarDesenho.getAltura();
    }

    @Override
    public Sistema getSistema() {
        return sistema;
    }

    @Override
    public Tabuleiro getTabuleiro() {
        return tabuleiroDesenho;
    }
            
}
