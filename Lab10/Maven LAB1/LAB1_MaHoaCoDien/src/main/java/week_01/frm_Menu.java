package week_01;

public class frm_Menu extends javax.swing.JFrame {

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_caesar = new javax.swing.JButton();
        btn_vigenere = new javax.swing.JButton();
        btn_railfence = new javax.swing.JButton();
        btn_playfair = new javax.swing.JButton();
        btn_transposition = new javax.swing.JButton();
        btn_autokey = new javax.swing.JButton();
        btn_tonghop = new javax.swing.JButton();
        btn_tancong = new javax.swing.JButton();
        btn_sql = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LAB 1 - Mã hóa đối xứng cổ điển");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("LAB 1 - MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN");

        jLabel2.setText("Chọn một bài để mở form tương ứng:");

        btn_caesar.setText("1.1 Caesar");
        btn_caesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caesarActionPerformed(evt);
            }
        });

        btn_vigenere.setText("1.2 Vigenère");
        btn_vigenere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vigenereActionPerformed(evt);
            }
        });

        btn_railfence.setText("1.3 Rail Fence");
        btn_railfence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_railfenceActionPerformed(evt);
            }
        });

        btn_playfair.setText("1.4 Playfair");
        btn_playfair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_playfairActionPerformed(evt);
            }
        });

        btn_transposition.setText("1.5 Transposition");
        btn_transposition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transpositionActionPerformed(evt);
            }
        });

        btn_autokey.setText("1.6 Autokey");
        btn_autokey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_autokeyActionPerformed(evt);
            }
        });

        btn_tonghop.setText("Nâng cao 01 - Tổng hợp");
        btn_tonghop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tonghopActionPerformed(evt);
            }
        });

        btn_tancong.setText("Nâng cao 02 - Tấn công");
        btn_tancong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tancongActionPerformed(evt);
            }
        });

        btn_sql.setText("Kết nối SQL Server");
        btn_sql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sqlActionPerformed(evt);
            }
        });

        jLabel3.setText("Mỗi form mở ra là một cửa sổ riêng; đóng form con không làm tắt chương trình.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_caesar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_vigenere, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_railfence, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_playfair, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_transposition, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_autokey, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_tonghop, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_tancong, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sql, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_caesar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_vigenere, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_railfence, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_playfair, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_transposition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_autokey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tonghop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tancong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sql, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_caesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caesarActionPerformed
        new frm_Caesar().setVisible(true);
    }//GEN-LAST:event_btn_caesarActionPerformed

    private void btn_vigenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vigenereActionPerformed
        new frm_Vigenere().setVisible(true);
    }//GEN-LAST:event_btn_vigenereActionPerformed

    private void btn_railfenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_railfenceActionPerformed
        new frm_RailFence().setVisible(true);
    }//GEN-LAST:event_btn_railfenceActionPerformed

    private void btn_playfairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_playfairActionPerformed
        new frm_Playfair().setVisible(true);
    }//GEN-LAST:event_btn_playfairActionPerformed

    private void btn_transpositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transpositionActionPerformed
        new frm_Transposition().setVisible(true);
    }//GEN-LAST:event_btn_transpositionActionPerformed

    private void btn_autokeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_autokeyActionPerformed
        new frm_Autokey().setVisible(true);
    }//GEN-LAST:event_btn_autokeyActionPerformed

    private void btn_tonghopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tonghopActionPerformed
        new frm_TongHop().setVisible(true);
    }//GEN-LAST:event_btn_tonghopActionPerformed

    private void btn_tancongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tancongActionPerformed
        new frm_TanCong().setVisible(true);
    }//GEN-LAST:event_btn_tancongActionPerformed

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sqlActionPerformed
        new frm_KetNoiSQL().setVisible(true);
    }//GEN-LAST:event_btn_sqlActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(frm_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_autokey;
    private javax.swing.JButton btn_caesar;
    private javax.swing.JButton btn_playfair;
    private javax.swing.JButton btn_railfence;
    private javax.swing.JButton btn_sql;
    private javax.swing.JButton btn_tancong;
    private javax.swing.JButton btn_tonghop;
    private javax.swing.JButton btn_transposition;
    private javax.swing.JButton btn_vigenere;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}

