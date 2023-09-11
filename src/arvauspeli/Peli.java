package arvauspeli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Peli implements ActionListener {

    private final JFrame frame;
    private final JPanel panel;
    private JLabel label;
    private JTextField input;
    private JButton arvaa;
    private JButton reset;

    private final Integer oikeaVastaus;

    //alustaa pelin ja käyttöliittymän
    public Peli() {
        oikeaVastaus = new Random().nextInt(1000) + 1;

        frame = new JFrame("Arvaa numero -peli");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lisaaKomponentit();

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    //lisää tarvittavat komponentit JPaneliin
    private void lisaaKomponentit() {
        label = new JLabel("Olen valinnut numeron väliltä 1-1000. Voitko arvata sen?");
        input = new JTextField();
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setSize(50, 20);
        input.setFont(new Font("Open Sans", Font.BOLD, 20));

        //sallii syöttökenttään vain numerot
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        reset = new JButton("Reset");
        arvaa = new JButton("Arvaa");

        input.addActionListener(this);
        arvaa.addActionListener(this);
        reset.addActionListener(this);
        reset.setActionCommand("reset");

        panel.add(label);
        panel.add(input);
        panel.add(arvaa);
        panel.add(reset);
    }

    //määrittelee mitä tapahtuu kun arvaus on annettu
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("reset")) {
            reset();
            return;
        }
        Integer arvaus = Integer.parseInt(input.getText().trim());
        kasitteleArvaus(arvaus);
    }

    private void kasitteleArvaus(int arvaus) {
        //kertoo onko arvaus liian pieni, liian suuri vai oikein
        if (arvaus == oikeaVastaus) {
            input.setEditable(false);
            panel.setBackground(Color.GREEN);
            label.setText("Oikein!");
            return;
        } else if (arvaus > oikeaVastaus) {
            label.setText("Liian suuri");
        } else {
            label.setText("Liian pieni");
        }

    }

    //resetoi pelin takaisin alkutilaan
    private void reset() {
        label.setText("Olen valinnut numeron väliltä 1-1000. Voitko arvata sen?");
        panel.setBackground(Color.LIGHT_GRAY);
        input.setText("");
        input.setEditable(true);
    }
}
