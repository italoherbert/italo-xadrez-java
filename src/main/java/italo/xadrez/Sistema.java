package italo.xadrez;

import italo.xadrez.controller.JogoDesenhoController;
import italo.xadrez.controller.media.CarregandoController;
import italo.xadrez.controller.JanelaController;
import italo.xadrez.algoritmo.ComputadorAlgoritmo;
import italo.xadrez.algoritmo.DoisComputadorAlgoritmo;
import italo.xadrez.algoritmo.JogadorAlgoritmo;
import italo.xadrez.controller.JogoController;
import italo.xadrez.controller.JogoTecladoController;
import italo.xadrez.controller.MenuController;
import italo.xadrez.controller.MoveuController;
import italo.xadrez.controller.media.CarregadorController;
import italo.xadrez.ctrl.JogoCtrl;
import italo.xadrez.desenho.JogoDesenho;
import italo.xadrez.desenho.TelaInicialDesenho;
import italo.xadrez.gui.GUI;
import italo.xadrez.media.CarregandoRecursoException;
import italo.xadrez.media.MediaCarregador;
import italo.xadrez.media.MediaManager;
import italo.xadrez.nucleo.Jogo;
import italo.xadrez.nucleo.JogoDriver;
import italo.xadrez.nucleo.JogoManager;
import italo.xadrez.nucleo.Tabuleiro;
import italo.xadrez.nucleo.move.MoveManager;
import italo.xadrez.nucleo.move.MoveuListener;
import italo.xadrez.nucleo.peca.PecaIDUtil;

public class Sistema implements JogoDriver {
    
    private final JanelaController janelaController = new JanelaController( this );
    private final JogoTecladoController jogoTecladoController = new JogoTecladoController( this );
    
    private final JogoDesenhoController desenhoController = new JogoDesenhoController( this );
    
    private final JogoController jogoController = new JogoController( this );

    private final CarregandoController carregandoController = new CarregandoController( this );
    private final CarregadorController carregadorController = new CarregadorController( this );
    
    private final MoveuController moveuController = new MoveuController( this );
    
    private final MenuController menuController = new MenuController( this );
    
    private final JogoCtrl jogoCtrl = new JogoCtrl( this );

    private final MediaManager mediaManager = new MediaManager();
    private final JogoManager jogoManager = new JogoManager();
    private final MoveManager moveManager = new MoveManager();
        
    private final GUI gui = new GUI();
    private final Jogo jogo = new Jogo();
    
    private final TelaInicialDesenho telaInicialDesenho = new TelaInicialDesenho( this );
    private final JogoDesenho jogoDesenho = new JogoDesenho( this );    
                
    private final ComputadorAlgoritmo computador1 = new ComputadorAlgoritmo( this, Jogo.COR_JOGADOR1 );
    private final ComputadorAlgoritmo computador2 = new ComputadorAlgoritmo( this, Jogo.COR_JOGADOR2 );
    private final JogadorAlgoritmo jogador = new JogadorAlgoritmo( this, computador2 );
    private final DoisComputadorAlgoritmo doisComputadorAlgoritmo = new DoisComputadorAlgoritmo( this );
        
    public Sistema() {
        gui.getJanelaGUI().setJanelaGUIListener( janelaController );
        gui.getJanelaGUI().getJogoPNL().setTecladoGUIListener( jogoTecladoController ); 
        
        gui.getJanelaGUI().getMenuPNL().setMenuGUIListener( menuController );
        gui.getJanelaGUI().getJogoPNL().setDesenhoGUIListener( desenhoController );
        
        gui.getJanelaGUI().getCarregandoPNL().setDesenho( telaInicialDesenho ); 
        gui.getJanelaGUI().getMenuPNL().setDesenho( telaInicialDesenho );         
        gui.getJanelaGUI().getJogoPNL().setDesenho( jogoDesenho ); 
        
        jogo.setJogoListener( jogoController ); 
             
        mediaManager.getImagemManager().setCarregandoImagemListener( carregandoController );
        mediaManager.getAudioManager().setCarregandoAudioListener( carregandoController ); 
    }
        
    public void executaTelaCarregando() throws CarregandoRecursoException {
        gui.getJanelaGUI().showCarregandoPNL();
        
        MediaCarregador carregador = mediaManager.criaCarregador();
        carregador.setCarregadorListener( carregadorController ); 
        carregador.start();
    }
        
    public void startCompVersusComp() {
        new Thread( doisComputadorAlgoritmo ).start();
    }  
        
    public void repaint() {
        gui.getJanelaGUI().getJogoPNL().repaint();
    }
            
    @Override
    public PecaIDUtil getPecaIDUtil() {
        return mediaManager.getImagemManager();
    }
    
    public JogoCtrl getJogoCtrl() {
        return jogoCtrl;
    }

    public MoveManager getMoveManager() {
        return moveManager;
    }
    
    @Override
    public MoveuListener getMoveuListener() {
        return moveuController;
    }
    
    public GUI getGUI() {
        return gui;
    }
    
    @Override
    public Jogo getJogo() {
        return jogo;
    }
    
    public JogadorAlgoritmo getJogador() {
        return jogador;
    }
    
    public ComputadorAlgoritmo getComputador1() {
        return computador1;
    }

    public ComputadorAlgoritmo getComputador2() {
        return computador2;
    }

    public DoisComputadorAlgoritmo getDoisComputadorAlgoritmo() {
        return doisComputadorAlgoritmo;
    }
    
    @Override
    public Tabuleiro getTabuleiro() {
        return jogoDesenho.getTabuleiro();
    }
            
    @Override
    public JogoManager getJogoManager() {
        return jogoManager;
    }

    public MediaManager getMediaManager() {
        return mediaManager;
    } 
         
}
