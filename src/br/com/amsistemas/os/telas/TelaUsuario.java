/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amsistemas.os.telas;

/**
 *
 * @author André
 */
import java.sql.*;
import br.com.amsistemas.os.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void Consultar() {
        String sql = "SELECT * FROM tbusuarios WHERE idUsuario = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtbxIDUsuario.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtbxUsuario.setText(rs.getString(2));
                txtbxTelefoneUsuario.setText(rs.getString(3));
                txtbxLoginAcesso.setText(rs.getString(4));
                txtbxSenhaAcesso.setText(rs.getString(5));
                cbxPerfilUsuario.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "O ID informado para consulta, não corresponde a nenhum usuário credenciado.");
                txtbxIDUsuario.setText(null);
                txtbxIDUsuario.requestFocus();
                txtbxUsuario.setText(null);
                txtbxTelefoneUsuario.setText(null);
                txtbxLoginAcesso.setText(null);
                txtbxSenhaAcesso.setText(null);
                cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void Adicionar() {
        String sql = "INSERT INTO tbusuarios (idUsuario, usuario, telefone, login, senha, perfil) VALUES (?,?,?,?,?,?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtbxIDUsuario.getText());
            pst.setString(2, txtbxUsuario.getText());
            pst.setString(3, txtbxTelefoneUsuario.getText());
            pst.setString(4, txtbxLoginAcesso.getText());
            pst.setString(5, txtbxSenhaAcesso.getText());
            pst.setString(6, cbxPerfilUsuario.getSelectedItem().toString());
            if ((txtbxIDUsuario.getText().isEmpty()) || (txtbxUsuario.getText().isEmpty()) || (txtbxLoginAcesso.getText().isEmpty()) || (txtbxSenhaAcesso.getText().isEmpty()) || (cbxPerfilUsuario.getSelectedIndex() == 0)) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha os campos obrigatórios indicados com asterisco.");
            } else {

                int Adicionado = pst.executeUpdate();
                if (Adicionado > 0) {
                    JOptionPane.showMessageDialog(null, txtbxUsuario.getText() + " foi adicionado(a) com sucesso !");
                    txtbxIDUsuario.setText(null);
                    txtbxIDUsuario.requestFocus();
                    txtbxUsuario.setText(null);
                    txtbxTelefoneUsuario.setText(null);
                    txtbxLoginAcesso.setText(null);
                    txtbxSenhaAcesso.setText(null);
                    cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro, ao tentar adicionar o usuário" + txtbxUsuario.getText() + " ,por favor verifique.");
                    txtbxIDUsuario.setText(null);
                    txtbxIDUsuario.requestFocus();
                    txtbxUsuario.setText(null);
                    txtbxTelefoneUsuario.setText(null);
                    txtbxLoginAcesso.setText(null);
                    txtbxSenhaAcesso.setText(null);
                    cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Atualizar() {
        String sql = "UPDATE tbusuarios SET usuario=?, telefone=?, login=?, senha=?, perfil=? WHERE idUsuario = ?";        
        try {
            pst = conexao.prepareStatement(sql);            
            pst.setString(1, txtbxUsuario.getText());
            pst.setString(2, txtbxTelefoneUsuario.getText());
            pst.setString(3, txtbxLoginAcesso.getText());
            pst.setString(4, txtbxSenhaAcesso.getText());
            pst.setString(5, cbxPerfilUsuario.getSelectedItem().toString());
            pst.setString(6, txtbxIDUsuario.getText());            
                if ((txtbxIDUsuario.getText().isEmpty()) || (txtbxUsuario.getText().isEmpty()) || (txtbxLoginAcesso.getText().isEmpty()) || (txtbxSenhaAcesso.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha os campos obrigatórios indicados com asterisco.");
            } else {
                int Atualizado = pst.executeUpdate();
                if (Atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "O cadastro de " + txtbxUsuario.getText() + " foi atualizado(a) com sucesso !");
                    txtbxIDUsuario.setText(null);
                    txtbxIDUsuario.requestFocus();
                    txtbxUsuario.setText(null);
                    txtbxTelefoneUsuario.setText(null);
                    txtbxLoginAcesso.setText(null);
                    txtbxSenhaAcesso.setText(null);
                    cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
                } else {
                    JOptionPane.showMessageDialog(null, "O cadastro de(a) " + txtbxUsuario.getText() + " não foi atualizado(a) com sucesso, favor verificar.");
                    txtbxIDUsuario.setText(null);
                    txtbxIDUsuario.requestFocus();
                    txtbxUsuario.setText(null);
                    txtbxTelefoneUsuario.setText(null);
                    txtbxLoginAcesso.setText(null);
                    txtbxSenhaAcesso.setText(null);
                    cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Deletar(){
        int Confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que gostaria de remover o usuário(a) " + txtbxUsuario.getText() + " ?", "ATENÇÃO | AM Sistemas", JOptionPane.YES_NO_OPTION);
        if(Confirmar == JOptionPane.YES_OPTION){
            String sql = "DELETE from tbusuarios WHERE idUsuario = ?";
            try {
                pst = conexao.prepareStatement(sql);   
                pst.setString(1, txtbxIDUsuario.getText());
                int Deletado = pst.executeUpdate();
                if(Deletado > 0){
                    JOptionPane.showMessageDialog(null, "O cadastro de(o) " + txtbxUsuario.getText() + " foi removida(o) com êxito.", "Remoção efetuada | AM Sistemas", JOptionPane.OK_OPTION);  
                    txtbxIDUsuario.setText(null);
                    txtbxIDUsuario.requestFocus();
                    txtbxUsuario.setText(null);
                    txtbxTelefoneUsuario.setText(null);
                    txtbxLoginAcesso.setText(null);
                    txtbxSenhaAcesso.setText(null);
                    cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
                    txtbxIDUsuario.requestFocus();
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

        lblTituID = new javax.swing.JLabel();
        txtbxIDUsuario = new javax.swing.JTextField();
        lblTituTel = new javax.swing.JLabel();
        txtbxTelefoneUsuario = new javax.swing.JTextField();
        lblTituSenha = new javax.swing.JLabel();
        txtbxSenhaAcesso = new javax.swing.JTextField();
        txtbxLoginAcesso = new javax.swing.JTextField();
        lblTituLogin = new javax.swing.JLabel();
        lblTituPerfilUsu = new javax.swing.JLabel();
        cbxPerfilUsuario = new javax.swing.JComboBox();
        lblTituNomeUsu = new javax.swing.JLabel();
        txtbxUsuario = new javax.swing.JTextField();
        jSeparador1 = new javax.swing.JSeparator();
        btnNovoUsuario = new javax.swing.JButton();
        btnConsultarUsuario = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();
        btnDeletarUsuario = new javax.swing.JButton();
        btnLimparCamposUsuario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setTitle("Novo Usuário");
        setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1225, 567));

        lblTituID.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituID.setText("ID");

        txtbxIDUsuario.setBackground(new java.awt.Color(255, 255, 204));
        txtbxIDUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituTel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituTel.setText("Telefone");

        txtbxTelefoneUsuario.setBackground(new java.awt.Color(255, 255, 204));
        txtbxTelefoneUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituSenha.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituSenha.setText("Senha");

        txtbxSenhaAcesso.setBackground(new java.awt.Color(255, 255, 204));
        txtbxSenhaAcesso.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txtbxLoginAcesso.setBackground(new java.awt.Color(255, 255, 204));
        txtbxLoginAcesso.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTituLogin.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituLogin.setText("Login de Acesso");

        lblTituPerfilUsu.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituPerfilUsu.setText("Perfil de Usuário");

        cbxPerfilUsuario.setBackground(new java.awt.Color(255, 255, 204));
        cbxPerfilUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxPerfilUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione o Perfil", "Administrador", "Colaborador" }));

        lblTituNomeUsu.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTituNomeUsu.setText("Nome do Usuário");

        txtbxUsuario.setBackground(new java.awt.Color(255, 255, 204));
        txtbxUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnNovoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Adicionar-Usuario.png"))); // NOI18N
        btnNovoUsuario.setToolTipText("Novo Usuário");
        btnNovoUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoUsuarioActionPerformed(evt);
            }
        });

        btnConsultarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Consultar-Usuario.png"))); // NOI18N
        btnConsultarUsuario.setToolTipText("Consultar Usuário");
        btnConsultarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarUsuarioActionPerformed(evt);
            }
        });

        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Editar-Usuario.png"))); // NOI18N
        btnEditarUsuario.setToolTipText("Editar Usuário");
        btnEditarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnDeletarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/Deletar-Usuario.png"))); // NOI18N
        btnDeletarUsuario.setToolTipText("Deletar Usuário");
        btnDeletarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarUsuarioActionPerformed(evt);
            }
        });

        btnLimparCamposUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/amsistemas/os/icones/LimparCampos-Usuario.png"))); // NOI18N
        btnLimparCamposUsuario.setToolTipText("Limpar Campos");
        btnLimparCamposUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparCamposUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCamposUsuarioActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("*");

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("*");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("*");

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("*");

        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("*");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparador1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtbxIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituNomeUsu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtbxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbxTelefoneUsuario)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituTel)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituLogin)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtbxLoginAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbxSenhaAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituSenha)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTituPerfilUsu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbxPerfilUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnNovoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConsultarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeletarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimparCamposUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTituID)
                        .addComponent(lblTituNomeUsu)
                        .addComponent(lblTituTel))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtbxIDUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtbxTelefoneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbxUsuario))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituLogin)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbxLoginAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTituSenha)
                                .addComponent(lblTituPerfilUsu))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtbxSenhaAcesso)
                            .addComponent(cbxPerfilUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLimparCamposUsuario)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnDeletarUsuario)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNovoUsuario)
                            .addComponent(btnConsultarUsuario)
                            .addComponent(btnEditarUsuario))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        setBounds(0, 0, 601, 315);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarUsuarioActionPerformed
        Consultar();
    }//GEN-LAST:event_btnConsultarUsuarioActionPerformed

    private void btnLimparCamposUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCamposUsuarioActionPerformed
        txtbxIDUsuario.setText(null);
        txtbxUsuario.setText(null);
        txtbxTelefoneUsuario.setText(null);
        txtbxLoginAcesso.setText(null);
        txtbxSenhaAcesso.setText(null);
        cbxPerfilUsuario.setSelectedItem("Selecione o Perfil");
        txtbxIDUsuario.requestFocus();
    }//GEN-LAST:event_btnLimparCamposUsuarioActionPerformed

    private void btnNovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoUsuarioActionPerformed
        Adicionar();
    }//GEN-LAST:event_btnNovoUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        Atualizar();
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void btnDeletarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarUsuarioActionPerformed
        Deletar();
    }//GEN-LAST:event_btnDeletarUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarUsuario;
    private javax.swing.JButton btnDeletarUsuario;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnLimparCamposUsuario;
    private javax.swing.JButton btnNovoUsuario;
    private javax.swing.JComboBox cbxPerfilUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparador1;
    private javax.swing.JLabel lblTituID;
    private javax.swing.JLabel lblTituLogin;
    private javax.swing.JLabel lblTituNomeUsu;
    private javax.swing.JLabel lblTituPerfilUsu;
    private javax.swing.JLabel lblTituSenha;
    private javax.swing.JLabel lblTituTel;
    private javax.swing.JTextField txtbxIDUsuario;
    private javax.swing.JTextField txtbxLoginAcesso;
    private javax.swing.JTextField txtbxSenhaAcesso;
    private javax.swing.JTextField txtbxTelefoneUsuario;
    private javax.swing.JTextField txtbxUsuario;
    // End of variables declaration//GEN-END:variables
}
