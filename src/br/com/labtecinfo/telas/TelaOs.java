package br.com.labtecinfo.telas;

import java.sql.*;
import br.com.labtecinfo.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Criando uma variável para definir o rbt de entrada
    private String tipo;

    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // Método para pesquisar as informações do cliente
    private void pesquisarClienteOs(boolean isPesquisaOs) {
        String sql = "select idcli as ID, nomecli as Nome, endcli as Endereço, cpf as CPF, emailcli as Email, telcli as Telefone from tbclientes where nomecli like ? limit 4";
        
        if (isPesquisaOs) {
            sql = "select endcli as Endereço, emailcli as Email, cpf as CPF, telcli as Telefone from tbclientes where nomecli like ? limit 4";
        }

        try {
            if(!txtCadOSPes.getText().isEmpty()) {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadOSPes.getText() + "%");
                rs = pst.executeQuery();
                tblOSClientes.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                ((DefaultTableModel) tblOSClientes.getModel()).setRowCount(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCamposOs() {
        int setar = tblOSClientes.getSelectedRow();

        if (tblOSClientes.getModel().getColumnName(0).equals("ID")) {
            txtCadOSIdcli.setText(tblOSClientes.getModel().getValueAt(setar, 0).toString());
        }
    }

    private void emissaoOs() {
        String sql = "insert into tboss(tipo, situacao, equipamento, marca, modelo, N_Serie, estado, defeito, RT, laudo, valor, idcli) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboCadOSSituacao.getSelectedItem().toString());
            pst.setString(3, cboCadOSEquip.getSelectedItem().toString());
            pst.setString(4, cboCadOSMarca.getSelectedItem().toString());
            pst.setString(5, txtCadOSModelo.getText());
            pst.setString(6, txtCadOSNSerie.getText());
            pst.setString(7, txtCadOSEstado.getText());
            pst.setString(8, txtCadOSDefeito.getText());
            pst.setString(9, cboCadOSRT.getSelectedItem().toString());
            pst.setString(10, txtCadOSLaudo.getText());
            pst.setString(11, txtCadOSValor.getText().replace(",", "."));
            pst.setString(12, txtCadOSIdcli.getText());

            if (txtCadOSIdcli.getText().isEmpty() || txtCadOSModelo.getText().isEmpty() || txtCadOSNSerie.getText().isEmpty()
                    || txtCadOSEstado.getText().isEmpty() || txtCadOSDefeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço emitida com sucesso!");
                    //limpar_campos_os();
                    setarIdOs();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterarOs() {
        String sql = "update tboss set tipo=?, situacao=?, equipamento=?, marca=?, modelo=?, N_Serie=?, estado=?, defeito=?, RT=?, laudo=?, valor=? where idos=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboCadOSSituacao.getSelectedItem().toString());
            pst.setString(3, cboCadOSEquip.getSelectedItem().toString());
            pst.setString(4, cboCadOSMarca.getSelectedItem().toString());
            pst.setString(5, txtCadOSModelo.getText());
            pst.setString(6, txtCadOSNSerie.getText());
            pst.setString(7, txtCadOSEstado.getText());
            pst.setString(8, txtCadOSDefeito.getText());
            pst.setString(9, cboCadOSRT.getSelectedItem().toString());
            pst.setString(10, txtCadOSLaudo.getText());
            pst.setString(11, txtCadOSValor.getText().replace(",", "."));
            pst.setString(12, txtCadOSnumOS.getText());

            if (txtCadOSIdcli.getText().isEmpty() || txtCadOSModelo.getText().isEmpty() || txtCadOSNSerie.getText().isEmpty()
                    || txtCadOSEstado.getText().isEmpty() || txtCadOSDefeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencher todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço alterada com sucesso!");
                    limparCamposOs();

                    btnCadOSAdd.setEnabled(true);
                    txtCadOSPes.setEnabled(true);
                    tblOSClientes.setVisible(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void deletarOs() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a ordem de serviço?", "Atenção!!!", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tboss where idos=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadOSnumOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço removida com sucesso!");
                }
                limparCamposOs();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void pesquisarOrdemServico() {
        String num_os = JOptionPane.showInputDialog("Número da Ordem de Serviço");
        // String sql = "select * from tboss where idos = " + num_os;
        String sql = "select O.idos, date_format(data_os,'%d/%m/%Y - %H:%i'),tipo, situacao, equipamento, marca, modelo, N_serie, estado, defeito, RT, laudo, valor, C.idcli, nomecli, telcli from tboss as O inner join tbclientes as C on (O.idcli = C.idcli) where idos = " + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCadOSnumOS.setText(rs.getString(1));
                txtCadOSData.setText(rs.getString(2));
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("Ordem de Serviço")) {
                    rdbCadOSOS.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    rdbCadOSOrcamento.setSelected(true);
                    tipo = "Ordem de Orçamento";
                }
                cboCadOSSituacao.setSelectedItem(rs.getString(4));
                cboCadOSEquip.setSelectedItem(rs.getString(5));
                cboCadOSMarca.setSelectedItem(rs.getString(6));
                txtCadOSModelo.setText(rs.getString(7));
                txtCadOSNSerie.setText(rs.getString(8));
                txtCadOSEstado.setText(rs.getString(9));
                txtCadOSDefeito.setText(rs.getString(10));
                cboCadOSRT.setSelectedItem(rs.getString(11));
                txtCadOSLaudo.setText(rs.getString(12));
                txtCadOSValor.setText(rs.getString(13));
                txtCadOSIdcli.setText(rs.getString(14));
                txtCadOSPes.setText(rs.getString(15)); // Novo

                pesquisarClienteOs(true);

                btnCadOSAdd.setEnabled(false);
                txtCadOSPes.setEditable(false);
                // tblOSClientes.setVisible(false);
            } else {
                if(num_os != null) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço não cadastrada");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limparCamposOs() {
        // Limpa os campos
        txtCadOSnumOS.setText(null);
        txtCadOSData.setText(null);
        txtCadOSIdcli.setText(null);
        cboCadOSSituacao.setSelectedIndex(0);
        cboCadOSEquip.setSelectedIndex(0);
        cboCadOSMarca.setSelectedIndex(0);
        txtCadOSModelo.setText(null);
        txtCadOSNSerie.setText(null);
        txtCadOSEstado.setText(null);
        txtCadOSDefeito.setText(null);
        cboCadOSRT.setSelectedIndex(0);
        txtCadOSValor.setText("0");
        txtCadOSLaudo.setText(null);
        txtCadOSPes.setText(null);
        // Limpa a tabela
        ((DefaultTableModel) tblOSClientes.getModel()).setRowCount(0);
        // Define o radio button como orçamento
        rdbCadOSOrcamento.setSelected(true);
        tipo = "Ordem de orçamento";
        // Habilitar botão adicionar
        btnCadOSAdd.setEnabled(true);

        // habilitar pesquisar clientes
        txtCadOSPes.setEnabled(true);
    }

    private void imprimirOs() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma impressão de relatório do cliente", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                HashMap idos = new HashMap();
                idos.put("idos", Integer.parseInt(txtCadOSnumOS.getText()));
                
                String dirJasper = "C:/ireports/labtecinfo/ordem_de_servicos.jasper";
                JasperPrint print = JasperFillManager.fillReport(dirJasper, idos, conexao);
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Selecione uma ordem de serviço!");
            }
        }
    }

    // Método para mostrar o id inserir ordem de serviços
    private void setarIdOs() {
        String sql = "select max(idos) from tboss";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCadOSnumOS.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCadOSnumOS = new javax.swing.JTextField();
        txtCadOSData = new javax.swing.JTextField();
        rdbCadOSOrcamento = new javax.swing.JRadioButton();
        rdbCadOSOS = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        txtCadOSPes = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCadOSIdcli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOSClientes = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jPanel3 = new javax.swing.JPanel();
        btnCadOSAdd = new javax.swing.JButton();
        btnCadOSCon = new javax.swing.JButton();
        btnCadOSMod = new javax.swing.JButton();
        btnCadOSDel = new javax.swing.JButton();
        btnCadOSImprimir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboCadOSEquip = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboCadOSMarca = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCadOSModelo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCadOSNSerie = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCadOSLaudo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCadOSValor = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboCadOSRT = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cboCadOSSituacao = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtCadOSEstado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCadOSDefeito = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tela de Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(1, 1));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("OS"));

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtCadOSnumOS.setEditable(false);

        txtCadOSData.setEditable(false);
        txtCadOSData.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        buttonGroup1.add(rdbCadOSOrcamento);
        rdbCadOSOrcamento.setText("Orçamento");
        rdbCadOSOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbCadOSOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbCadOSOS);
        rdbCadOSOS.setText("Ordem de Serviço");
        rdbCadOSOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbCadOSOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdbCadOSOrcamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbCadOSOS))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCadOSnumOS)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtCadOSData, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadOSnumOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadOSData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbCadOSOrcamento)
                    .addComponent(rdbCadOSOS))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCadOSPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadOSPesKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/iconsearch20px.png"))); // NOI18N

        txtCadOSIdcli.setEditable(false);

        jLabel5.setText("ID");

        tblOSClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Endereço", "CPF", "Email", "Telefone"
            }
        ));
        tblOSClientes.getTableHeader().setReorderingAllowed(false);
        tblOSClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOSClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOSClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCadOSPes, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCadOSIdcli, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtCadOSPes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCadOSIdcli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        btnCadOSAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/adduser.png"))); // NOI18N
        btnCadOSAdd.setToolTipText("Adicionar");
        btnCadOSAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadOSAdd.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadOSAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadOSAddActionPerformed(evt);
            }
        });

        btnCadOSCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/iconsearch.png"))); // NOI18N
        btnCadOSCon.setToolTipText("Consultar");
        btnCadOSCon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadOSCon.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadOSCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadOSConActionPerformed(evt);
            }
        });

        btnCadOSMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/alteruser.png"))); // NOI18N
        btnCadOSMod.setToolTipText("Modificar");
        btnCadOSMod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadOSMod.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadOSMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadOSModActionPerformed(evt);
            }
        });

        btnCadOSDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/removeuser.png"))); // NOI18N
        btnCadOSDel.setToolTipText("Deletar");
        btnCadOSDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadOSDel.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadOSDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadOSDelActionPerformed(evt);
            }
        });

        btnCadOSImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/labtecinfo/icones/print.png"))); // NOI18N
        btnCadOSImprimir.setToolTipText("Imprimir");
        btnCadOSImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadOSImprimir.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCadOSImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadOSImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadOSAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadOSCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadOSMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadOSDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadOSImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadOSAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadOSMod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadOSDel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadOSImprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadOSCon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Equipamento*");

        cboCadOSEquip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Computador", "Notebook", "Smartfone", "Impressora", "Tablet", "Monitor", "SmartWatch", "PCI", "TVBox" }));

        jLabel7.setText("Marca*");

        cboCadOSMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Samsung", "LG", "Positivo", "HP", "DELL", "CCE", "Semp Toshiba", "Motorola", "Acer", "Lenovo", "Philco", "Megaware", "Multilaser", "Asus", "ASRock" }));

        jLabel8.setText("Modelo*");

        jLabel9.setText("N° Série*");

        jLabel13.setText("Laudo");

        jLabel14.setText("Valor R$");

        txtCadOSValor.setText("0");

        jLabel12.setText("Técnico Responsável*");

        cboCadOSRT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anna", "Gabriel", "Igor", "Larissa", "Marcio", "Matheus", "Sergio", "Weverton" }));

        jLabel3.setText("Situação*");

        cboCadOSSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguardando Orçamento", "Aguardando Componente", "Aguardando Autorização", "Aguardando Retirada", "Orçamento Aprovado", "Orçamento Recusado", "Orçamento em Análise", "Serviço Pronto", "Entregue", "Informar ao Cliente", "Cliente Informado para Retirada", "Retorno" }));

        jLabel10.setText("Estado do Equipamento*");

        jLabel11.setText("Defeito Reclamado*");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCadOSDefeito)
                            .addComponent(txtCadOSEstado)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCadOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCadOSRT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(10, 10, 10)
                        .addComponent(cboCadOSEquip, 0, 145, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCadOSMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadOSModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadOSNSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadOSLaudo)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCadOSEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(cboCadOSMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtCadOSModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtCadOSNSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCadOSEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCadOSDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCadOSLaudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboCadOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(txtCadOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCadOSRT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        setBounds(0, 0, 795, 575);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCadOSPesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadOSPesKeyReleased
        pesquisarClienteOs(false);
    }//GEN-LAST:event_txtCadOSPesKeyReleased

    private void tblOSClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOSClientesMouseClicked
        setarCamposOs();
    }//GEN-LAST:event_tblOSClientesMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        rdbCadOSOrcamento.setSelected(true);
        tipo = "Ordem de Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void rdbCadOSOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbCadOSOrcamentoActionPerformed
        tipo = "Ordem de Orçamento";
    }//GEN-LAST:event_rdbCadOSOrcamentoActionPerformed

    private void btnCadOSAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadOSAddActionPerformed
        emissaoOs();
    }//GEN-LAST:event_btnCadOSAddActionPerformed

    private void btnCadOSConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadOSConActionPerformed
        pesquisarOrdemServico();
    }//GEN-LAST:event_btnCadOSConActionPerformed

    private void rdbCadOSOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbCadOSOSActionPerformed
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rdbCadOSOSActionPerformed

    private void btnCadOSModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadOSModActionPerformed
        alterarOs();
    }//GEN-LAST:event_btnCadOSModActionPerformed

    private void btnCadOSDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadOSDelActionPerformed
        deletarOs();
    }//GEN-LAST:event_btnCadOSDelActionPerformed

    private void btnCadOSImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadOSImprimirActionPerformed
        imprimirOs();
    }//GEN-LAST:event_btnCadOSImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadOSAdd;
    private javax.swing.JButton btnCadOSCon;
    private javax.swing.JButton btnCadOSDel;
    private javax.swing.JButton btnCadOSImprimir;
    private javax.swing.JButton btnCadOSMod;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboCadOSEquip;
    private javax.swing.JComboBox<String> cboCadOSMarca;
    private javax.swing.JComboBox<String> cboCadOSRT;
    private javax.swing.JComboBox<String> cboCadOSSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbCadOSOS;
    private javax.swing.JRadioButton rdbCadOSOrcamento;
    private javax.swing.JTable tblOSClientes;
    private javax.swing.JTextField txtCadOSData;
    private javax.swing.JTextField txtCadOSDefeito;
    private javax.swing.JTextField txtCadOSEstado;
    private javax.swing.JTextField txtCadOSIdcli;
    private javax.swing.JTextField txtCadOSLaudo;
    private javax.swing.JTextField txtCadOSModelo;
    private javax.swing.JTextField txtCadOSNSerie;
    private javax.swing.JTextField txtCadOSPes;
    private javax.swing.JTextField txtCadOSValor;
    private javax.swing.JTextField txtCadOSnumOS;
    // End of variables declaration//GEN-END:variables
}
