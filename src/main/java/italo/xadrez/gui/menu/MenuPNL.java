package italo.xadrez.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public class MenuPNL extends javax.swing.JPanel implements MenuGUITO, ActionListener, ItemListener {
       
    private MenuGUIListener listener = null;
    
    public MenuPNL() {
        initComponents();
                
        ButtonGroup btg1 = new ButtonGroup();
        btg1.add( jogadorHumano1RB );
        btg1.add( jogadorComp1RB );
                
        DefaultComboBoxModel model1 = (DefaultComboBoxModel)jogadorComp1CBB.getModel();
        model1.addElement( FACIL );
        model1.addElement( MEDIANO );
        model1.addElement( DIFICIL );
        
        DefaultComboBoxModel model2 = (DefaultComboBoxModel)jogadorComp2CBB.getModel();
        model2.addElement( FACIL );
        model2.addElement( MEDIANO );
        model2.addElement( DIFICIL );
                
        jogadorComp1CBB.setModel( model1 );
        jogadorComp2CBB.setModel( model2 );         

        iniciarJogoBT.addActionListener( this );
        
        jogadorHumano1RB.addItemListener( this ); 
        jogadorComp1RB.addItemListener( this ); 
        
        jogadorComp1CBB.setSelectedItem( MEDIANO );
        jogadorComp2CBB.setSelectedItem( DIFICIL );                 
        jogadorHumano1RB.setSelected( true );

        jogadorHumano1RB.setOpaque( false ); 
        jogadorComp1RB.setOpaque( false ); 

        tituloPNL.setOpaque( false );   
        jogador1PNL.setOpaque( false );
        jogador2PNL.setOpaque( false );
        sulPNL.setOpaque( false );         
        super.setOpaque( false ); 
                
        jogadoresPNL.setOpaque( true );        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        listener.iniciarBTAcionado( this );
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        jogadorComp1CBB.setEnabled( e.getSource() == jogadorComp1RB );                
    }
    
    public void setMenuGUIListener( MenuGUIListener listener ) {
        this.listener = listener;
    }

    @Override
    public boolean isJogador1Humano() {
        return jogadorHumano1RB.isSelected();
    }

    @Override
    public Object getNivelJogadorComp1() {
        return jogadorComp1CBB.getSelectedItem();
    }

    @Override
    public Object getNivelJogadorComp2() {
        return jogadorComp2CBB.getSelectedItem();
    }

    public JPanel getJogadoresPNL() {
        return jogadoresPNL;
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tituloPNL = new javax.swing.JPanel();
        tituloLB = new javax.swing.JLabel();
        jogadoresPNL = new javax.swing.JPanel();
        jogador1PNL = new javax.swing.JPanel();
        jogadorHumano1RB = new javax.swing.JRadioButton();
        jogadorComp1RB = new javax.swing.JRadioButton();
        jogadorComp1CBB = new javax.swing.JComboBox<>();
        jogador2PNL = new javax.swing.JPanel();
        jogadorComp2CBB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        sulPNL = new javax.swing.JPanel();
        iniciarJogoBT = new javax.swing.JButton();

        tituloLB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tituloLB.setForeground(new java.awt.Color(255, 255, 255));
        tituloLB.setText("Menu");
        tituloPNL.add(tituloLB);

        jogadoresPNL.setBorder(javax.swing.BorderFactory.createTitledBorder("Configure Jogadores"));

        jogador1PNL.setBorder(javax.swing.BorderFactory.createTitledBorder("Brancas"));

        jogadorHumano1RB.setText("Jogador");

        jogadorComp1RB.setText("Computador");

        javax.swing.GroupLayout jogador1PNLLayout = new javax.swing.GroupLayout(jogador1PNL);
        jogador1PNL.setLayout(jogador1PNLLayout);
        jogador1PNLLayout.setHorizontalGroup(
            jogador1PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogador1PNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogadorHumano1RB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jogadorComp1RB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jogadorComp1CBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jogador1PNLLayout.setVerticalGroup(
            jogador1PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogador1PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jogadorHumano1RB)
                .addComponent(jogadorComp1RB)
                .addComponent(jogadorComp1CBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jogador2PNL.setBorder(javax.swing.BorderFactory.createTitledBorder("Pretas"));
        jogador2PNL.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Computador");

        javax.swing.GroupLayout jogador2PNLLayout = new javax.swing.GroupLayout(jogador2PNL);
        jogador2PNL.setLayout(jogador2PNLLayout);
        jogador2PNLLayout.setHorizontalGroup(
            jogador2PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogador2PNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jogadorComp2CBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jogador2PNLLayout.setVerticalGroup(
            jogador2PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogador2PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jogadorComp2CBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
        );

        javax.swing.GroupLayout jogadoresPNLLayout = new javax.swing.GroupLayout(jogadoresPNL);
        jogadoresPNL.setLayout(jogadoresPNLLayout);
        jogadoresPNLLayout.setHorizontalGroup(
            jogadoresPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogadoresPNLLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jogadoresPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jogador1PNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jogador2PNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jogadoresPNLLayout.setVerticalGroup(
            jogadoresPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jogadoresPNLLayout.createSequentialGroup()
                .addComponent(jogador1PNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jogador2PNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        iniciarJogoBT.setText("Iniciar");
        sulPNL.add(iniciarJogoBT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sulPNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tituloPNL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jogadoresPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jogadoresPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sulPNL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton iniciarJogoBT;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jogador1PNL;
    private javax.swing.JPanel jogador2PNL;
    private javax.swing.JComboBox<String> jogadorComp1CBB;
    private javax.swing.JRadioButton jogadorComp1RB;
    private javax.swing.JComboBox<String> jogadorComp2CBB;
    private javax.swing.JRadioButton jogadorHumano1RB;
    private javax.swing.JPanel jogadoresPNL;
    private javax.swing.JPanel sulPNL;
    private javax.swing.JLabel tituloLB;
    private javax.swing.JPanel tituloPNL;
    // End of variables declaration//GEN-END:variables
}
