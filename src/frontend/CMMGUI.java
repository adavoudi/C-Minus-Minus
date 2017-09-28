package frontend;

import Core.CMM;
import Core.Code;
import Core.ParseException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alireza
 */
public class CMMGUI extends javax.swing.JFrame {

    private File sourceFile, ASMSource, objFile, exeFile;
    private String assemblerPath;
    private String asmFile;
    private Process ExePS;
    private JFrame ASMPanel;
    private RSyntaxTextArea txtEditor, txtASM;

    public CMMGUI() {
        initComponents();

        //adding editor
        editorPane.setLayout(new BorderLayout());
        txtEditor = new RSyntaxTextArea(20, 60);
        txtEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        txtEditor.setCodeFoldingEnabled(true);
        txtEditor.setAntiAliasingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(txtEditor);
        sp.setFoldIndicatorEnabled(true);
        editorPane.add(sp);

//        assemblerPath = "C:/Users/alireza/Documents/NetBeansProjects/CMM";
        assemblerPath = ""; // current folder
//        asmFile = "C:/Users/alireza/Documents/NetBeansProjects/CMM/asm";
        asmFile = ""; // current folder
    }

    private void initFiles(File CMMFile) {
        int cmmIndex = CMMFile.getName().lastIndexOf(".CMM");
        String name = CMMFile.getName().substring(0, cmmIndex);

        ASMSource = new File(CMMFile.getParent() + "/" + name + ".ASM");
        objFile = new File(CMMFile.getParent() + "/" + name + ".obj");
        exeFile = new File(CMMFile.getParent() + "/" + name + ".exe");
    }

    private void openFile() {
        try {
            Scanner input = new Scanner(sourceFile);
            StringBuilder code = new StringBuilder();
            while (input.hasNext()) {
                code.append(input.nextLine() + "\n");
            }
            txtEditor.setText(code.toString());
            input.close();
            btnCompile.setEnabled(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveFile() {
        FileOutputStream output;
        try {
            output = new FileOutputStream(sourceFile);
            output.write(txtEditor.getText().getBytes());
            output.close();
            btnCompile.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(CMMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void convertToASM() throws ParseException, IOException {
        InputStream in = new FileInputStream(sourceFile);

        CMM cmm = new CMM(in);
        cmm.start();

        StringBuilder asm = new StringBuilder();
        Scanner asmReader = new Scanner(new File("asm"));

        while (asmReader.hasNext()) {
            asm.append(asmReader.nextLine() + "\n");
        }

        asmReader.close();

        int indexOfRes = asm.indexOf("{res_size}");
        asm.replace(indexOfRes, indexOfRes + "{res_size}".length(),
                cmm.getCurrentAddr() + "");

        StringBuilder strings = new StringBuilder();
        for (String name : cmm.getStrings().keySet()) {
            strings.append("\t" + name + " DB " + cmm.getStrings().get(name)
                    + "\n");
        }

        int indexOfStrings = asm.indexOf("{strings}");
        asm.replace(indexOfStrings, indexOfStrings + "{strings}".length(), "\n"
                + strings.toString());

        StringBuilder codeSeg = new StringBuilder();

        for (Code c : cmm.getFunctions().get("main")) {
            codeSeg.append("\t\t" + c + "\n");
        }

        int indexOfCode = asm.indexOf("{code_segment}");
        asm.replace(indexOfCode, indexOfCode + "{code_segment}".length(), "\n"
                + codeSeg.toString());

        StringBuilder functions = new StringBuilder();

        Enumeration<String> enu = cmm.getFunctions().keys();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            if (name.equals("main")) {
                continue;
            }
            StringBuilder func = new StringBuilder();
            func.append("\t" + name + " PROC NEAR\n");

            for (Code c : cmm.getFunctions().get(name)) {
                func.append("\t\t" + c + "\n");
            }
            func.append("\t" + name + " ENDP\n");
            functions.append(func.toString());
        }

        int indexOfFunc = asm.indexOf("{functions}");
        asm.replace(indexOfFunc, indexOfFunc + "{functions}".length(), "\n"
                + functions.toString());



        FileOutputStream output;
        output = new FileOutputStream(ASMSource);
        output.write(asm.toString().getBytes());
        output.close();
        btnASM.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCompile = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnASM = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        editorPane = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("C-- IDE");
        setIconImages(null);
        setName("CMMGUI"); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 500));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnOpen.setText("Open");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);

        btnSave.setText("Save");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnCompile.setText("Compile");
        btnCompile.setEnabled(false);
        btnCompile.setFocusable(false);
        btnCompile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompileActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompile);

        btnRun.setText("Run");
        btnRun.setEnabled(false);
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRun);

        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jToolBar1.add(btnStop);

        btnASM.setText("ASM");
        btnASM.setEnabled(false);
        btnASM.setFocusable(false);
        btnASM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnASM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnASM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnASMActionPerformed(evt);
            }
        });
        jToolBar1.add(btnASM);

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout editorPaneLayout = new javax.swing.GroupLayout(editorPane);
        editorPane.setLayout(editorPaneLayout);
        editorPaneLayout.setHorizontalGroup(
            editorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 765, Short.MAX_VALUE)
        );
        editorPaneLayout.setVerticalGroup(
            editorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(editorPane);

        txtStatus.setEditable(false);
        txtStatus.setBackground(new java.awt.Color(102, 102, 102));
        txtStatus.setColumns(20);
        txtStatus.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtStatus.setForeground(new java.awt.Color(255, 255, 255));
        txtStatus.setRows(5);
        jScrollPane2.setViewportView(txtStatus);

        jSplitPane2.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        JFileChooser fileChooser = new JFileChooser(sourceFile);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".CMM") || f.isDirectory()) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "C Mines Mines source file";
            }
        });
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            sourceFile = fileChooser.getSelectedFile();
            openFile();
            initFiles(sourceFile);
        }


    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        JFileChooser fileChooser = new JFileChooser(sourceFile);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".CMM") || f.isDirectory()) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "C Mines Mines source file";
            }
        });
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().endsWith(".CMM")) {
                sourceFile = new File(selectedFile.getAbsolutePath() + ".CMM");
            } else {
                sourceFile = selectedFile;
            }
            saveFile();
            initFiles(sourceFile);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompileActionPerformed
        try {
            saveFile();
            convertToASM();

            String command = "cmd /c cd " + sourceFile.getParent() + " & " + assemblerPath + "ml.exe /Bl  " + assemblerPath + "link.exe " + ASMSource.getPath();
            Process ps = Runtime.getRuntime().exec(command);
            Scanner input = new Scanner(ps.getInputStream());
            while (input.hasNext()) {
                txtStatus.append(input.nextLine() + "\n");
            }
            input.close();
            ps.destroy();
            btnRun.setEnabled(true);
        } catch (ParseException pe) {
            txtStatus.append(pe.getMessage() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(CMMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCompileActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        try {
            ExePS = Runtime.getRuntime().exec(exeFile.getPath());
            btnStop.setEnabled(true);
            btnRun.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(CMMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRunActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        ExePS.destroy();
        btnStop.setEnabled(false);
        btnRun.setEnabled(true);
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnASMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnASMActionPerformed

        if (ASMPanel == null) {
            ASMPanel = new JFrame(ASMSource.getName());
            ASMPanel.setLayout(new BorderLayout());

            txtASM = new RSyntaxTextArea(20, 60);
            txtASM.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
            txtASM.setCodeFoldingEnabled(true);
            txtASM.setAntiAliasingEnabled(true);
            RTextScrollPane sp = new RTextScrollPane(txtASM);
            sp.setFoldIndicatorEnabled(true);

            txtASM.setEditable(false);
            txtASM.setFont(new Font("Arial", Font.BOLD, 14));

            ASMPanel.add(sp);
            ASMPanel.setSize(700, 600);
            ASMPanel.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        }

        try {

            Scanner input = new Scanner(ASMSource);
            StringBuilder asmCode = new StringBuilder();
            while (input.hasNext()) {
                asmCode.append(input.nextLine() + "\n");
            }
            ASMPanel.setVisible(true);
            txtASM.setText(asmCode.toString());
            input.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnASMActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CMMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CMMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CMMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CMMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CMMGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnASM;
    private javax.swing.JButton btnCompile;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel editorPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea txtStatus;
    // End of variables declaration//GEN-END:variables
}
