package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.TecladoGUIListener;
import javax.swing.JOptionPane;

public class JogoTecladoController implements TecladoGUIListener {

    private final Sistema sistema;
    
    public JogoTecladoController( Sistema sistema ) {
        this.sistema = sistema;
    }
    
    @Override
    public void teclaPressionada( int tecla ) {
        if ( sistema.getJogo().isFim() )
            return;
        
        switch ( tecla ) {
            case ENTER:
                sistema.getJogo().pausaContinua();
                break;
            case ESC:
                int result = JOptionPane.showConfirmDialog( null, 
                        "Tem certeza que deseja abandonar a partida?", 
                        "Abandonar partida", 
                        JOptionPane.YES_NO_OPTION );
                
                if ( result == JOptionPane.YES_OPTION ) {
                    sistema.getJogo().abandonarPartida();   
                    sistema.getGUI().getJanelaGUI().getJogoPNL().repaint();
                }
                break;
        } 
    }

    @Override
    public void qualquerTeclaPressionada() {
        if ( sistema.getJogo().isFim() )
            sistema.getJogoCtrl().paraTelaMenu();        
    }
    
}
