package italo.xadrez.gui.janela;

import italo.xadrez.gui.carregando.CarregandoDesenhoPNL;
import italo.xadrez.gui.desenho.DesenhoPNL;
import italo.xadrez.gui.menu.MenuDesenhoPNL;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class JanelaGUI extends JFrame implements WindowListener {
    
    public final static String CARREGANDO = "carregando";
    public final static String MENU = "menu";
    public final static String JOGO = "jogo";
    
    private final CarregandoDesenhoPNL carregandoDesenhoPNL = new CarregandoDesenhoPNL();
    private final MenuDesenhoPNL menuDesenhoPNL = new MenuDesenhoPNL();
    private final DesenhoPNL jogoDesenhoPNL = new DesenhoPNL();    
    
    private JanelaGUIListener listener;
    
    private final CardLayout card = new CardLayout();        
        
    public JanelaGUI() {
        super.setTitle( "Jogo de Xadrez" );
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        super.setSize( 750, 700 );
        super.setLocationRelativeTo( this ); 
                
        Container c = super.getContentPane();
        c.setLayout( card );
        c.add( CARREGANDO, carregandoDesenhoPNL );
        c.add( MENU, menuDesenhoPNL );
        c.add( JOGO, jogoDesenhoPNL );
        
        super.addWindowListener( this );        
    }
    
    public void showCarregandoPNL() {
        card.show( this.getContentPane(), CARREGANDO );
    }
    
    public void showMenuPNL() {
        card.show( this.getContentPane(), MENU ); 
    }
    
    public void showJogoPNL() {               
        card.show( this.getContentPane(), JOGO ); 
        
        jogoDesenhoPNL.setFocusable( true );
        jogoDesenhoPNL.requestFocusInWindow();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if ( listener != null )
            listener.fechandoJanela();
    }        
    
    @Override
    public void windowOpened(WindowEvent e) {}
    
    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    public void setJanelaGUIListener( JanelaGUIListener listener ) {
        this.listener = listener;
    }
    
    public CarregandoDesenhoPNL getCarregandoPNL() {
        return carregandoDesenhoPNL;
    }
    
    public MenuDesenhoPNL getMenuPNL() {
        return menuDesenhoPNL;
    }
    
    public DesenhoPNL getJogoPNL() {
        return jogoDesenhoPNL;
    }
    
}
