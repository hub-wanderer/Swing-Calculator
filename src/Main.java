import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {
    private static class Btn extends JButton {
        Btn(String value) {
            super(value);
            this.setFocusable(false);
            this.setPreferredSize(new Dimension(65, 65));
            this.setBackground(new Color(0x656565));
            this.setForeground(new Color(0xffffff));
            this.setBorder(new Rounded(15));
            this.setFocusPainted(false);
        }
    }

    public static void main(String[] args) {

        Calculate calculate = new Calculate();

        int result = 0;

        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();

        JLabel textL = new JLabel();
        JLabel outputL = new JLabel();
        JPanel textOutputMerged = new JPanel();

        JPanel btnPanel = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 650));
        frame.setResizable(false);

        mainPanel.setBackground(new Color(0x131417));
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new BorderLayout());

        textL.setFont(new Font("Mono sans", Font.PLAIN, 25));
        textL.setText("Calculator");
        textL.setForeground(Color.lightGray);
        textL.setHorizontalAlignment(JLabel.CENTER);

        outputL.setText("");
        outputL.setHorizontalAlignment(JLabel.CENTER);
        outputL.setFont(new Font(textL.getFont().getFamily(), Font.PLAIN, 45));
        outputL.setForeground(textL.getForeground());
        outputL.setOpaque(false);

        textOutputMerged.setLayout(new BorderLayout());
        textOutputMerged.setOpaque(false);
        textOutputMerged.add(textL, BorderLayout.NORTH);
        textOutputMerged.add(outputL, BorderLayout.EAST);

        btnPanel.setOpaque(false);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        ArrayList<String> buttonLabels = new ArrayList<>(Arrays.asList(
                "AC", "+/-", "%", "/",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ",", "="
        ));

        for (String label : buttonLabels) {
            Btn btn = new Btn(label);
            btnPanel.add(btn);
            if (btn.getText().equals("0")) {
                btn.setPreferredSize(new Dimension(155, 65));
            }
            btn.addActionListener(e -> {
                String operatorPattern = "[+\\-/x%=]|AC|";
                Pattern pattern = Pattern.compile(operatorPattern);

                JButton pressedBtn = (JButton) e.getSource();

                if (!pressedBtn.getText().matches(pattern.pattern())) {
                    calculate.updateNum(outputL, (Btn) pressedBtn);
                } else {
                    calculate.setOp((Btn) pressedBtn, outputL);

                    if (pressedBtn.getText().equals("=")) {
                        calculate.toEqual(outputL);
                    } else if (pressedBtn.getText().equals("AC")) {
                        outputL.setText("");
                        calculate.reset();
                    }
                }
            });
        }

        mainPanel.add(textOutputMerged, BorderLayout.NORTH);
        mainPanel.add(btnPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static class Calculate {

        private int prev = 0;
        private int cur = 0;
        private String lastOperator = "";
        private boolean operatorPressed = false;

        void setOp(Btn pressedBtn, JLabel outputL) {
            if (!pressedBtn.getText().equals("=")) {
                lastOperator = pressedBtn.getText();
            }
            operatorPressed = true;
            outputL.setText("");

            String op = lastOperator;
            System.out.println(op);
        }

        void updateNum(JLabel outputL, Btn pressedBtn) {

            String curText = outputL.getText() + pressedBtn.getText();
            outputL.setText(curText);

            if (operatorPressed) {
                cur = (Integer.parseInt(curText));
            } else {
                prev = (Integer.parseInt(curText));
            }
        }

        void toEqual(JLabel outputL) {

            if (!operatorPressed) {
                return;
            }

            switch (lastOperator) {
                case "+" -> prev = prev + cur;
                case "-" -> prev = prev - cur;
                case "/" -> prev = prev / cur;
                case "x" -> prev = prev * cur;
                case "%" -> prev = prev % cur;
            }
            outputL.setText(String.valueOf(prev));
            cur = 0;
            operatorPressed = false;
        }

        void reset() {
            cur = 0;
            prev = 0;
            lastOperator = "";
            operatorPressed = false;
        }
    }
}