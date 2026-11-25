/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import modelo.Empresa;
import dao.EmpresaDao;

/**
 *
 * @author f3l1p3
 * 
 * 
 */

public class TelaLoginEmpresa extends javax.swing.JFrame {

  
    /**
     * Creates new form TelaLoginEmpresa
     */
   public TelaLoginEmpresa() {
    initComponents();
    aplicarVisualModerno();
    configurarScrollBar();
    configurarMascarasEmpresa();
    configurarBuscaCepEmpresa();
    configurarEstados();
}
   
   private String gerarHashSenha(String senha) {

    try {

        java.security.MessageDigest md =
                java.security.MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(senha.getBytes("UTF-8"));

        StringBuilder hexadecimal = new StringBuilder();

        for (byte b : hash) {

            hexadecimal.append(
                    String.format("%02x", b & 0xff)
            );

        }

        return hexadecimal.toString();

    } catch (Exception erro) {

        throw new RuntimeException(erro);

    }
}
   
   private void configurarScrollBar() {

    // Dimensões da barra
    jScrollBar1.setPreferredSize(
        new java.awt.Dimension(12, 100)
    );

   jScrollBar1.setMinimum(0);
jScrollBar1.setMaximum(1000);
jScrollBar1.setVisibleAmount(700);

jScrollBar1.setUnitIncrement(1);
jScrollBar1.setBlockIncrement(10);

    jScrollBar1.addAdjustmentListener(evt -> {

        int posicao = evt.getValue();

        jPainelFundo.setLocation(
            jPainelFundo.getX(),
            80 - posicao
        );
    });
}
  
   
   
    
    private void salvarEmpresa() {

    // Recupera as senhas
    String senha = new String(jPasswordField5.getPassword());
    String confirmarSenha = new String(jPasswordField4.getPassword());

    // Valida os campos obrigatórios
    if (jTextField1.getText().trim().isEmpty()
            || jTextField5.getText().trim().isEmpty()
            || jTextField3.getText().trim().isEmpty()
            || CampoCurso.getText().trim().isEmpty()
            || jTextField6.getText().trim().isEmpty()
            || TextDescricao.getText().trim().isEmpty()) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos obrigatórios."
        );
        return;
    }

    // Valida CNPJ
    String cnpj = jFormattedTextField1.getText().replaceAll("\\D", "");

    if (cnpj.length() != 14) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Informe um CNPJ válido."
        );
        return;
    }

    // Valida CEP
    String cep = jFormattedTextField4.getText().replaceAll("\\D", "");

    if (cep.length() != 8) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Informe um CEP válido."
        );
        return;
    }

    // Valida telefone
    String telefone = jFormattedTextField2.getText().replaceAll("\\D", "");

    if (telefone.length() != 10 && telefone.length() != 11) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Informe um telefone válido."
        );
        return;
    }

    // Valida e-mail
    String emailAcesso = jTextField6.getText().trim();
String emailContato = CampoCurso.getText().trim();

String regexEmail = "^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";

if (!emailAcesso.matches(regexEmail)
        || !emailContato.matches(regexEmail)) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Informe e-mails válidos."
    );

    return;
}

    // Valida senha
    if (senha.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Informe uma senha."
        );
        return;
    }

    if (!senha.equals(confirmarSenha)) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "As senhas não coincidem."
        );
        return;
    }

    // Cria o objeto Empresa
    Empresa empresa = new Empresa();

    empresa.setRazaoSocial(jTextField1.getText().trim());
    empresa.setNomeFantasia(jTextField5.getText().trim());
    empresa.setCnpj(cnpj);
    empresa.setAreaAtuacao(jTextField3.getText().trim());

    empresa.setCep(cep);
    empresa.setLogradouro(jTextField7.getText().trim());
    empresa.setNumero(jTextField8.getText().trim());
    empresa.setComplemento(jTextField9.getText().trim());
    empresa.setBairro(jTextField10.getText().trim());
    empresa.setCidade(jTextField11.getText().trim());

    empresa.setUf(
            jComboBox2.getSelectedItem() == null
            ? ""
            : jComboBox2.getSelectedItem().toString()
    );

    empresa.setEmail(CampoCurso.getText().trim());
    empresa.setTelefone(telefone);

   
    empresa.setDescricao(TextDescricao.getText().trim());

   empresa.setEmailAcesso(emailAcesso);
empresa.setSenha(gerarHashSenha(senha));

    // Envia os dados para o banco
    EmpresaDao dao = new EmpresaDao();

    if (dao.inserir(empresa)) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Empresa cadastrada com sucesso!"
        );

    }
}


private void configurarMascarasEmpresa() {

    try {
        // Máscara do CNPJ
        javax.swing.text.MaskFormatter mascaraCNPJ =
            new javax.swing.text.MaskFormatter("##.###.###/####-##");
        mascaraCNPJ.setPlaceholderCharacter('_');
        mascaraCNPJ.install(jFormattedTextField1);

        // Máscara do CEP
        javax.swing.text.MaskFormatter mascaraCEP =
            new javax.swing.text.MaskFormatter("#####-###");
        mascaraCEP.setPlaceholderCharacter('_');
        mascaraCEP.install(jFormattedTextField4);

        // Máscara do telefone
        javax.swing.text.MaskFormatter mascaraTelefone =
            new javax.swing.text.MaskFormatter("(##) #####-####");
        mascaraTelefone.setPlaceholderCharacter('_');
        mascaraTelefone.install(jFormattedTextField2);

    } catch (java.text.ParseException erro) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Erro ao configurar as máscaras dos campos: " + erro.getMessage()
        );
    }
}

private void configurarBuscaCepEmpresa() {

    jFormattedTextField4.addFocusListener(
        new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                buscarCepEmpresa();
            }
        }
    );
}

private void configurarEstados() {

    String[] estados = {
        "AC", "AL", "AP", "AM", "BA", "CE",
        "DF", "ES", "GO", "MA", "MT", "MS",
        "MG", "PA", "PB", "PR", "PE", "PI",
        "RJ", "RN", "RS", "RO", "RR", "SC",
        "SP", "SE", "TO"
    };

    jComboBox2.setModel(
        new javax.swing.DefaultComboBoxModel<>(estados)
    );
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLogo = new javax.swing.JLabel();
        jmensagemlogo = new javax.swing.JLabel();
        SairBotao = new javax.swing.JButton();
        InicioBotao = new javax.swing.JButton();
        VagasBotao = new javax.swing.JButton();
        MeuPerflBotao = new javax.swing.JButton();
        jPainelFundo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPasswordField5 = new javax.swing.JPasswordField();
        jLabel39 = new javax.swing.JLabel();
        jPasswordField4 = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPerfil = new javax.swing.JLabel();
        j2 = new javax.swing.JLabel();
        jTexto2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        CampoCurso = new javax.swing.JTextField();
        jCurso = new javax.swing.JLabel();
        jFormacaoehabilidades = new javax.swing.JLabel();
        j3 = new javax.swing.JLabel();
        jTexto = new javax.swing.JLabel();
        jCurso1 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel10 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        CancelarBotao1 = new javax.swing.JButton();
        SalvaContinuarBotao1 = new javax.swing.JButton();
        jCurso2 = new javax.swing.JLabel();
        jFormacaoehabilidades1 = new javax.swing.JLabel();
        j4 = new javax.swing.JLabel();
        jTexto1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextDescricao = new javax.swing.JTextArea();
        jScrollBar1 = new javax.swing.JScrollBar();
        jAlterarFoto = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(12, 50, 111));
        jPanel2.setMaximumSize(new java.awt.Dimension(1300, 988));
        jPanel2.setMinimumSize(new java.awt.Dimension(1300, 988));
        jPanel2.setPreferredSize(new java.awt.Dimension(1300, 1800));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLogo.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        jLogo.setForeground(new java.awt.Color(180, 200, 220));
        jLogo.setText("</> DevJobs");
        jPanel2.add(jLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 30));

        jmensagemlogo.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jmensagemlogo.setForeground(new java.awt.Color(180, 200, 220));
        jmensagemlogo.setText("<html>Conectando talentos<br>ao futuro da tecnologia</html>");
        jPanel2.add(jmensagemlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 160, 40));

        SairBotao.setBackground(new java.awt.Color(19, 81, 180));
        SairBotao.setForeground(new java.awt.Color(255, 255, 255));
        SairBotao.setText("     Sair");
        SairBotao.setBorderPainted(false);
        SairBotao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SairBotao.setMaximumSize(new java.awt.Dimension(102, 102));
        SairBotao.setMinimumSize(new java.awt.Dimension(102, 102));
        SairBotao.setPreferredSize(new java.awt.Dimension(102, 102));
        SairBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SairBotaoActionPerformed(evt);
            }
        });
        jPanel2.add(SairBotao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 140, 50));

        InicioBotao.setBackground(new java.awt.Color(12, 50, 111));
        InicioBotao.setForeground(new java.awt.Color(255, 255, 255));
        InicioBotao.setText("     Inicio");
        InicioBotao.setBorderPainted(false);
        InicioBotao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        InicioBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InicioBotaoActionPerformed(evt);
            }
        });
        jPanel2.add(InicioBotao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 140, 50));

        VagasBotao.setBackground(new java.awt.Color(12, 50, 111));
        VagasBotao.setForeground(new java.awt.Color(255, 255, 255));
        VagasBotao.setText("     Vagas");
        VagasBotao.setBorderPainted(false);
        VagasBotao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        VagasBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VagasBotaoActionPerformed(evt);
            }
        });
        jPanel2.add(VagasBotao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 140, 50));

        MeuPerflBotao.setBackground(new java.awt.Color(12, 50, 111));
        MeuPerflBotao.setForeground(new java.awt.Color(255, 255, 255));
        MeuPerflBotao.setText("  Meu Perfil");
        MeuPerflBotao.setBorderPainted(false);
        MeuPerflBotao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MeuPerflBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeuPerflBotaoActionPerformed(evt);
            }
        });
        jPanel2.add(MeuPerflBotao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 140, 50));

        jPainelFundo.setBackground(new java.awt.Color(255, 255, 255));
        jPainelFundo.setPreferredSize(new java.awt.Dimension(1000, 1800));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 500, 15));

        jLabel48.setBackground(new java.awt.Color(255, 255, 255));
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/key-round.png"))); // NOI18N
        jLabel48.setMaximumSize(new java.awt.Dimension(15, 15));
        jLabel48.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel48.setOpaque(true);
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 28, 28));

        jLabel30.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(12, 50, 111));
        jLabel30.setText("Dados de Acesso");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 500, 25));

        jLabel31.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(153, 153, 153));
        jLabel31.setText("Login do candidato");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 500, 15));

        jLabel32.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel32.setText("E-mail  de Acesso*");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 300, 20));

        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 520, 30));

        jLabel38.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel38.setText("Senha*");
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 200, 20));

        jPasswordField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField5ActionPerformed(evt);
            }
        });
        jPanel1.add(jPasswordField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 350, 30));

        jLabel39.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel39.setText("Confirmar Senha*");
        jPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 200, 20));

        jPasswordField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField4ActionPerformed(evt);
            }
        });
        jPanel1.add(jPasswordField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 420, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(12, 50, 111));
        jLabel6.setText("Dados da empresa");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 500, 25));

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/building-complex.png"))); // NOI18N
        jLabel33.setMaximumSize(new java.awt.Dimension(15, 15));
        jLabel33.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel33.setOpaque(true);
        jPanel6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 28, 28));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Identificação e contato da empresa");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 500, 15));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel6.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 250, 30));

        jLabel10.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel10.setText("Razao Social *");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 300, 20));
        jPanel6.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 150, 30));

        jLabel11.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel11.setText("Nome Fantasia *");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 200, 20));

        jLabel14.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel14.setText("Area de atuacao *");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 200, 20));
        jPanel6.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 150, 30));

        jFormattedTextField1.setText("__-___-___/____-__");
        jPanel6.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 250, 30));

        jLabel13.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel13.setText("CNPJ *");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 200, 20));

        jLabel12.setText("foto");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 80, 80));

        jButton5.setText("Alterar Foto");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 160, 90, 25));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 9)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Opcional");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 185, 90, 15));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPerfil.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jPerfil.setForeground(new java.awt.Color(12, 50, 111));
        jPerfil.setText("Localizacao");
        jPanel8.add(jPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 6, 99, -1));

        j2.setBackground(new java.awt.Color(255, 255, 255));
        j2.setForeground(new java.awt.Color(255, 255, 255));
        j2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/map-pin.png"))); // NOI18N
        j2.setOpaque(true);
        jPanel8.add(j2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 28, 28));

        jTexto2.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jTexto2.setForeground(new java.awt.Color(153, 153, 153));
        jTexto2.setText("Informações sobre localidade");
        jPanel8.add(jTexto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 500, 15));

        jLabel16.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel16.setText("CEP *");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 200, 20));

        jFormattedTextField4.setText("_____-___");
        jPanel8.add(jFormattedTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 100, 30));

        jLabel17.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel17.setText("Logradouro *");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 200, 20));
        jPanel8.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 250, 30));

        jLabel18.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel18.setText("Número *");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 200, 20));
        jPanel8.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 80, 30));
        jPanel8.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 100, 30));

        jLabel20.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel20.setText("Complemento");
        jLabel20.setToolTipText("");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 200, 20));

        jLabel22.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel22.setText("Bairro *");
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 200, 20));

        jTextField10.setActionCommand("<Not Set>");
        jPanel8.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 200, 30));

        jLabel21.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel21.setText("Cidade *");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 200, 20));
        jPanel8.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 200, 30));

        jLabel26.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jLabel26.setText("UF *");
        jPanel8.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 200, 20));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel8.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 80, 30));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        CampoCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoCursoActionPerformed(evt);
            }
        });

        jCurso.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jCurso.setText("E-mail *");

        jFormacaoehabilidades.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jFormacaoehabilidades.setForeground(new java.awt.Color(12, 50, 111));
        jFormacaoehabilidades.setText("Contato");

        j3.setBackground(new java.awt.Color(255, 255, 255));
        j3.setForeground(new java.awt.Color(255, 255, 255));
        j3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/contact-round.png"))); // NOI18N
        j3.setOpaque(true);

        jTexto.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jTexto.setForeground(new java.awt.Color(153, 153, 153));
        jTexto.setText("Informacoes sobre contato");

        jCurso1.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jCurso1.setText("Telefone *");

        jFormattedTextField2.setText("(  ) _____-____");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(j3)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormacaoehabilidades, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jCurso))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(CampoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCurso1)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(759, 759, 759))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jFormacaoehabilidades, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(j3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCurso)
                    .addComponent(jCurso1))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CampoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(380, 380, 380))
        );

        jPanel9.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 820, 150));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 760, 0));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        CancelarBotao1.setText("Cancelar");
        CancelarBotao1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SalvaContinuarBotao1.setBackground(new java.awt.Color(19, 81, 180));
        SalvaContinuarBotao1.setForeground(new java.awt.Color(255, 255, 255));
        SalvaContinuarBotao1.setText("Salvar e Continuar");
        SalvaContinuarBotao1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SalvaContinuarBotao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvaContinuarBotao1ActionPerformed(evt);
            }
        });

        jCurso2.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jCurso2.setText("Descrição da empresa *");

        jFormacaoehabilidades1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jFormacaoehabilidades1.setForeground(new java.awt.Color(12, 50, 111));
        jFormacaoehabilidades1.setText("Apresentacao");

        j4.setBackground(new java.awt.Color(255, 255, 255));
        j4.setForeground(new java.awt.Color(255, 255, 255));
        j4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/file-text.png"))); // NOI18N
        j4.setOpaque(true);

        jTexto1.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jTexto1.setForeground(new java.awt.Color(153, 153, 153));
        jTexto1.setText("Conhecimentos do candidato");

        TextDescricao.setColumns(20);
        TextDescricao.setRows(5);
        jScrollPane1.setViewportView(TextDescricao);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFormacaoehabilidades1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jCurso2)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(CancelarBotao1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128)
                                .addComponent(SalvaContinuarBotao1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormacaoehabilidades1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCurso2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelarBotao1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SalvaContinuarBotao1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(203, 203, 203))
        );

        jPanel10.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout jPainelFundoLayout = new javax.swing.GroupLayout(jPainelFundo);
        jPainelFundo.setLayout(jPainelFundoLayout);
        jPainelFundoLayout.setHorizontalGroup(
            jPainelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPainelFundoLayout.createSequentialGroup()
                .addGroup(jPainelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPainelFundoLayout.setVerticalGroup(
            jPainelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPainelFundoLayout.createSequentialGroup()
                .addGroup(jPainelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPainelFundoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPainelFundoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPainelFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 840, 1170));

        jAlterarFoto.setBackground(new java.awt.Color(19, 81, 180));
        jAlterarFoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(220, 230, 240));
        jLabel3.setText("Cadastro da Empresa");
        jAlterarFoto.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 30));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(220, 230, 240));
        jLabel4.setText("Crie o perfil da sua organizacao ");
        jAlterarFoto.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 700, 20));

        jPanel2.add(jAlterarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 840, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1251, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SairBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SairBotaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SairBotaoActionPerformed

    private void InicioBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InicioBotaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InicioBotaoActionPerformed

    private void VagasBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VagasBotaoActionPerformed
        TelaVagas vagas = new TelaVagas();
        vagas.setLocationRelativeTo(null);
        vagas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VagasBotaoActionPerformed

    private void MeuPerflBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeuPerflBotaoActionPerformed
        // TODO add your handling code here:
        }
    
        private void aplicarVisualModerno() {
            // PALETA DE CORES DEVJOBS
            java.awt.Color azul = new java.awt.Color(19, 81, 180);
            java.awt.Color azulEscuro = new java.awt.Color(12, 50, 111);
            java.awt.Color fundo = new java.awt.Color(245, 247, 251);
            java.awt.Color branco = java.awt.Color.WHITE;
            java.awt.Color texto = new java.awt.Color(40, 50, 65);
            java.awt.Color cinza = new java.awt.Color(120, 130, 145);
            java.awt.Color borda = new java.awt.Color(220, 226, 235);

            // TIPOGRAFIA
            java.awt.Font fonte = new java.awt.Font(
                "Ubuntu", java.awt.Font.PLAIN, 13
            );

            java.awt.Font fonteNegrito = new java.awt.Font(
                "Ubuntu", java.awt.Font.BOLD, 13
            );

            java.awt.Font titulo = new java.awt.Font(
                "Ubuntu", java.awt.Font.BOLD, 17
            );

            java.awt.Font subtitulo = new java.awt.Font(
                "Ubuntu", java.awt.Font.PLAIN, 11
            );

            // FUNDOS
            getContentPane().setBackground(fundo);

            jPanel2.setBackground(azulEscuro);
            jPainelFundo.setBackground(fundo);
            jAlterarFoto.setBackground(azul);

            // CARTÕES
            javax.swing.JPanel[] paineis = {
                jPanel1, jPanel6, jPanel8, jPanel9, jPanel10
            };

            for (javax.swing.JPanel painel : paineis) {
                painel.setBackground(branco);
                painel.setBorder(
                    javax.swing.BorderFactory.createLineBorder(borda, 1)
                );
            }

            // LOGO
            jLogo.setFont(new java.awt.Font(
                "Ubuntu", java.awt.Font.BOLD, 21
            ));
            jLogo.setForeground(branco);

            jmensagemlogo.setFont(subtitulo);
            jmensagemlogo.setForeground(
                new java.awt.Color(195, 210, 235)
            );

            // MENU LATERAL
            javax.swing.JButton[] botoesMenu = {
                InicioBotao,
                VagasBotao,
                MeuPerflBotao,
                SairBotao
            };

            for (javax.swing.JButton botao : botoesMenu) {
                botao.setFont(fonteNegrito);
                botao.setForeground(branco);
                botao.setFocusPainted(false);
                botao.setBorderPainted(false);
                botao.setOpaque(true);
                botao.setCursor(
                    new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
                );
                botao.setHorizontalAlignment(
                    javax.swing.SwingConstants.LEFT
                );
            }

            // MENU ATIVO
            InicioBotao.setBackground(azulEscuro);
            VagasBotao.setBackground(azulEscuro);
            MeuPerflBotao.setBackground(azul);
            SairBotao.setBackground(azulEscuro);

            // CABEÇALHO PRINCIPAL
            jLabel3.setFont(new java.awt.Font(
                "Ubuntu", java.awt.Font.BOLD, 23
            ));
            jLabel3.setForeground(branco);

            jLabel4.setFont(new java.awt.Font(
                "Ubuntu", java.awt.Font.PLAIN, 12
            ));
            jLabel4.setForeground(
                new java.awt.Color(225, 235, 250)
            );

            // TÍTULOS DOS CARTÕES
            javax.swing.JLabel[] titulos = {
                jLabel6,
    jPerfil,
    jFormacaoehabilidades,
    jFormacaoehabilidades1
            };

            for (javax.swing.JLabel label : titulos) {
                label.setFont(titulo);
                label.setForeground(azulEscuro);
            }

            // SUBTÍTULOS
            javax.swing.JLabel[] subtitulos = {
                jLabel31,
    jLabel7,
    jTexto2,
    jTexto,
    jTexto1
            };

            for (javax.swing.JLabel label : subtitulos) {
                label.setFont(subtitulo);
                label.setForeground(cinza);
            }

            // INFORMAÇÕES DA EMPRESA
            javax.swing.JLabel[] informacoes = {
                jLabel10,
                jLabel17,
                jLabel18,
                jLabel21
            };

            for (javax.swing.JLabel label : informacoes) {
                label.setFont(fonte);
                label.setForeground(texto);
            }

            

            // BOTÕES
            javax.swing.JButton[] botoes = {
               InicioBotao,
    VagasBotao,
    MeuPerflBotao,
    SairBotao,
    CancelarBotao1,
    SalvaContinuarBotao1,
    jButton5
            };

            for (javax.swing.JButton botao : botoes) {
                botao.setFont(fonteNegrito);
                botao.setFocusPainted(false);
                botao.setCursor(
                    new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
                );
            }

          

            // BOTÃO ALTERAR FOTO
            jButton5.setBackground(branco);
            jButton5.setForeground(azulEscuro);
            jButton5.setBorder(
                javax.swing.BorderFactory.createLineBorder(borda)
            );

           

            // ÁREA SOBRE A EMPRESA
            TextDescricao.setFont(fonte);
            TextDescricao.setForeground(texto);
            TextDescricao.setBackground(branco);
            TextDescricao.setCaretColor(azul);
            TextDescricao.setLineWrap(true);
            TextDescricao.setWrapStyleWord(true);

            jScrollPane1.setBorder(
                javax.swing.BorderFactory.createLineBorder(borda)
            );
            jScrollPane1.getViewport().setBackground(branco);

           

            // SEPARADORES
            jSeparator2.setForeground(borda);

            revalidate();
            repaint();
    }//GEN-LAST:event_MeuPerflBotaoActionPerformed

    private void jPasswordField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField5ActionPerformed

    private void jPasswordField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField4ActionPerformed

    private void CampoCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoCursoActionPerformed

    private void SalvaContinuarBotao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvaContinuarBotao1ActionPerformed
        salvarEmpresa();
    }//GEN-LAST:event_SalvaContinuarBotao1ActionPerformed

    private void buscarCepEmpresa() {

    try {

        String cep = jFormattedTextField4.getText()
                .replaceAll("\\D", "");

        if (cep.length() != 8) {
            return;
        }

        URL url = new URL(
            "https://viacep.com.br/ws/" + cep + "/json/"
        );

        HttpURLConnection conexao =
                (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(5000);
        conexao.setReadTimeout(5000);

        BufferedReader leitor = new BufferedReader(
                new InputStreamReader(
                    conexao.getInputStream(), "UTF-8"
                )
        );

        StringBuilder resposta = new StringBuilder();
        String linha;

        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }

        leitor.close();
        conexao.disconnect();

        String json = resposta.toString();

        if (json.replaceAll("\\s", "").contains("\"erro\":true")) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "CEP não encontrado!"
            );
            return;
        }

        jTextField7.setText(pegarValor(json, "logradouro"));
        jTextField10.setText(pegarValor(json, "bairro"));
        jTextField11.setText(pegarValor(json, "localidade"));
        jComboBox2.setSelectedItem(pegarValor(json, "uf"));

    } catch (Exception erro) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Erro ao consultar o CEP: " + erro.getMessage()
        );
    }
}

private String pegarValor(String json, String campo) {

    String busca = "\"" + campo + "\":";
    int inicio = json.indexOf(busca);

    if (inicio == -1) {
        return "";
    }

    inicio += busca.length();

    while (inicio < json.length()
            && Character.isWhitespace(json.charAt(inicio))) {
        inicio++;
    }

    if (inicio >= json.length() || json.charAt(inicio) != '"') {
        return "";
    }

    inicio++;

    int fim = json.indexOf("\"", inicio);

    if (fim == -1) {
        return "";
    }

    return json.substring(inicio, fim);
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLoginEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLoginEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLoginEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLoginEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLoginEmpresa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CampoCurso;
    private javax.swing.JTextField CampoCurso1;
    private javax.swing.JButton CancelarBotao1;
    private javax.swing.JButton InicioBotao;
    private javax.swing.JButton MeuPerflBotao;
    private javax.swing.JButton SairBotao;
    private javax.swing.JButton SalvaContinuarBotao1;
    private javax.swing.JTextArea TextDescricao;
    private javax.swing.JButton VagasBotao;
    private javax.swing.JLabel j2;
    private javax.swing.JLabel j3;
    private javax.swing.JLabel j4;
    private javax.swing.JLabel j5;
    private javax.swing.JPanel jAlterarFoto;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jCurso;
    private javax.swing.JLabel jCurso1;
    private javax.swing.JLabel jCurso2;
    private javax.swing.JLabel jCurso3;
    private javax.swing.JLabel jCurso4;
    private javax.swing.JLabel jFormacaoehabilidades;
    private javax.swing.JLabel jFormacaoehabilidades1;
    private javax.swing.JLabel jFormacaoehabilidades2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLogo;
    private javax.swing.JPanel jPainelFundo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JPasswordField jPasswordField5;
    private javax.swing.JLabel jPerfil;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel jTexto;
    private javax.swing.JLabel jTexto1;
    private javax.swing.JLabel jTexto2;
    private javax.swing.JLabel jTexto3;
    private javax.swing.JLabel jmensagemlogo;
    // End of variables declaration//GEN-END:variables
}
