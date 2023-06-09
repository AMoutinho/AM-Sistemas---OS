/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amsistemas.os.telas;
import java.sql.*;
import br.com.amsistemas.os.dal.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author André
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();        
        conexao = ModuloConexao.conector();
    }
    
    private void Adicionar() {
        String sql = "INSERT INTO tbclientes (cliente, endercli, telefonecli, emailcli) VALUES (?,?,?,?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtbxCliente.getText());            
            pst.setString(2, txtbxEndCli.getText());
            pst.setString(3, txtbxTelefoneCliente.getText());
            pst.setString(4, txtbxEmailCli.getText());            
            if ((txtbxCliente.getText().toUpperCase().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha com o nome do cliente para prosseguir o cadastro.");
            }
            else if(txtbxTelefoneCliente.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor, preencha com o telefone do cliente para prosseguir o cadastro.");
            }else {

                int Adicionado = pst.executeUpdate();
                if (Adicionado > 0) {
                    JOptionPane.showMessageDialog(null, txtbxCliente.getText() + " foi adicionado(a) com sucesso !"); 
                    txtbxCliente.setText(null);
                    txtbxTelefoneCliente.setText(null);
                    txtbxEndCli.setText(null);
                    txtbxEmailCli.setText(null);   
                    txtbxCliente.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar adicionar o cliente " + txtbxCliente.getText() + " ,por favor verifique."); 
                    txtbxCliente.setText(null);
                    txtbxEndCli.setText(null);
                    txtbxTelefoneCliente.setText(null);                    
                    txtbxEmailCli.setText(null);                       
                    txtbxCliente.requestFocus();
                }
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void pesquisa_inteligente_cliente(){
        String sql = "SELECT * FROM tbclientes WHERE cliente like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtbxPesCli.getText() + "%");
            rs=pst.executeQuery();
            tblDadosCli.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void setar_campos(){
        int Setar = tblDadosCli.getSelectedRow();
        txtbxIDCli.setText(tblDadosCli.getModel().getValueAt(Setar, 0).toString());
        txtbxCliente.setText(tblDadosCli.getModel().getValueAt(Setar, 1).toString());
        txtbxEndCli.setText(tblDadosCli.getModel().getValueAt(Setar, 2).toString());
        txtbxTelefoneCliente.setText(tblDadosCli.getModel().getValueAt(Setar, 3).toString());        
        txtbxEmailCli.setText(tblDadosCli.getModel().getValueAt(Setar, 4).toString());     
        btnNovoCli.setEnabled(false);
    }
    
    private void Atualizar() {
        String sql = "UPDATE tbclientes SET cliente=?, endercli=?, telefonecli=?, emailcli=? WHERE IdCliente=?";        
        try {
            pst = conexao.prepareStatement(sql);            
            pst.setString(1, txtbxCliente.getText());
            pst.setString(2, txtbxEndCli.getText());
            pst.setString(3, txtbxTelefoneCliente.getText());            
            pst.setString(4, txtbxEmailCli.getText());
            pst.setString(5, txtbxIDCli.getText());            
                if ((txtbxCliente.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do cliente para prosseguir com a atualização.");
            }
                else if((txtbxTelefoneCliente.getText().isEmpty())){
                    JOptionPane.showMessageDialog(null, "Por favor, preencha o telefone do cliente para prosseguir com a atualização.");
                }else {
                int Atualizado = pst.executeUpdate();
                if (Atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "A atualização do(a) " + txtbxCliente.getText() + " foi efetuado(a) com êxito !");
                    txtbxIDCli.setText(null);
                    txtbxCliente.setText(null);
                    txtbxEndCli.setText(null);
                    txtbxTelefoneCliente.setText(null);
                    txtbxEmailCli.setText(null); 
                    txtbxPesCli.requestFocus();
                    btnNovoCli.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "A atualização dos dados de(a) " + txtbxCliente.getText() + " não foi efetuado(a) com êxito, favor verificar.");
                    txtbxIDCli.setText(null);
                    txtbxCliente.setText(null);                    
                    txtbxEndCli.setText(null);
                    txtbxTelefoneCliente.setText(null);
                    txtbxEmailCli.setText(null); 
                    txtbxPesCli.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Deletar(){
        int Confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que gostaria de remover o(a) cliente " + txtbxCliente.getText() + " ?", "ATENÇÃO | AM Sistemas", JOptionPane.YES_NO_OPTION);
        if(Confirmar == JOptionPane.YES_OPTION){
            String sql = "DELETE from tbclientes WHERE idCliente = ?";
            try {
                pst = conexao.prepareStatement(sql);   
                pst.setString(1, txtbxIDCli.getText());
                int Deletado = pst.executeUpdate();
                if(Deletado > 0){
                    JOptionPane.showMessageDialog(null, "O cadastro de(o) " + txtbxCliente.getText() + " foi removida(o) com êxito.", "Remoção efetuada | AM Sistemas", JOptionPane.OK_OPTION);  
                    txtbxIDCli.setText(null);
                    txtbxCliente.setText(null);                    
                    txtbxEndCli.setText(null);
                    txtbxTelefoneCliente.setText(null);
                    txtbxEmailCli.setText(null); 
                    txtbxPesCli.requestFocus();
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

        lblTituNomeCli = new javax.swing.JLabel();
        txtbxCliente = new javax.swing.JTextField();
        lblTituTelCli = new javax.swing.JLabel();
        txtbxTelefoneCliente = new javax.swing.JTextField();
        lblTituEmailCli = new javax.swing.JLabel();
        txtbxEmailCli = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnNovoCli = new javax.swing.JButton();
        btnEditarCli = new javax.swing.JButton();
        btnDeletarCli = new javax.swing.JButton();
        txtbxPesCli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDadosCli = new javax.swing.JTable();
        lblTituEndCli = new javax.swing.JLabel();
        txtbxEndCli = new javax.swing.JTextField();
        lblTituNomeCli1 = new javax.swing.JLabel();
        txtbxIDCli = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Novo Cliente");

        lblTituNomeCli.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituNomeCli.setText("Nome do Cliente");

        txtbxCliente.setBackground(new java.awt.Color(255, 255, 204));
        txtbxCliente.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituTelCli.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituTelCli.setText("Telefone do Cliente");

        txtbxTelefoneCliente.setBackground(new java.awt.Color(255, 255, 204));
        txtbxTelefoneCliente.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituEmailCli.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituEmailCli.setText("E-mail do Cliente");

        txtbxEmailCli.setBackground(new java.awt.Color(255, 255, 204));
        txtbxEmailCli.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("*");

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("*");

        btnNovoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Novo-Cliente.png"))); // NOI18N
        btnNovoCli.setToolTipText("Novo Cliente");
        btnNovoCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoCliActionPerformed(evt);
            }
        });

        btnEditarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Editar-Cliente.png"))); // NOI18N
        btnEditarCli.setToolTipText("Editar Cliente");
        btnEditarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCliActionPerformed(evt);
            }
        });

        btnDeletarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Deletar-Cliente.png"))); // NOI18N
        btnDeletarCli.setToolTipText("Deletar Cliente");
        btnDeletarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarCliActionPerformed(evt);
            }
        });

        txtbxPesCli.setBackground(new java.awt.Color(255, 255, 204));
        txtbxPesCli.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtbxPesCli.setToolTipText("Informe o nome de um cliente para pesquisar");
        txtbxPesCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbxPesCliKeyReleased(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Pesquisar-Cliente.png"))); // NOI18N
        jLabel3.setToolTipText("Pesquisa Avançada de Clientes");

        tblDadosCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDadosCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDadosCliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDadosCli);

        lblTituEndCli.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituEndCli.setText("Endereço do Cliente");

        txtbxEndCli.setBackground(new java.awt.Color(255, 255, 204));
        txtbxEndCli.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituNomeCli1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituNomeCli1.setText("ID Cliente");

        txtbxIDCli.setBackground(new java.awt.Color(255, 255, 204));
        txtbxIDCli.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtbxIDCli.setToolTipText("");
        txtbxIDCli.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtbxPesCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnNovoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(203, 203, 203)
                        .addComponent(btnEditarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addComponent(btnDeletarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbxTelefoneCliente)
                            .addComponent(txtbxCliente)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblTituTelCli)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblTituNomeCli)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTituNomeCli1)
                                    .addComponent(txtbxIDCli, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituEndCli)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtbxEmailCli, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                                .addComponent(lblTituEmailCli)
                                .addComponent(txtbxEndCli)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtbxPesCli, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtbxIDCli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTituNomeCli1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblTituEndCli))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbxEndCli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTituNomeCli))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTituEmailCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTituTelCli))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoCliActionPerformed
        Adicionar();
    }//GEN-LAST:event_btnNovoCliActionPerformed

    private void txtbxPesCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbxPesCliKeyReleased
        pesquisa_inteligente_cliente();
    }//GEN-LAST:event_txtbxPesCliKeyReleased

    private void tblDadosCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDadosCliMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblDadosCliMouseClicked

    private void btnEditarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCliActionPerformed
        Atualizar();
    }//GEN-LAST:event_btnEditarCliActionPerformed

    private void btnDeletarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarCliActionPerformed
        Deletar();
    }//GEN-LAST:event_btnDeletarCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletarCli;
    private javax.swing.JButton btnEditarCli;
    private javax.swing.JButton btnNovoCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTituEmailCli;
    private javax.swing.JLabel lblTituEndCli;
    private javax.swing.JLabel lblTituNomeCli;
    private javax.swing.JLabel lblTituNomeCli1;
    private javax.swing.JLabel lblTituTelCli;
    private javax.swing.JTable tblDadosCli;
    private javax.swing.JTextField txtbxCliente;
    private javax.swing.JTextField txtbxEmailCli;
    private javax.swing.JTextField txtbxEndCli;
    private javax.swing.JTextField txtbxIDCli;
    private javax.swing.JTextField txtbxPesCli;
    private javax.swing.JTextField txtbxTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
