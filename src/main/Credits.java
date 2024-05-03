package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class Credits extends JPanel implements ActionListener {

    Timer t = new Timer (20, this);
    String text;
    int textY = GAME_HEIGHT;
    public Credits(){
        createPanel ();
        SetTextCredits ();
        t.start ();
    }

    private void createPanel(){
        JFrame fg = new JFrame ("CREDITS");
        this.setBackground (Color.black);
        fg.setResizable (false);
        fg.setSize (1204, 672);
        //fg.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        fg.setLocationRelativeTo (null);
        fg.add(this);
        fg.setVisible (true);
        setPanelSize ();
    }
    private void SetTextCredits(){
        text = "INCREDIBILE \n"
                + "HAI COMPLETATO IL GIOCO \n\n"
                + "SII FIERO DI TE E GODITI QUESTA VITTORIA MERITATA \n\n\n"
                + "AUTORI\n"
                + "MARTINS DONATO NICOLAS FERRANTE\n"
                + "SABATELLI RICCARDO\n\n"
                + "MUSICHE\n" +
                "NO COPYRIGHT\n\n" +
                "DIREZIONE ARTISTICA\n" +
                "MARTINS DONATO NICOLAS FERRANTE\n" +
                "SABATELLI RICCARDO\n\n" +
                "FUNZIONALITA'\n" +
                "MARTINS DONATO NICOLAS FERRANTE\n" +
                "SABATELLI RICCARDO\n\n" +
                "STORIA\n" +
                "MARTINS DONATO NICOLAS FERRANTE\n" +
                "SABATELLI RICCARDO\n\n" +
                "MENZIONI ONOREVOLI\n" +
                "KEKKOOO\n" +
                "DALMATO DANIELE\n" +
                "CAPUTO MARCO\n" +
                "LUISI LUCA\n\n\n\n\n\n\n\n\n" +
                "GRAZIE PER AVER GIOCATO A\n\n" +
                "GIOCO_BELLISSIMO";
    }
    public void paintComponent(Graphics g){
        super.paintComponent (g);

        Graphics2D gs = (Graphics2D)g;

        gs.setFont (new Font ("Times New Roman", Font.PLAIN, 28));
        gs.setColor (Color.white);
        gs.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int y = textY;
        for(String line : text.split ("\n")){
            int strL = (int)gs.getFontMetrics ().getStringBounds (line, gs).getWidth ();
            int x = getWidth ()/2 - strL / 2;
            g.drawString (line, x, y += 28);
        }


    }
    private void setPanelSize() {
        Dimension size = new Dimension (GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize (size);
        setPreferredSize (size);
        setMaximumSize (size);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        textY--;
        if(textY < -792){
            t.stop ();
        }
        repaint ();
    }
}
