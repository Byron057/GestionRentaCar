package assets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

public class ComboBoxRound extends JComboBox<Object> {

    private String opciones = "";
    private String placeholder = "Seleccione una opción";
    private Color placeholderColor = new Color(153, 153, 153);
    private Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);
    private int borderSize = 0;
    private Color borderColor = new Color(200, 200, 200);
    private int popupBorderSize = 0;
    private Color popupBorderColor = new Color(200, 200, 200);
    private boolean editable = false;

    public String getOpciones() { 
        return opciones; 
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
        this.removeAllItems();
        if (opciones != null && !opciones.trim().isEmpty()) {
            String[] items = opciones.split("[\n,]");
            for (String item : items) {
                if (!item.trim().isEmpty()) {
                    this.addItem(item.trim());
                }
            }
            if (this.getItemCount() > 0 && this.getSelectedIndex() != -1) {
                return;
            }
            if (this.getItemCount() > 0) {
                this.setSelectedIndex(-1); 
            }
        }
    }

    public void limpiarSeleccion() {
        this.setSelectedIndex(-1);
    }

    public String getPlaceholder() { 
        return placeholder; 
    }

    public void setPlaceholder(String placeholder) { 
        this.placeholder = placeholder; 
        setRenderer(new CustomRenderer()); 
        repaint(); 
    }

    public Color getPlaceholderColor() { 
        return placeholderColor; 
    }

    public void setPlaceholderColor(Color placeholderColor) { 
        this.placeholderColor = placeholderColor; 
        setRenderer(new CustomRenderer()); 
        repaint(); 
    }

    public Font getPlaceholderFont() { 
        return placeholderFont; 
    }

    public void setPlaceholderFont(Font placeholderFont) { 
        this.placeholderFont = placeholderFont; 
        setRenderer(new CustomRenderer()); 
        repaint(); 
    }

    public int getBorderSize() { 
        return borderSize; 
    }

    public void setBorderSize(int borderSize) { 
        this.borderSize = borderSize; 
        repaint(); 
    }

    public Color getBorderColor() { 
        return borderColor; 
    }

    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor; 
        repaint(); 
    }

    public int getPopupBorderSize() { 
        return popupBorderSize; 
    }

    public void setPopupBorderSize(int popupBorderSize) { 
        this.popupBorderSize = popupBorderSize; 
        repaint(); 
    }

    public Color getPopupBorderColor() { 
        return popupBorderColor; 
    }

    public void setPopupBorderColor(Color popupBorderColor) { 
        this.popupBorderColor = popupBorderColor; 
        repaint(); 
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        super.setEditable(editable);
    }

    public ComboBoxRound() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(new Color(60, 60, 60));
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setUI(new CustomComboBoxUI());
        setRenderer(new CustomRenderer());
        setEditable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        if (borderSize > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderSize));
            g2.drawRect(0, 0, getWidth() - borderSize, getHeight() - borderSize);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    private class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    if (!editable) {
                        return;
                    }
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(130, 130, 130)); 
                    g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    int size = 6;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - (size / 2)) / 2;
                    g2.drawLine(x, y, x + size / 2, y + size / 2);
                    g2.drawLine(x + size / 2, y + size / 2, x + size, y);
                    g2.dispose();
                }
            };
            button.setPreferredSize(new Dimension(30, 30));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        }

        @Override
        public void setPopupVisible(JComboBox<?> c, boolean v) {
            if (editable) {
                super.setPopupVisible(c, v);
            }
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {}

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    JScrollPane scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    scroller.setBorder(BorderFactory.createEmptyBorder());
                    scroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = new Color(200, 200, 200);
                            this.trackColor = Color.WHITE;
                        }
                        @Override
                        protected JButton createDecreaseButton(int orientation) { return crearBotonVacio(); }
                        @Override
                        protected JButton createIncreaseButton(int orientation) { return crearBotonVacio(); }
                        private JButton crearBotonVacio() {
                            JButton b = new JButton(); 
                            b.setPreferredSize(new java.awt.Dimension(0,0)); 
                            return b;
                        }
                    });
                    return scroller;
                }
            };
            if (popupBorderSize > 0) {
                popup.setBorder(BorderFactory.createLineBorder(popupBorderColor, popupBorderSize));
            } else {
                popup.setBorder(BorderFactory.createEmptyBorder());
            }
            return popup;
        }
    }

    private class CustomRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, false);
            String textoItem = "";
            if (value != null) {
                String valStr = value.toString();
                if (valStr != null) {
                    textoItem = valStr.trim();
                }
            }
            if (index == -1 && textoItem.isEmpty()) {
                label.setText(placeholder);
                label.setForeground(placeholderColor);
                label.setFont(placeholderFont);
                label.setBackground(Color.WHITE);
            } else {
                label.setFont(ComboBoxRound.this.getFont());
                if (isSelected && index >= 0) {
                    label.setBackground(new Color(245, 245, 245)); 
                    label.setForeground(Color.BLACK);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(ComboBoxRound.this.getForeground()); 
                }
            }
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight()); 

            String text = getText();
            boolean tienePunto = false;

            if (text != null && !text.equals(placeholder)) { 
                if (text.trim().equalsIgnoreCase("Activo") || text.trim().equalsIgnoreCase("Disponible")) {
                    g2.setColor(new Color(40, 167, 69)); 
                    g2.fillOval(4, (getHeight() - 8) / 2, 8, 8); 
                    tienePunto = true;
                } else if (text.trim().equalsIgnoreCase("Inactivo")) {
                    g2.setColor(new Color(220, 53, 69)); 
                    g2.fillOval(4, (getHeight() - 8) / 2, 8, 8); 
                    tienePunto = true;
                } else if (text.trim().equalsIgnoreCase("Alquilado")) {
                    g2.setColor(new Color(150, 150, 150)); 
                    g2.fillOval(4, (getHeight() - 8) / 2, 8, 8); 
                    tienePunto = true;
                } else if (text.trim().equalsIgnoreCase("Finalizado")) {
                    g2.setColor(new Color(220, 53, 69)); 
                    g2.fillOval(4, (getHeight() - 8) / 2, 8, 8); 
                    tienePunto = true;
                }
            }
            g2.dispose();

            if (tienePunto) {
                setBorder(new EmptyBorder(0, 18, 0, 5)); 
            } else {
                setBorder(new EmptyBorder(0, 2, 0, 5)); 
            }

            setOpaque(false);
            super.paintComponent(g);
            setOpaque(true);
        }
    }
}