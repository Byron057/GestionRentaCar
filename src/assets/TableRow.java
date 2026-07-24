package assets;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class TableRow extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    private int radius = 20;
    private int borderSize = 1;

    private Color borderColor = new Color(220, 220, 220);
    private Color headerColor = new Color(253, 248, 243); // Tono crema/cálido
    private Color rowColor = Color.WHITE;
    
    private int roundTopLeft = 20;
    private int roundTopRight = 20;
    private int roundBottomLeft = 20;
    private int roundBottomRight = 20;


    // ==========================
    // CONSTRUCTOR PARA NETBEANS
    // ==========================
    public TableRow() {
        setOpaque(false);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de la celda
            }
        };

        tabla = new JTable(modelo) {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                // Si hacemos un clic normal (sin mantener Ctrl o Shift) en una fila que ya está seleccionada
                if (!toggle && !extend && isRowSelected(rowIndex)) {
                    clearSelection(); // La deseleccionamos
                } else {
                    // Comportamiento normal para las demás filas
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        configurarTabla();
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        personalizarScroll(scroll);

        
        add(scroll, BorderLayout.CENTER);
    }

    // ==========================
    // CONSTRUCTOR CON DATOS
    // ==========================
    public TableRow(String[] columnas, Object[][] datos) {
        this();
        modelo.setDataVector(datos, columnas);
    }

    // ==========================
    // CONFIGURACIÓN VISUAL
    // ==========================
    private void configurarTabla() {
      tabla.setRowHeight(45); 

        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Quitar apariencia clásica de cuadrícula
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(Color.WHITE);

        // CONFIGURACIÓN DE SELECCIÓN
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSelectionAllowed(true); 
        tabla.setColumnSelectionAllowed(false);
        tabla.setFocusable(false);

        // Color de selección sutil
        tabla.setSelectionBackground(new Color(245, 245, 245));
        tabla.setSelectionForeground(Color.BLACK);


        // CONFIGURACIÓN DEL HEADER
        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(BorderFactory.createEmptyBorder()); 

        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                c.setBackground(headerColor); 
                c.setForeground(new Color(80, 80, 80)); 
                setHorizontalAlignment(SwingConstants.LEFT);
                
                // Línea divisoria inferior para separar el Header de los datos
                javax.swing.border.Border padding = BorderFactory.createEmptyBorder(0, 20, 0, 0);
                javax.swing.border.Border bottomLine = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)); 
                setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
                
                return c;
            }
        });

        // RENDERIZADOR DE LAS CELDAS NORMALES (AQUÍ VAN LAS LÍNEAS DE LAS FILAS)
        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setHorizontalAlignment(SwingConstants.LEFT);
                
                // CREACIÓN DEL SEPARADOR DE FILAS
                // El padding mantiene el texto separado del borde izquierdo
                javax.swing.border.Border padding = BorderFactory.createEmptyBorder(0, 20, 0, 0);
                
                // MatteBorder dibuja una línea de 1 pixel solo abajo. 
                // NOTA: Usa 'new Color(235, 235, 235)' para un gris moderno. Si lo quieres negro fuerte, cámbialo a 'Color.BLACK'.
                javax.swing.border.Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 235, 235)); 
                
                // Unimos la línea y el padding
                setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
                
                // Respetar el color de selección
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(rowColor);
                }
                
                return c;
            }
        };

        tabla.setDefaultRenderer(Object.class, render);
    }
    
    private void personalizarScroll(JScrollPane scroll) {
  // 1. Mostrar barra solo si los datos superan la altura (AS_NEEDED)
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // 2. ARREGLAR EL CUADRO GRIS DE LA ESQUINA DEL HEADER
        JPanel esquina = new JPanel();
        esquina.setBackground(headerColor); // Usa el color crema del header
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, esquina);

        JScrollBar vertical = scroll.getVerticalScrollBar();
        
        // 3. Grosor total del área de la barra
        vertical.setPreferredSize(new Dimension(8, 0)); 
        vertical.setBorder(null);
        
        // Truco: Forzar el fondo de la barra al color de la tabla para que se fusione
        vertical.setBackground(Color.WHITE); 

        vertical.setUI(new BasicScrollBarUI() {
            
            @Override
            protected void configureScrollBarColors() {
                // Color de la línea de la barra
                this.thumbColor = new Color(200, 200, 200); 
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return crearBotonVacio();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return crearBotonVacio();
            }

            private JButton crearBotonVacio() {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(0, 0));
                boton.setMinimumSize(new Dimension(0, 0));
                boton.setMaximumSize(new Dimension(0, 0));
                boton.setBorder(null);
                return boton;
            }

            // DIBUJAR LA LÍNEA DELGADA (THUMB)
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(thumbColor);
                
                // Línea extra delgada (3 píxeles)
                int ancho = 3; 
                int arco = 10;
                
                int x = thumbBounds.x + ((thumbBounds.width - ancho) / 2);
                int y = thumbBounds.y + 2;
                int alto = thumbBounds.height - 4;
                
                g2.fillRoundRect(x, y, ancho, alto, arco, arco);
                g2.dispose();
            }

            // DEJAR EL CARRIL TOTALMENTE INVISIBLE
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // No dibujamos nada. El color blanco de fondo (linea 21) hará el trabajo.
            }
        });
    }

    // ==========================
    // MÉTODOS DE TABLA
    // ==========================
    public JTable getTabla() {
        return tabla;
    }

    public void agregarFila(Object[] fila) {
        modelo.addRow(fila);
    }

    public void eliminarFila(int fila) {
        modelo.removeRow(fila);
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void actualizarDatos(Object[][] datos) {
        limpiarTabla();
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    public void setColumnas(String[] columnas) {
        modelo.setColumnIdentifiers(columnas);
    }


    // ==========================
    // PROPIEDADES NETBEANS
    // ==========================
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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

    public Color getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor;
        if (tabla != null) {
            tabla.getTableHeader().setBackground(headerColor);
            tabla.getTableHeader().repaint();
        }
        repaint();
    }

    public Color getRowColor() {
        return rowColor;
    }

    public void setRowColor(Color rowColor) {
        this.rowColor = rowColor;
        tabla.setBackground(rowColor);
        repaint();
    }
}