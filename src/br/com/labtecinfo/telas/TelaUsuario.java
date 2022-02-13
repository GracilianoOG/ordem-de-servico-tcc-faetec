package br.com.labtecinfo.telas;

import java.sql.*;
import br.com.labtecinfo.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // Ordem do rs = ordem das colunas no BD | Ordem do pst = ordem das interrogações ?
    private void adicionarUsuario() {
        String sql = "insert into tbusuarios(iduser, usuario, perfil, tel, email, login, senha) "
                + "values(?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadUsuIdUser.getText());
            pst.setString(2, txtCadUsuNome.getText());
            pst.setString(3, cboCadUsuPerfil.getSelectedItem().toString());
            pst.setString(4, txtCadUsuTel.getText());
            pst.setString(5, txtCadUsuEmail.getText());
            pst.setString(6, txtCadUsuLogin.getText());
            pst.setString(7, txtCadUsuSenha.getText());

            // Validação
            if ((txtCadUsuIdUser.getText().isEmpty()) || (txtCadUsuNome.getText().isEmpty()) || txtCadUsuEmail.getText().isEmpty()
                    || txtCadUsuLogin.getText().isEmpty() || txtCadUsuSenha.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos OBRIGATÓRIOS!");
            } else {

                int adicionado = pst.executeUpdate();
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!!!");
                    limparCamposUsuario();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void consultarUsuario() {
        String sql = "select * from tbusuarios where iduser = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadUsuIdUser.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCadUsuNome.setText(rs.getString(2));
                cboCadUsuPerfil.setSelectedItem(rs.getString(3));
                txtCadUsuTel.setText(rs.getString(4));
                txtCadUsuEmail.setText(rs.getString(5));
                txtCadUsuLogin.setText(rs.getString(6));
                txtCadUsuSenha.setText(rs.getString(7));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterarUsuario() {
        String sql = "update tbusuarios set usuario = ?, perfil = ?, tel = ?, email = ?, login = ?, senha = ? where iduser = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadUsuNome.getText());
            pst.setString(2, cboCadUsuPerfil.getSelectedItem().toString());
            pst.setString(3, txtCadUsuTel.getText());
            pst.setString(4, txtCadUsuEmail.getText());
            pst.setString(5, txtCadUsuLogin.getText());
            pst.setString(6, txtCadUsuSenha.getText());
            pst.setString(7, txtCadUsuIdUser.getText());

            if ((txtCadUsuNome.getText().isEmpty()) || txtCadUsuEmail.getText().isEmpty()
                    || txtCadUsuLogin.getText().isEmpty() || txtCadUsuSenha.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos OBRIGATÓRIOS!");
            } else {

                int adicionado = pst.executeUpdate();
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!!!");
                    limparCamposUsuario();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void removerUsuario() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza remover este usuário?", "Atenção!!!", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuarios where iduser = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadUsuIdUser.getText());
                int apagado = pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!!!");
                limparCamposUsuario();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limparCamposUsuario() {
        txtCadUsuNome.setText(null);
        cboCadUsuPerfil.setSelectedIndex(0);
        txtCadUsuTel.setText(null);
        txtCadUsuEmail.setText(null);
        txtCadUsuLogin.setText(null);
        txtCadUsuSenha.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnCadUsuDel = new javax.swing.JButton();
        btnCadUsuCon = new javax.swing.JButton();
        btnCadUsuAdd = new javax.swing.JButton();
        btnCadUsuMod = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtCadUsuIdUser = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCadUsuNome = new javax.swing.JTextField();
        txtCadUsuEmail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCadUsuLogin = new javax.swing.JTextField();
        txtCadUsuSenha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCadUsuPerfil = new javax.swing.JComboBox<>();
        txtCadUsuTel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuário");
        setPreferredSize(new java.awt.Dimension(740, 484));

        btnCadUsuDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/removeuser.png"))); // NOI18N
        btnCadUsuDel.setToolTipText("Deletar");
        btnCadUsuDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadUsuDel.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadUsuDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadUsuDelActionPerformed(evt);
            }
        });

        btnCadUsuCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/iconsearch.png"))); // NOI18N
        btnCadUsuCon.setToolTipText("Consultar");
        btnCadUsuCon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadUsuCon.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadUsuCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadUsuConActionPerformed(evt);
            }
        });

        btnCadUsuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/adduser.png"))); // NOI18N
        btnCadUsuAdd.setToolTipText("Adicionar");
        btnCadUsuAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadUsuAdd.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadUsuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadUsuAddActionPerformed(evt);
            }
        });

        btnCadUsuMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/alteruser.png"))); // NOI18N
        btnCadUsuMod.setToolTipText("Modificar");
        btnCadUsuMod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadUsuMod.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadUsuMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadUsuModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadUsuAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadUsuCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadUsuMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadUsuDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadUsuCon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadUsuAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadUsuMod, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadUsuDel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("ID*");

        jLabel2.setText("Nome*");

        jLabel5.setText("E-mail*");

        jLabel6.setText("Login*");

        jLabel7.setText("Senha*");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("* Campos obrigatórios de Preenchimento");

        jLabel3.setText("Perfil*");

        cboCadUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "adm", "atd", "tec" }));

        jLabel4.setText("Telefone");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCadUsuNome)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(134, 134, 134))
                    .addComponent(txtCadUsuEmail)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadUsuIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCadUsuSenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                .addComponent(txtCadUsuLogin, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(123, 123, 123)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtCadUsuTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cboCadUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadUsuIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(txtCadUsuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCadUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadUsuTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        setBounds(0, 0, 795, 575);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadUsuConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadUsuConActionPerformed
        // Chamando o método consultar
        consultarUsuario();
    }//GEN-LAST:event_btnCadUsuConActionPerformed

    private void btnCadUsuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadUsuAddActionPerformed
        // Chamando o método adicionar
        adicionarUsuario();
    }//GEN-LAST:event_btnCadUsuAddActionPerformed

    private void btnCadUsuModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadUsuModActionPerformed
        // Chamando o método alterar
        alterarUsuario();
    }//GEN-LAST:event_btnCadUsuModActionPerformed

    private void btnCadUsuDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadUsuDelActionPerformed
        // Chamando o método remover
        removerUsuario();
    }//GEN-LAST:event_btnCadUsuDelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadUsuAdd;
    private javax.swing.JButton btnCadUsuCon;
    private javax.swing.JButton btnCadUsuDel;
    private javax.swing.JButton btnCadUsuMod;
    private javax.swing.JComboBox<String> cboCadUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCadUsuEmail;
    private javax.swing.JTextField txtCadUsuIdUser;
    private javax.swing.JTextField txtCadUsuLogin;
    private javax.swing.JTextField txtCadUsuNome;
    private javax.swing.JTextField txtCadUsuSenha;
    private javax.swing.JTextField txtCadUsuTel;
    // End of variables declaration//GEN-END:variables
}
