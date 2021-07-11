package ml.pkom.generatorpapermcmod;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import ml.pkom.generatorpapermcmod.classes.MCVersion;
import ml.pkom.generatorpapermcmod.classes.PaperVersion;

public class MainFrame extends JFrame {
    public static MainFrame instance;

    private JPanel contentPane = new JPanel();
    private JPanel line1stPanel = new JPanel();
    private JPanel line2ndPanel = new JPanel();
    private JPanel line3rdPanel = new JPanel();
    private JPanel line4thPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JComboBox<MCVersion> selectVersionBox = new JComboBox<MCVersion>();
    private JComboBox<PaperVersion> selectBuildBox = new JComboBox<PaperVersion>();
    private JButton downloadBtn = new JButton();

    public MainFrame() {
        instance = this;
        // setTitle("GeneratorPaperMCPlugin");
        setTitle("PaperMC Downloader v0.2");
        setBounds(getX(), getY(), 340, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectVersionBoxInit();
        selectBuildBoxGenerate(selectVersionBox.getItemAt(selectVersionBox.getSelectedIndex()));
        downloadBtnChange();
        downloadBtn.addActionListener(new DownloadBtnListener());
        selectBuildBox.addItemListener(new BuildBoxListener());
        contentPaneInit();
        setContentPane(contentPane);
        menuBarInit();
        setJMenuBar(menuBar);
        setDefaultUILAF();
        setVisible(true);
    }

    private void setDefaultUILAF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void menuBarInit() {
        JMenu helpMenu = new JMenu(Language.get("menu.help"));
        JMenuItem aboutItem = new JMenuItem(Language.get("menu.about.tool"));
        JMenuItem aboutPaperMCItem = new JMenuItem(Language.get("menu.about.papermc"));
        helpMenu.add(aboutItem);
        helpMenu.add(aboutPaperMCItem);
        menuBar.add(helpMenu);

        JMenu settingMenu = new JMenu(Language.get("menu.settings"));
        JMenu langItem = new JMenu(Language.get("menu.settings.lang"));
        JMenuItem langJP = new JMenuItem(Language.get("menu.settings.lang.ja"));
        JMenuItem langEN = new JMenuItem(Language.get("menu.settings.lang.en"));
        langItem.add(langEN);
        langItem.add(langJP);
        settingMenu.add(langItem);
        menuBar.add(settingMenu);
        aboutItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.instance,
                        "<html>Version: 0.2<br />Author: Pitan<br /><br />" + Language.get("menu.about.tool.expl") + "</html>",
                        "About the PaperMC Downloader", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        aboutPaperMCItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.instance,
                        "<html>URL: <a href=\"https://papermc.io/\">https://papermc.io/</a></html>",
                        "About the PaperMC", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        langEN.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Language.changeLanguage("en_us");
            }
            
        });
        langJP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Language.changeLanguage("ja_jp");
            }

        });
    }

    private void contentPaneInit() {
        contentPane.setLayout(new GridLayout(4,1));
        selectVersionBox.setPreferredSize(new Dimension(100, 20));
        selectBuildBox.setPreferredSize(new Dimension(100, 20));
        line1stPanel.add(new JLabel("Minecraft Version:"));
        line1stPanel.add(selectVersionBox);
        line2ndPanel.add(new JLabel("PaperMC Version:"));
        line2ndPanel.add(selectBuildBox);
        downloadBtn.setPreferredSize(new Dimension(200, 30));
        JLabel downloadLabel = new JLabel(Language.get("label.download"));
        downloadLabel.setFont(new Font(downloadLabel.getFont().getFamily(), Font.PLAIN, 20));
        line3rdPanel.add(downloadLabel);
        line4thPanel.add(downloadBtn);
        contentPane.add(line1stPanel);
        contentPane.add(line2ndPanel);
        contentPane.add(line3rdPanel);
        contentPane.add(line4thPanel);
    }

    private void downloadBtnChange() {
        try {
            PaperVersion info = selectBuildBox.getItemAt(selectBuildBox.getSelectedIndex());
            String buttonText = Language.get("button.download");
            buttonText = buttonText.replace("$file$", "Paper-" + info.mcVersionText + "-" + info.modVersionText + ".jar");
            downloadBtn.setText(buttonText);
        } catch (NullPointerException ex) {
            return;
        }
    }

    private void selectVersionBoxInit() {
        List<String> mcversionList = Main.getMCVersionsList();
        for (String version : mcversionList) {
            selectVersionBox.addItem(new MCVersion(version));
        }
        selectVersionBox.addItemListener(new VersionBoxListener());
    }
    
    private void selectBuildBoxGenerate(MCVersion mcVersion) {
        List<String> buildList = Main.getPaperMCBuildsList(mcVersion);
        for (String version : buildList) {
            selectBuildBox.addItem(new PaperVersion(mcVersion.versionText, version));
        }
        downloadBtnChange();
    }

    private class VersionBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            selectBuildBox.removeAllItems();
            Thread thread = new Thread(() -> {
                MCVersion mcVersion = selectVersionBox.getItemAt(selectVersionBox.getSelectedIndex());
                selectBuildBoxGenerate(mcVersion);
            });
            thread.start();
        }
    }

    private class BuildBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            downloadBtnChange();
        }
    }

    private class DownloadBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.downloadPaperMC(selectBuildBox.getItemAt(selectBuildBox.getSelectedIndex()));
        }
    }
}
