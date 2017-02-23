package jnotepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class MainClass extends JFrame {

    int R = 0, C = 0;
    int tabsName = 1;
    int tabsLimit = 15;
    int currenttab = 0;
    int tabsopen = 0;
    int fileToOpen;
    int fileToSave;
    JFileChooser fileOpen;
    JFileChooser fileSave;
    String Clipboard = "";
    boolean istool = true;
    boolean modified[] = new boolean[tabsLimit];
    boolean iswrap[] = new boolean[tabsLimit];
    boolean isLine[] = new boolean[tabsLimit];
    boolean isStatus[] = new boolean[tabsLimit];
    boolean searchflag[] = new boolean[tabsLimit];
    int searchindex;
    String searchtext;
    ImageIcon icon = new ImageIcon();
    ImageIcon eicon = new ImageIcon();
    final JToolBar toolbar = new JToolBar("Tool Bar");
    JMenuBar menuBar = new JMenuBar();
    String[] DefaultSavePath = new String[tabsLimit];
    final JLabel[] Lstatus = new JLabel[tabsLimit];
    final JLabel[] Rstatus = new JLabel[tabsLimit];
    final JLabel aboutJNP = new JLabel("Welcome to JNotePad. Click Anywhere to Add new Tab!", icon, 0);
    final JSplitPane[] statuspanes = new JSplitPane[tabsLimit];
    final JTextArea[] textAreas = new JTextArea[tabsLimit];
    final JTextArea[] lns = new JTextArea[tabsLimit];
    final JScrollPane[] editorpanes = new JScrollPane[tabsLimit];
    final JScrollPane[] lineNopanes = new JScrollPane[tabsLimit];
    final JSplitPane[] splitpanes = new JSplitPane[tabsLimit];
    final JSplitPane[] EditorWithStatuspane = new JSplitPane[tabsLimit];
    JTabbedPane tabbedPane = new JTabbedPane();
    Color c;
    final JSplitPane HomeScreen = new JSplitPane(JSplitPane.VERTICAL_SPLIT, toolbar, tabbedPane);
    JMenuItem closetab = new JMenuItem("Close Tab", new ImageIcon(ImageIO.read(new File("Images/close-tab.png"))));
    JMenuItem renametab = new JMenuItem("Rename Tab", new ImageIcon(ImageIO.read(new File("Images/write.png"))));

    final void addnewtab() throws IOException {
        modified[tabsopen] = false;
        DefaultSavePath[tabsopen] = "";
        iswrap[tabsopen] = false;
        isLine[tabsopen] = true;
        isStatus[tabsopen] = true;
        searchflag[tabsopen] = true;
        Lstatus[tabsopen] = new JLabel("New Text Document...");
        Rstatus[tabsopen] = new JLabel(" 1 lines   | 0 chars   |              |  Ln: 1 |  Col: 1 |");
        statuspanes[tabsopen] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, Lstatus[tabsopen], Rstatus[tabsopen]);
        statuspanes[tabsopen].setResizeWeight(0.5);
        statuspanes[tabsopen].setDividerSize(1);
        statuspanes[tabsopen].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textAreas[tabsopen] = new JTextArea("");
        lns[tabsopen] = new JTextArea("1");
        lns[tabsopen].setEnabled(false);
        lns[tabsopen].setFont(new Font(Font.DIALOG, Font.PLAIN, 13));
        lns[tabsopen].setDisabledTextColor(Color.BLACK);
        lns[tabsopen].setBackground(Color.getColor("D9D9D9"));
        lns[tabsopen].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        lns[tabsopen].setText("1");
        editorpanes[tabsopen] = new JScrollPane(textAreas[tabsopen]);
        lineNopanes[tabsopen] = new JScrollPane(lns[tabsopen]);
        lineNopanes[tabsopen].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lineNopanes[tabsopen].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        lineNopanes[tabsopen].getVerticalScrollBar().setModel(editorpanes[tabsopen].getVerticalScrollBar().getModel());
        lineNopanes[tabsopen].setBorder(null);
        splitpanes[tabsopen] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lineNopanes[tabsopen], editorpanes[tabsopen]);
        splitpanes[tabsopen].setDividerSize(1);
        splitpanes[tabsopen].setEnabled(false);
        splitpanes[tabsopen].setResizeWeight(0);
        splitpanes[tabsopen].setDividerLocation(25);
        splitpanes[tabsopen].setBorder(null);
        EditorWithStatuspane[tabsopen] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitpanes[tabsopen], statuspanes[tabsopen]);
        EditorWithStatuspane[tabsopen].setResizeWeight(1);
        EditorWithStatuspane[tabsopen].setDividerSize(1);
        EditorWithStatuspane[tabsopen].setEnabled(false);
        EditorWithStatuspane[tabsopen].setBorder(null);
        tabbedPane.removeTabAt(tabsopen);
        tabbedPane.insertTab("Workdesk #" + tabsName, null, EditorWithStatuspane[tabsopen], "Workdesk #" + tabsName++, tabsopen);
        tabbedPane.insertTab("", new ImageIcon(ImageIO.read(new File("Images/add-tab.png"))), aboutJNP, "Add New File", tabsopen + 1);
        tabbedPane.setSelectedIndex(tabsopen);
        tabsopen = tabbedPane.getTabCount() - 1;

    }

    final void removetab() {
        if (!textAreas[currenttab].getText().equals("")) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Notes?", "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                savefileoption();
            }
        }
        if (tabsopen != 0 && !tabbedPane.getTitleAt(currenttab).equals("")) {
            if (tabsopen > 1 && currenttab == tabsopen - 1) {
                tabbedPane.setSelectedIndex(tabsopen - 2);
            }
            tabbedPane.remove(currenttab);
            tabsopen = tabbedPane.getTabCount() - 1;
            for (int i = currenttab; i <= tabsopen; i++) {
                modified[i] = modified[i + 1];
                DefaultSavePath[i] = DefaultSavePath[i + 1];
                iswrap[i] = iswrap[i + 1];
                isLine[i] = isLine[i + 1];
                isStatus[i] = isStatus[i + 1];
                searchflag[i] = searchflag[i + 1];
                Lstatus[i] = Lstatus[i + 1];
                Rstatus[i] = Rstatus[i + 1];
                lns[i] = lns[i + 1];
                textAreas[i] = textAreas[i + 1];
                editorpanes[i] = editorpanes[i + 1];
                lineNopanes[i] = lineNopanes[i + 1];
                statuspanes[i] = statuspanes[i + 1];
                splitpanes[i] = splitpanes[i + 1];
                EditorWithStatuspane[i] = EditorWithStatuspane[i + 1];
            }
        }
        tabsopen = tabbedPane.getTabCount() - 1;
    }

    final void renametab() {
        String tabname = JOptionPane.showInputDialog("Enter Workspace Title: ");
        if (tabname != null) {
            tabbedPane.setTitleAt(currenttab, tabname);
        }
    }

    void getcurrentposition() {
        try {
            R = textAreas[currenttab].getLineOfOffset(textAreas[currenttab].getCaretPosition()) + 1;
            C = textAreas[currenttab].getCaretPosition() - textAreas[currenttab].getLineStartOffset(textAreas[currenttab].getLineOfOffset(textAreas[currenttab].getCaretPosition())) + 1;
        } catch (BadLocationException ex) {
        }
    }

    MainClass() throws IOException {

        icon.setImage(ImageIO.read(new File("Images/Notepad1.png")));
        eicon.setImage(ImageIO.read(new File("Images/ico_edit.gif")));
        toolbar.setBorderPainted(false);
        toolbar.setFloatable(false);

        tabbedPane.insertTab("", new ImageIcon(ImageIO.read(new File("Images/add-tab.png"))), null, "Add New File", tabsopen);
        addnewtab();
        modified[tabsopen] = false;
        c = textAreas[0].getSelectionColor();
        getContentPane().add(HomeScreen);
        HomeScreen.setDividerSize(1);
        HomeScreen.setEnabled(false);
        HomeScreen.setResizeWeight(0);
        HomeScreen.setTopComponent(toolbar);

        UIManager.put("Menu.font", new Font(Font.DIALOG, Font.PLAIN, 12));
        UIManager.put("MenuBar.font", new Font(Font.DIALOG, Font.PLAIN, 12));
        UIManager.put("MenuItem.font", new Font(Font.DIALOG, Font.LAYOUT_LEFT_TO_RIGHT, 12));
        setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        JMenu fonts = new JMenu("Format");
        JMenu edit = new JMenu("Edit");
        JMenu view = new JMenu("View");
        JMenu help = new JMenu("Help");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(fonts);
        menuBar.add(view);
        menuBar.add(help);
        JMenuItem newOption = new JMenuItem("New                      Ctrl+N", new ImageIcon(ImageIO.read(new File("Images/file.png"))));
        JMenuItem newTabOption = new JMenuItem("New Tab", new ImageIcon(ImageIO.read(new File("Images/add-tab.png"))));
        JMenuItem open = new JMenuItem("Open                    Ctrl+O", new ImageIcon(ImageIO.read(new File("Images/open.png"))));
        JMenuItem save = new JMenuItem("Save                     Ctrl+S", new ImageIcon(ImageIO.read(new File("Images/save.png"))));
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem pagesetup = new JMenuItem("Page Setup                     ");
        JMenuItem pageprint = new JMenuItem("Print                      Ctrl+P", new ImageIcon(ImageIO.read(new File("Images/print.png"))));
        JMenuItem closeTabOption = new JMenuItem("Close current tab", new ImageIcon(ImageIO.read(new File("Images/close-tab.png"))));
        JMenuItem close = new JMenuItem("Exit                        Alt+F4");
        JMenuItem wordwrap = new JMenuItem("Toggle Word Wrap             ");
        JMenuItem fontformat = new JMenuItem("Font...                      ");
        JMenuItem cut = new JMenuItem("Cut                       Ctrl+X", new ImageIcon(ImageIO.read(new File("Images/cut.png"))));
        JMenuItem copy = new JMenuItem("Copy                    Ctrl+C", new ImageIcon(ImageIO.read(new File("Images/copy.png"))));
        JMenuItem paste = new JMenuItem("Paste                  Ctrl+V", new ImageIcon(ImageIO.read(new File("Images/paste.png"))));
        JMenuItem delete = new JMenuItem("Delete                  Del", new ImageIcon(ImageIO.read(new File("Images/delete.gif"))));
        JMenuItem renametabOption = new JMenuItem("Rename current tab", new ImageIcon(ImageIO.read(new File("Images/write.png"))));
        JMenuItem findstr = new JMenuItem("Find                     Ctr+F", new ImageIcon(ImageIO.read(new File("Images/search.png"))));
        JMenuItem selectall = new JMenuItem("Select All            Ctrl+A");
        JMenuItem gotoline = new JMenuItem("Go To...               Ctrl+G");
        JMenuItem datetime = new JMenuItem("Date And Time  Ctrl+D");
        final JMenuItem displaylineNo = new JMenuItem("Show Line Numbers          ", new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
        final JMenuItem displayStatus = new JMenuItem("Show Status Bar", new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
        final JMenuItem displaytoolbar = new JMenuItem("Show Tool Bar", new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
        JMenuItem helpcontents = new JMenuItem("Help Contents                ", new ImageIcon(ImageIO.read(new File("Images/help.png"))));
        JMenuItem about = new JMenuItem("About", new ImageIcon(ImageIO.read(new File("Images/about.png"))));
        file.add(newOption);
        file.add(newTabOption);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(pagesetup);
        file.add(pageprint);
        file.add(closeTabOption);
        file.add(close);
        fonts.add(wordwrap);
        fonts.add(fontformat);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.add(renametabOption);
        edit.add(findstr);
        edit.add(selectall);
        edit.add(gotoline);
        edit.add(datetime);
        view.add(displaylineNo);
        view.add(displayStatus);
        view.add(displaytoolbar);
        help.add(helpcontents);
        help.add(about);


        JPopupMenu popupmenu = new JPopupMenu();
        popupmenu.add(renametab);
        popupmenu.add(closetab);
        JButton newfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/file.png"))));
        newfile.setBorder(null);
        newfile.setToolTipText("New File");
        toolbar.add(newfile);
        toolbar.add(new JLabel("  "));

        JButton savefile = new JButton(new ImageIcon(ImageIO.read(new File("Images/save.png"))));
        savefile.setBorder(null);
        savefile.setToolTipText("Save File");
        toolbar.add(savefile);
        toolbar.add(new JLabel("  "));

        JButton openfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/open.png"))));
        openfile.setBorder(null);
        openfile.setToolTipText("Open a File");
        toolbar.add(openfile);
        toolbar.add(new JLabel("  "));

        JButton printfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/print.png"))));
        printfile.setBorder(null);
        printfile.setToolTipText("Print File");
        toolbar.add(printfile);
        toolbar.add(new JLabel("  "));

        JButton cutfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/cut.png"))));
        cutfile.setBorder(null);
        cutfile.setToolTipText("Cut Selected Text");
        toolbar.add(cutfile);
        toolbar.add(new JLabel("  "));

        JButton copyfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/copy.png"))));
        copyfile.setBorder(null);
        copyfile.setToolTipText("Copy Selected Text");
        toolbar.add(copyfile);
        toolbar.add(new JLabel("  "));

        JButton pastefile = new JButton(new ImageIcon(ImageIO.read(new File("Images/paste.png"))));
        pastefile.setBorder(null);
        pastefile.setToolTipText("Paste Selected Text");
        toolbar.add(pastefile);
        toolbar.add(new JLabel("      "));

        JLabel searchfile = new JLabel(new ImageIcon(ImageIO.read(new File("Images/search.png"))));
        searchfile.setBorder(null);
        searchfile.setToolTipText("Search for a String");
        toolbar.add(searchfile);
        toolbar.add(new JLabel("  "));

        final JTextField stext = new JTextField(15);
        stext.setMaximumSize(stext.getPreferredSize());
        stext.setToolTipText("Search for a String");
        toolbar.add(stext);
        toolbar.add(new JLabel("  "));

        JButton nextfile = new JButton(new ImageIcon(ImageIO.read(new File("Images/right-arrow.png"))));
        nextfile.setBorder(null);
        nextfile.setToolTipText("Search for a Next occurance");
        toolbar.add(nextfile, BorderLayout.EAST);
        toolbar.add(new JLabel("  "));

        nextfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Clipboard = textAreas[currenttab].getText();
                textAreas[currenttab].grabFocus();
                if (searchflag[currenttab] == true) {
                    searchtext = stext.getText();
                    searchindex = Clipboard.toLowerCase().indexOf(searchtext.toLowerCase());
                    searchindex = Clipboard.toLowerCase().indexOf(searchtext.toLowerCase());
                }
                searchflag[currenttab] = false;
                if (searchindex != -1) {
                    textAreas[currenttab].setSelectionStart(searchindex);
                    textAreas[currenttab].setSelectionEnd(searchindex + (searchtext.length()));
                    textAreas[currenttab].setSelectionColor(Color.yellow);
                }
                searchtext = stext.getText();
                searchindex = Clipboard.toLowerCase().indexOf(searchtext.toLowerCase(), (searchindex + searchtext.length()));
                if (textAreas[currenttab].getSelectedText() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Cannot Find \"" + stext.getText() + "\"");
                }
            }
        });

        stext.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                searchflag[currenttab] = true;
                textAreas[currenttab].setSelectionColor(c);
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        textAreas[currenttab].addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                getcurrentposition();
                Rstatus[currenttab].setText(" " + textAreas[currenttab].getLineCount() + " lines   | " + Clipboard.length() + " chars   |              |  Ln: " + R + " |  Col: " + C + " |");
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        final KeyListener textListener1 = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                currenttab = tabbedPane.getSelectedIndex();
                Clipboard = textAreas[currenttab].getText();
                int a = textAreas[currenttab].getLineCount();
                lns[currenttab].setText("1");
                for (int i = 2; i < a + 1; i++) {
                    lns[currenttab].append("\n" + i);
                }
                modified[currenttab] = true;
                Lstatus[currenttab].setIcon(eicon);
                Lstatus[currenttab].setText("");
                getcurrentposition();
                Rstatus[currenttab].setText(" " + a + " lines   | " + Clipboard.length() + " chars   |              |  Ln: " + R + " |  Col: " + C + " |");
                textAreas[currenttab].setSelectionColor(c);
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                currenttab = tabbedPane.getSelectedIndex();
                if (evt.isActionKey()) {
                    getcurrentposition();
                    if (evt.getKeyCode() == KeyEvent.VK_UP && R > 1) {
                        R--;
                    } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && R < textAreas[currenttab].getLineCount()) {
                        R++;
                    } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
                        try {
                            if (textAreas[currenttab].getText((textAreas[currenttab].getCaretPosition() - 1), 1).equals("\n")) {
                                R--;
                                C = textAreas[currenttab].getLineEndOffset(R - 1);
                            } else {
                                C--;
                            }
                        } catch (Exception ex) {
                        }
                    } else {
                        try {
                            if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                                if (textAreas[currenttab].getText(textAreas[currenttab].getCaretPosition(), 1).equals("\n")) {
                                    if (R < textAreas[currenttab].getLineCount()) {
                                        R++;
                                        C = 1;
                                    }
                                } else {
                                    C++;
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                    Rstatus[currenttab].setText(" " + textAreas[currenttab].getLineCount() + " lines   | " + Clipboard.length() + " chars   |              |  Ln: " + R + " |  Col: " + C + " |");
                }
                if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N) {
                    newfileoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_O) {
                    openfileoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_S) {
                    savefileoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_D) {
                    printdateoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_P) {
                    printfileoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G) {
                    gotolineoption();

                } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_F) {
                    findtextoption();
                }
            }
        };
        textAreas[0].addKeyListener(textListener1);
        tabbedPane.setComponentPopupMenu(popupmenu);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                currenttab = tabbedPane.getSelectedIndex();
            }
        });
        tabbedPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currenttab = tabbedPane.getSelectedIndex();
                System.out.println("index:" + currenttab);
                if (currenttab == tabsopen) {
                    try {
                        addnewtab();
                        textAreas[tabsopen - 1].addKeyListener(textListener1);

                    } catch (IOException ex) {
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        renametab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renametab();
            }
        });

        renametabOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renametab();
            }
        });

        closetab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removetab();
            }
        });

        closeTabOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removetab();
            }
        });

        ActionListener newfilelistener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newfileoption();
            }
        };

        ActionListener openfileliListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openfileoption();
            }
        };

        ActionListener savefilelisListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savefileoption();
            }
        };

        ActionListener closefilelisListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closefileoption();
            }
        };

        findstr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findtextoption();
            }
        });

        displaylineNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currenttab = tabbedPane.getSelectedIndex();
                try {
                    if (isLine[currenttab]) {
                        isLine[currenttab] = false;
                        splitpanes[currenttab].setLeftComponent(null);
                        displaylineNo.setIcon(null);
                    } else {
                        isLine[currenttab] = true;
                        displaylineNo.setIcon(new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
                        splitpanes[currenttab].setLeftComponent(lineNopanes[currenttab]);
                        splitpanes[currenttab].setDividerLocation(30);
                        textAreas[currenttab].setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
                    }
                } catch (Exception f) {
                }
            }
        });
        displayStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currenttab = tabbedPane.getSelectedIndex();
                if (isStatus[currenttab]) {
                    isStatus[currenttab] = false;
                    EditorWithStatuspane[currenttab].setBottomComponent(null);
                    displayStatus.setIcon(null);
                } else {
                    try {
                        isStatus[currenttab] = true;
                        EditorWithStatuspane[currenttab].setBottomComponent(statuspanes[currenttab]);
                        displayStatus.setIcon(new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
                    } catch (IOException ex) {
                    }
                }
            }
        });
        displaytoolbar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (istool) {
                    istool = false;
                    HomeScreen.setTopComponent(null);
                    displaytoolbar.setIcon(null);
                } else {
                    istool = true;
                    HomeScreen.setTopComponent(toolbar);
                    try {
                        displaytoolbar.setIcon(new ImageIcon(ImageIO.read(new File("Images/tick.png"))));
                    } catch (IOException ex) {
                    }
                }
            }
        });

        newTabOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addnewtab();
                    textAreas[tabsopen - 1].addKeyListener(textListener1);
                } catch (IOException ex) {
                }
            }
        });
        pagesetup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pagePainter p = new pagePainter();
                PrinterJob pj = PrinterJob.getPrinterJob();
                PageFormat pf = pj.defaultPage();
                pj.setPrintable(p);
                pj.pageDialog(pf);
            }
        });

        gotoline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gotolineoption();
            }
        });

        pageprint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printfileoption();
            }
        });

        wordwrap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (iswrap[currenttab]) {
                    textAreas[currenttab].setLineWrap(false);
                    displaylineNo.setEnabled(true);
                    iswrap[currenttab] = false;
                    isLine[currenttab] = true;
                    splitpanes[currenttab].setLeftComponent(lineNopanes[currenttab]);
                    splitpanes[currenttab].setDividerLocation(30);
                } else {
                    textAreas[currenttab].setLineWrap(true);
                    displaylineNo.setEnabled(false);
                    iswrap[currenttab] = true;
                    isLine[currenttab] = false;
                    splitpanes[currenttab].setLeftComponent(null);
                }
            }
        });

        fontformat.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFontChooser f = new JFontChooser();
                        int option = f.showDialog(textAreas[currenttab]);
                        if (option == 0) {
                            Font q = f.getSelectedFont();
                            textAreas[currenttab].setFont(q);
                            splitpanes[currenttab].setLeftComponent(null);
                            isLine[currenttab] = false;
                        }
                    }
                });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int start = textAreas[currenttab].getSelectionStart();
                int end = textAreas[currenttab].getSelectionEnd();
                String startText = textAreas[currenttab].getText().substring(0, start);
                String endText = textAreas[currenttab].getText().substring(end, textAreas[currenttab].getText().length());
                textAreas[currenttab].setText(startText + endText);
                Clipboard = textAreas[currenttab].getText();
            }
        });

        newOption.addActionListener(newfilelistener);
        newfile.addActionListener(newfilelistener);
        open.addActionListener(openfileliListener);
        openfile.addActionListener(openfileliListener);
        save.addActionListener(savefilelisListener);
        savefile.addActionListener(savefilelisListener);
        close.addActionListener(closefilelisListener);

        saveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultSavePath[currenttab] = "";
                savefileoption();
            }
        });

        printfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printfileoption();
            }
        });

        cutfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].cut();
            }
        });

        copyfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].copy();
            }
        });

        pastefile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].paste();
            }
        });

        helpcontents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPane, "Make Notes using JNotePad! Version 1.0 \n Simple and Easy to use with File Open, Save, Print and Formatting Features...", "JNotePad Help", JOptionPane.PLAIN_MESSAGE, icon);
            }
        });
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPane, "NotePad Simulation using Java Swing!\nVersion 1.0\n\nDeveloped By\nSubramaniya Sai S", "About JNotePad", JOptionPane.PLAIN_MESSAGE, icon);
            }
        });
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].paste();
            }
        });
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].copy();
            }
        });
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].cut();
            }
        });
        selectall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAreas[currenttab].selectAll();
            }
        });
        datetime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printdateoption();
            }
        });
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if ("".equals(Clipboard)) {
                System.exit(0);
            }
            if (tabsopen > 1) {
                int tabclose = JOptionPane.showConfirmDialog(rootPane, "Do you want to close all tabs without saving?", "JNotePad", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (tabclose == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } else if (tabsopen == 1) {
                int exit = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Notes?", "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    savefileoption();
                    System.exit(0);
                } else if (exit == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
    }

    public void newfileoption() {
        if (!Clipboard.equals("") && modified[currenttab] == true) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Notes?", "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                savefileoption();
            } else if (reply == JOptionPane.NO_OPTION) {
                textAreas[currenttab].setText("");
                Clipboard = "";
                lns[currenttab].setText("1");
                Lstatus[currenttab].setIcon(null);
                Lstatus[currenttab].setText("New Text Document...");
            } else {
                Lstatus[currenttab].setText("");
            }
        } else {
            textAreas[currenttab].setText("");
            Clipboard = "";
            lns[currenttab].setText("1");
            Lstatus[currenttab].setIcon(null);
            Lstatus[currenttab].setText("New Text Document...");
        }
    }

    public void openfileoption() {
        if (!Clipboard.equals("") && modified[currenttab] == true) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the current Notes?", "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                savefileoption();
            } else if (reply == JOptionPane.NO_OPTION) {
                Lstatus[currenttab].setText("Opening File...");
                JFileChooser open = new JFileChooser();
                int option = open.showOpenDialog(this);
                fileToOpen = option;
                fileOpen = open;

                if (fileToOpen == JFileChooser.APPROVE_OPTION) {
                    textAreas[currenttab].setText("");
                    try {
                        tabbedPane.setTitleAt(currenttab, fileOpen.getSelectedFile().getName());
                        Scanner scan = new Scanner(new FileReader(fileOpen.getSelectedFile().getPath()));
                        DefaultSavePath[currenttab] = fileOpen.getSelectedFile().getPath();
                        while (scan.hasNext()) {
                            textAreas[currenttab].append(scan.nextLine());
                            textAreas[currenttab].append("\n");
                        }
                        textAreas[currenttab].append(scan.nextLine());
                        Clipboard = textAreas[currenttab].getText();
                        Lstatus[currenttab].setIcon(null);
                        Lstatus[currenttab].setText("File Opened!");
                        modified[currenttab] = false;
                    } catch (Exception ex) {
                    }
                } else {
                    Lstatus[currenttab].setText("");
                }
                Clipboard = textAreas[currenttab].getText();
                int a = textAreas[currenttab].getLineCount();
                lns[currenttab].setText("1");
                for (int i = 2; i < a + 1; i++) {
                    lns[currenttab].append("\n" + i);
                }
                getcurrentposition();
                Rstatus[currenttab].setText(" " + a + " lines   | " + Clipboard.length() + " chars   |              | " + R + " | " + C + " |");
            }
        } else {

            Lstatus[currenttab].setText("Opening File...");
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            fileToOpen = option;
            fileOpen = open;
            if (fileToOpen == JFileChooser.APPROVE_OPTION) {
                textAreas[currenttab].setText("");
                try {
                    tabbedPane.setTitleAt(currenttab, fileOpen.getSelectedFile().getName());
                    Scanner scan = new Scanner(new FileReader(fileOpen.getSelectedFile().getPath()));
                    DefaultSavePath[currenttab] = fileOpen.getSelectedFile().getPath();
                    while (scan.hasNext()) {
                        textAreas[currenttab].append(scan.nextLine());
                        textAreas[currenttab].append("\n");
                    }
                    textAreas[currenttab].append(scan.nextLine());
                    Clipboard = textAreas[currenttab].getText();
                    Lstatus[currenttab].setText("File Opened!");
                    modified[currenttab] = false;
                    Lstatus[currenttab].setIcon(null);
                } catch (Exception ex) {
                }
            } else {
                Lstatus[currenttab].setText("");
            }
            Clipboard = textAreas[currenttab].getText();
            int a = textAreas[currenttab].getLineCount();
            lns[currenttab].setText("1");
            for (int i = 2; i < a + 1; i++) {
                lns[currenttab].append("\n" + i);
            }
            getcurrentposition();
            Rstatus[currenttab].setText(" " + a + " lines   | " + Clipboard.length() + " chars   |              |  Ln: " + R + " |  Col: " + C + " |");
        }
    }

    public void savefileoption() {
        Lstatus[currenttab].setText("Saving File...");
        if (!DefaultSavePath[currenttab].equals("") && DefaultSavePath[currenttab].contains(tabbedPane.getTitleAt(currenttab))) {
            BufferedWriter out;
            try {
                out = new BufferedWriter(new FileWriter(DefaultSavePath[currenttab]));
                out.write(textAreas[currenttab].getText());
                out.close();
            } catch (IOException ex) {
            }
            Lstatus[currenttab].setIcon(null);
            Lstatus[currenttab].setText("File Saved!");
            modified[currenttab] = false;
            JOptionPane.showMessageDialog(rootPane, "Notes Saved!", "JNotePad - Saved", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            fileToSave = option;
            fileSave = save;
            if (fileToSave == JFileChooser.APPROVE_OPTION) {
                try {
                    tabbedPane.setTitleAt(currenttab, fileSave.getSelectedFile().getName());
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileSave.getSelectedFile().getPath()));
                    out.write(textAreas[currenttab].getText());
                    out.close();
                    Lstatus[currenttab].setIcon(null);
                    Lstatus[currenttab].setText("File Saved!");
                    modified[currenttab] = false;
                    JOptionPane.showMessageDialog(rootPane, "Notes Saved!", "JNotePad - Saved", JOptionPane.INFORMATION_MESSAGE, icon);
                } catch (Exception ex) {
                }
            } else {
                Lstatus[currenttab].setText("");
            }
        }
    }

    public void closefileoption() {
        if (!textAreas[currenttab].getText().equals("") && modified[currenttab] == true) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Notes?", "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                savefileoption();
            }
        }
        System.exit(0);
    }

    public void printdateoption() {
        int pos = textAreas[currenttab].getCaretPosition();
        Date date = new Date();
        textAreas[currenttab].insert(date.toString(), pos);
        Clipboard = textAreas[currenttab].getText();
    }

    public void printfileoption() {
        pagePainter p = new pagePainter();
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        pj.setPrintable(p);
        pj.pageDialog(pf);
        if (pj.printDialog()) {
        }
    }

    public void gotolineoption() {
        try {
            int reply = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Enter Line Number :", "Go To Line..", JOptionPane.PLAIN_MESSAGE));
            if (reply > 0 && reply < textAreas[currenttab].getLineCount() + 1) {
                textAreas[currenttab].select(textAreas[currenttab].getLineStartOffset(reply - 1), textAreas[currenttab].getLineEndOffset(reply - 1) - 1);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Line Number is Beyound file size!");
            }
        } catch (Exception e1) {
        }
    }

    public void findtextoption() {
        String str = JOptionPane.showInputDialog(rootPane, "Enter the string :", "Find text..", JOptionPane.PLAIN_MESSAGE);
        Clipboard = textAreas[currenttab].getText();
        if (str != null && Clipboard.toLowerCase().contains(str.toLowerCase())) {
            int b = Clipboard.toLowerCase().indexOf(str.toLowerCase());
            textAreas[currenttab].grabFocus();
            textAreas[currenttab].select(b, (b + str.length()));
        }
    }
}
