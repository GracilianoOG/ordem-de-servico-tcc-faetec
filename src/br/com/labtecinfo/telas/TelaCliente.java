package br.com.labtecinfo.telas;

import java.sql.*;
import br.com.labtecinfo.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // Ordem do rs = ordem das colunas no BD | Ordem do pst = ordem das interrogações ?
    private void adicionarCliente() {
        String sql = "insert into tbclientes(nomecli, endcli, emailcli, cpf, telcli) values(?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadCliNome.getText());
            pst.setString(2, txtCadCliEnd.getText());
            pst.setString(3, txtCadCliEmail.getText());
            pst.setString(4, txtCadCliCPF.getText());
            pst.setString(5, txtCadCliTel.getText());

            // Validação
            if ((txtCadCliNome.getText().isEmpty()) || txtCadCliCPF.getText().isEmpty() || txtCadCliEmail.getText().isEmpty() || txtCadCliCPF.getText().isEmpty() || txtCadCliTel.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos OBRIGATÓRIOS!");
            } else {

                int adicionado = pst.executeUpdate();
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                    limparCamposCliente();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterarCliente() {
        String sql = "update tbclientes set nomecli = ?, endcli = ?, emailcli = ?, cpf = ?, telcli = ? where idcli = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadCliNome.getText());
            pst.setString(2, txtCadCliEnd.getText());
            pst.setString(3, txtCadCliEmail.getText());
            pst.setString(4, txtCadCliCPF.getText());
            pst.setString(5, txtCadCliTel.getText());
            pst.setString(6, txtCadCliId.getText());

            if ((txtCadCliNome.getText().isEmpty()) || txtCadCliCPF.getText().isEmpty() || txtCadCliEmail.getText().isEmpty() || txtCadCliCPF.getText().isEmpty() || txtCadCliTel.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos OBRIGATÓRIOS!");
            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
                    limparCamposCliente();
                    btnCadCliAdd.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Métodos para pesquisar clientes
    private void pesquisarCliente() {
        String sql = "select idcli as ID, nomecli as Nome, endcli as Endereço, emailcli as Email, cpf as CPF, telcli as Telefone from tbclientes where nomecli like ? limit 4";

        try {
            // Se o campo de pesquisa não estiver vazio, mostre o conteúdo adequado
            if(!txtCadCliPes.getText().isEmpty()) {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadCliPes.getText() + "%");
                rs = pst.executeQuery();
                tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void preencherTabelaCampos() {
        int preencher = tblClientes.getSelectedRow();
        txtCadCliId.setText(tblClientes.getModel().getValueAt(preencher, 0).toString());
        txtCadCliNome.setText(tblClientes.getModel().getValueAt(preencher, 1).toString());
        txtCadCliEnd.setText(tblClientes.getModel().getValueAt(preencher, 2).toString());
        txtCadCliEmail.setText(tblClientes.getModel().getValueAt(preencher, 3).toString());
        txtCadCliCPF.setText(tblClientes.getModel().getValueAt(preencher, 4).toString());
        txtCadCliTel.setText(tblClientes.getModel().getValueAt(preencher, 5).toString());
        btnCadCliAdd.setEnabled(false);
    }

    private void removerCliente() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção!", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbclientes where idcli = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadCliId.getText());
                int apagado = pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                limparCamposCliente();
                btnCadCliAdd.setEnabled(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limparCamposCliente() {
        txtCadCliPes.setText(null);
        txtCadCliId.setText(null);
        txtCadCliNome.setText(null);
        txtCadCliEnd.setText(null);
        txtCadCliEmail.setText(null);
        txtCadCliCPF.setText(null);
        txtCadCliTel.setText(null);
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCadCliPes = new javax.swing.JTextField();
        txtCadCliNome = new javax.swing.JTextField();
        txtCadCliEmail = new javax.swing.JTextField();
        txtCadCliCPF = new javax.swing.JTextField();
        txtCadCliTel = new javax.swing.JTextField();
        btnCadCliAdd = new javax.swing.JButton();
        btnCadCliMod = new javax.swing.JButton();
        btnCadCliDel = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCadCliEnd = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel8 = new javax.swing.JLabel();
        txtCadCliId = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cliente");
        setPreferredSize(new java.awt.Dimension(740, 484));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/iconsearch20px.png"))); // NOI18N

        jLabel2.setText("Nome*");

        jLabel3.setText("Endereço*");

        jLabel4.setText("E-mail*");

        jLabel5.setText("CPF*");

        jLabel6.setText("Telefone*");

        txtCadCliPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadCliPesKeyReleased(evt);
            }
        });

        btnCadCliAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/adduser.png"))); // NOI18N
        btnCadCliAdd.setToolTipText("Adicionar");
        btnCadCliAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadCliAdd.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadCliAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCliAddActionPerformed(evt);
            }
        });

        btnCadCliMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/alteruser.png"))); // NOI18N
        btnCadCliMod.setToolTipText("Modificar");
        btnCadCliMod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadCliMod.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadCliMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCliModActionPerformed(evt);
            }
        });

        btnCadCliDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/removeuser.png"))); // NOI18N
        btnCadCliDel.setToolTipText("Deletar");
        btnCadCliDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadCliDel.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadCliDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCliDelActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("* Campos obrigatórios de Preenchimento");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Endereço", "Email", "CPF", "Telefone"
            }
        ));
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        jLabel8.setText("ID");

        txtCadCliId.setToolTipText("Campo automático");
        txtCadCliId.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCadCliId.setEnabled(false);
        txtCadCliId.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(btnCadCliAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadCliMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadCliDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(txtCadCliCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtCadCliTel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtCadCliNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCadCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtCadCliEnd)
                                    .addComponent(txtCadCliEmail)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCadCliPes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(121, 121, 121)
                                .addComponent(jLabel10))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCadCliPes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(txtCadCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCliEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCadCliCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCadCliTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadCliAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadCliMod, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadCliDel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        setBounds(0, 0, 795, 575);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadCliAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCliAddActionPerformed
        adicionarCliente();
    }//GEN-LAST:event_btnCadCliAddActionPerformed

    private void btnCadCliModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCliModActionPerformed
        alterarCliente();
    }//GEN-LAST:event_btnCadCliModActionPerformed

    private void btnCadCliDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCliDelActionPerformed
        removerCliente();
    }//GEN-LAST:event_btnCadCliDelActionPerformed

    private void txtCadCliPesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadCliPesKeyReleased
        pesquisarCliente();
    }//GEN-LAST:event_txtCadCliPesKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        preencherTabelaCampos();
    }//GEN-LAST:event_tblClientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadCliAdd;
    private javax.swing.JButton btnCadCliDel;
    private javax.swing.JButton btnCadCliMod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCadCliCPF;
    private javax.swing.JTextField txtCadCliEmail;
    private javax.swing.JTextField txtCadCliEnd;
    private javax.swing.JTextField txtCadCliId;
    private javax.swing.JTextField txtCadCliNome;
    private javax.swing.JTextField txtCadCliPes;
    private javax.swing.JTextField txtCadCliTel;
    // End of variables declaration//GEN-END:variables
}
