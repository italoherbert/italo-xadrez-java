package italo.xadrez;

import italo.xadrez.media.CarregandoRecursoException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class XadrezMain {
    
    static {
        UIManager.put("OptionPane.yesButtonText", "Sim"); 
        UIManager.put("OptionPane.noButtonText", "Não");
    }
    
    public static void main( String[] args ) {
        Sistema sistema = new Sistema();
                
        SwingUtilities.invokeLater(() -> {
            try {                
                sistema.getGUI().getJanelaGUI().setVisible( true );
                sistema.executaTelaCarregando();
            } catch (CarregandoRecursoException ex) {
                JOptionPane.showMessageDialog( null, 
                        "Não foi possível carregar o recurso: "+ex.getRecursoPath(), 
                        "Falha no carregamento", 
                        JOptionPane.ERROR_MESSAGE );
            }
        } );
    }
    
}
