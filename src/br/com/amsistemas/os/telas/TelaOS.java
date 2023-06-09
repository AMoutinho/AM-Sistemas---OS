/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amsistemas.os.telas;

import java.sql.*;
import br.com.amsistemas.os.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;

/**
 *
 * @author André
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisa_inteligente_cliente() {
        String sql = "SELECT idCliente AS ID, cliente AS 'Nome do Cliente', telefonecli AS 'Telefone do Cliente' FROM tbclientes WHERE cliente LIKE ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtbxCliOS.getText() + "%");
            rs = pst.executeQuery();
            tblCliOS.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int Setar = tblCliOS.getSelectedRow();
        txtbxIDOS.setText(tblCliOS.getModel().getValueAt(Setar, 0).toString());
    }

    private void LimparTabela() {
        int linhas = 0;
        int colunas = 0;
        String zer = null;
        for (linhas = 0; linhas <= tblCliOS.getRowCount() - 1; linhas++) {
            for (colunas = 0; colunas <= tblCliOS.getColumnCount() - 1; colunas++) {
                tblCliOS.setValueAt(zer, linhas, colunas);
            }
        }
    }

    private void emitirOS() {
        String sql = "INSERT INTO tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idCliente) VALUES (?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbxSituacao.getSelectedItem().toString());
            pst.setString(3, txtbxEquip.getText());
            pst.setString(4, txtbxDef.getText());
            pst.setString(5, txtbxServ.getText());
            pst.setString(6, txtbxTec.getText());
            pst.setString(7, txtbxVlr.getText().replace(",", "."));
            pst.setString(8, txtbxIDOS.getText());
            if (txtbxIDOS.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo ID do cliente não informado, favor verificar para prosseguir.");
            } else if (txtbxEquip.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo Equipamento não informado, favor verificar para prosseguir.");
            } else if (txtbxDef.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo Defeito não informado, favor verificar para prosseguir.");
            } else {
                int Adicionado = pst.executeUpdate();
                if (Adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com êxito");
                    rdbOrcamento.setSelected(true);
                    cbxSituacao.setSelectedIndex(0);
                    txtbxEquip.setText(null);
                    txtbxDef.setText(null);
                    txtbxServ.setText(null);
                    txtbxTec.setText(null);
                    txtbxVlr.setText(null);
                    txtbxIDOS.setText(null);
                    txtbxCliOS.setText(null);
                    LimparTabela();
                    txtbxCliOS.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void PesquisarOS() {
        String numOS = JOptionPane.showInputDialog("Número da Ordem de Serviço");
        String sql = "SELECT * FROM tbos WHERE idOS=" + numOS;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                NumOS.setText(rs.getString(1));
                DataOS.setText(rs.getString(2));
                String rdbTipo = rs.getString(3);
                if (rdbTipo.equals("OS")) {
                    rdbOS.setSelected(true);
                    tipo = "OS";
                } else {
                    rdbOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                cbxSituacao.setSelectedItem(rs.getString(4));
                txtbxEquip.setText(rs.getString(5));
                txtbxDef.setText(rs.getString(6));
                txtbxServ.setText(rs.getString(7));
                txtbxTec.setText(rs.getString(8));
                txtbxVlr.setText(rs.getString(9));
                txtbxIDOS.setText(rs.getString(10));
                btnAdcOS.setEnabled(false);
                txtbxCliOS.setEnabled(false);
                tblCliOS.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma OS cadastrada, favor verificar.");
                txtbxCliOS.requestFocus();
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida");
            txtbxCliOS.requestFocus();
            //System.out.print(e);
        } catch (Exception erro2) {
            JOptionPane.showMessageDialog(null, erro2);
        }
    }
    
    private void AlterarOS() {
        String sql = "UPDATE tbos SET tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? WHERE idOS=?";
       
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbxSituacao.getSelectedItem().toString());
            pst.setString(3, txtbxEquip.getText());
            pst.setString(4, txtbxDef.getText());
            pst.setString(5, txtbxServ.getText());
            pst.setString(6, txtbxTec.getText());
            pst.setString(7, txtbxVlr.getText().replace(",", "."));
            pst.setString(8, NumOS.getText());
            if (txtbxIDOS.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo ID do cliente não informado, favor verificar para prosseguir.");
            } else if (txtbxEquip.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo Equipamento não informado, favor verificar para prosseguir.");
            } else if (txtbxDef.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo Defeito não informado, favor verificar para prosseguir.");
            } else {
                int Atualizado = pst.executeUpdate();
                if (Atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com êxito");
                    rdbOrcamento.setSelected(true);
                    cbxSituacao.setSelectedIndex(0);
                    txtbxEquip.setText(null);
                    txtbxDef.setText(null);
                    txtbxServ.setText(null);
                    txtbxTec.setText(null);
                    txtbxVlr.setText(null);
                    txtbxIDOS.setText(null);
                    txtbxCliOS.setText(null);
                    NumOS.setText(null);
                    DataOS.setText(null);
                    LimparTabela();
                    btnAdcOS.setEnabled(true);
                    txtbxCliOS.setEnabled(true);
                    tblCliOS.setVisible(true);
                    txtbxCliOS.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void DeletarOS(){
        int Confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que gostaria de remover a OS ?", "ATENÇÃO | AM Sistemas", JOptionPane.YES_NO_OPTION);
        if(Confirmar == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM tbos WHERE idOS = ?";
            try {
                pst = conexao.prepareStatement(sql);   
                pst.setString(1, NumOS.getText());
                int Deletado = pst.executeUpdate();
                if(Deletado > 0){
                JOptionPane.showMessageDialog(null, "A OS foi removida com êxito.", "Remoção efetuada | AM Sistemas", JOptionPane.OK_OPTION);  
                    rdbOrcamento.setSelected(true);
                    cbxSituacao.setSelectedIndex(0);
                    txtbxEquip.setText(null);
                    txtbxDef.setText(null);
                    txtbxServ.setText(null);
                    txtbxTec.setText(null);
                    txtbxVlr.setText(null);
                    txtbxIDOS.setText(null);
                    txtbxCliOS.setText(null);
                    NumOS.setText(null);
                    DataOS.setText(null);
                    LimparTabela();
                    btnAdcOS.setEnabled(true);
                    txtbxCliOS.setEnabled(true);
                    tblCliOS.setVisible(true);
                    txtbxCliOS.requestFocus();   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);                
            }            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpnl_Os = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        NumOS = new javax.swing.JTextField();
        DataOS = new javax.swing.JTextField();
        rdbOrcamento = new javax.swing.JRadioButton();
        rdbOS = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxSituacao = new javax.swing.JComboBox();
        jPnl = new javax.swing.JPanel();
        txtbxCliOS = new javax.swing.JTextField();
        lblIconeLupa = new javax.swing.JLabel();
        txtbxIDOS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliOS = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtbxEquip = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtbxDef = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtbxServ = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtbxTec = new javax.swing.JTextField();
        txtbxVlr = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnAdcOS = new javax.swing.JButton();
        btnConsultarOS = new javax.swing.JButton();
        btnEditarOS = new javax.swing.JButton();
        btnDeletarOS = new javax.swing.JButton();
        btnImpressaoOS = new javax.swing.JButton();

        setClosable(true);
        setTitle("Nova Ordem de Serviço");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jpnl_Os.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nº OS");

        NumOS.setEditable(false);
        NumOS.setBackground(new java.awt.Color(255, 255, 204));
        NumOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NumOS.setEnabled(false);

        DataOS.setEditable(false);
        DataOS.setBackground(new java.awt.Color(255, 255, 204));
        DataOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DataOS.setEnabled(false);

        buttonGroup1.add(rdbOrcamento);
        rdbOrcamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdbOrcamento.setText("Orçamento");
        rdbOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbOS);
        rdbOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdbOS.setText("Ordem de Serviço");
        rdbOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOSActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Data");

        javax.swing.GroupLayout jpnl_OsLayout = new javax.swing.GroupLayout(jpnl_Os);
        jpnl_Os.setLayout(jpnl_OsLayout);
        jpnl_OsLayout.setHorizontalGroup(
            jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnl_OsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnl_OsLayout.createSequentialGroup()
                        .addComponent(rdbOrcamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdbOS))
                    .addGroup(jpnl_OsLayout.createSequentialGroup()
                        .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(NumOS, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnl_OsLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(DataOS))
                            .addGroup(jpnl_OsLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jpnl_OsLayout.setVerticalGroup(
            jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnl_OsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NumOS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DataOS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnl_OsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbOrcamento)
                    .addComponent(rdbOS))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Situação OS:");

        cbxSituacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione a situação", "Resolvido", "Orçamento Recusado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Em Andamento", "Retornou" }));

        jPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        txtbxCliOS.setBackground(new java.awt.Color(255, 255, 204));
        txtbxCliOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbxCliOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbxCliOSKeyReleased(evt);
            }
        });

        lblIconeLupa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblIconeLupa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/PesqLupa.png"))); // NOI18N

        txtbxIDOS.setBackground(new java.awt.Color(255, 255, 204));
        txtbxIDOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbxIDOS.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ID*");

        tblCliOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Cliente", "Telefone"
            }
        ));
        tblCliOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCliOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliOS);

        javax.swing.GroupLayout jPnlLayout = new javax.swing.GroupLayout(jPnl);
        jPnl.setLayout(jPnlLayout);
        jPnlLayout.setHorizontalGroup(
            jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPnlLayout.createSequentialGroup()
                        .addComponent(txtbxCliOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIconeLupa)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxIDOS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPnlLayout.setVerticalGroup(
            jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLayout.createSequentialGroup()
                .addGroup(jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbxCliOS)
                    .addComponent(lblIconeLupa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtbxIDOS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Equipamento*");

        txtbxEquip.setBackground(new java.awt.Color(255, 255, 204));
        txtbxEquip.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Defeito*");

        txtbxDef.setBackground(new java.awt.Color(255, 255, 204));
        txtbxDef.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Serviço");

        txtbxServ.setBackground(new java.awt.Color(255, 255, 204));
        txtbxServ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Técnico");

        txtbxTec.setBackground(new java.awt.Color(255, 255, 204));
        txtbxTec.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtbxVlr.setBackground(new java.awt.Color(255, 255, 204));
        txtbxVlr.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbxVlr.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Valor");

        btnAdcOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdcOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/AdcOS.png"))); // NOI18N
        btnAdcOS.setToolTipText("Emitir OS");
        btnAdcOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdcOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdcOSActionPerformed(evt);
            }
        });

        btnConsultarOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/ConsultarOS.png"))); // NOI18N
        btnConsultarOS.setToolTipText("Consultar OS");
        btnConsultarOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarOSActionPerformed(evt);
            }
        });

        btnEditarOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/EditarOS.png"))); // NOI18N
        btnEditarOS.setToolTipText("Editar OS");
        btnEditarOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarOSActionPerformed(evt);
            }
        });

        btnDeletarOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/DeletarOS.png"))); // NOI18N
        btnDeletarOS.setToolTipText("Deletar OS");
        btnDeletarOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarOSActionPerformed(evt);
            }
        });

        btnImpressaoOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/ImpOS.png"))); // NOI18N
        btnImpressaoOS.setToolTipText("Impressão OS");
        btnImpressaoOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtbxServ, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtbxEquip)
                                    .addComponent(jpnl_Os, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxSituacao, 0, 227, Short.MAX_VALUE)))
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtbxDef)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(txtbxTec, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10)
                                            .addComponent(txtbxVlr, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdcOS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnConsultarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnEditarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnDeletarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnImpressaoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnl_Os, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxDef, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxServ, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxTec, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxVlr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnConsultarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeletarOS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdcOS, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnImpressaoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        setBounds(0, 0, 809, 494);
    }// </editor-fold>//GEN-END:initComponents

    private void txtbxCliOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbxCliOSKeyReleased
        pesquisa_inteligente_cliente();
    }//GEN-LAST:event_txtbxCliOSKeyReleased

    private void tblCliOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCliOSMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblCliOSMouseClicked

    private void rdbOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOrcamentoActionPerformed
        tipo = "Orçamento";
    }//GEN-LAST:event_rdbOrcamentoActionPerformed

    private void rdbOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOSActionPerformed
        tipo = "OS";
    }//GEN-LAST:event_rdbOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        rdbOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAdcOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdcOSActionPerformed
        emitirOS();
    }//GEN-LAST:event_btnAdcOSActionPerformed

    private void btnConsultarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarOSActionPerformed
        PesquisarOS();
    }//GEN-LAST:event_btnConsultarOSActionPerformed

    private void btnEditarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarOSActionPerformed
        AlterarOS();
    }//GEN-LAST:event_btnEditarOSActionPerformed

    private void btnDeletarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarOSActionPerformed
        DeletarOS();
    }//GEN-LAST:event_btnDeletarOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DataOS;
    private javax.swing.JTextField NumOS;
    private javax.swing.JButton btnAdcOS;
    private javax.swing.JButton btnConsultarOS;
    private javax.swing.JButton btnDeletarOS;
    private javax.swing.JButton btnEditarOS;
    private javax.swing.JButton btnImpressaoOS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPnl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpnl_Os;
    private javax.swing.JLabel lblIconeLupa;
    private javax.swing.JRadioButton rdbOS;
    private javax.swing.JRadioButton rdbOrcamento;
    private javax.swing.JTable tblCliOS;
    private javax.swing.JTextField txtbxCliOS;
    private javax.swing.JTextField txtbxDef;
    private javax.swing.JTextField txtbxEquip;
    private javax.swing.JTextField txtbxIDOS;
    private javax.swing.JTextField txtbxServ;
    private javax.swing.JTextField txtbxTec;
    private javax.swing.JTextField txtbxVlr;
    // End of variables declaration//GEN-END:variables
}
