package assets;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TableRow extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    private int borderSize = 0; 

    private Color borderColor = new Color(220, 220, 220);
    private Color headerColor = new Color(253, 248, 243); 
    private Color rowColor = Color.WHITE;
    
    private int round = 0;
    private int roundTopLeft = 20;
    private int roundTopRight = 20;
    private int roundBottomLeft = 20;
    private int roundBottomRight = 20;

    private String nombresColumnas = "";
    
    private int anchoPanel = 400;
    private int altoPanel = 250;

    public TableRow() {
        setOpaque(false);
        setLayout(new BorderLayout());
        
        setPreferredSize(new Dimension(anchoPanel, altoPanel));
        setSize(new Dimension(anchoPanel, altoPanel));

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(modelo) {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (!toggle && !extend && isRowSelected(rowIndex)) {
                    clearSelection(); 
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        configurarTabla();
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setViewportBorder(BorderFactory.createEmptyBorder()); 
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        personalizarScroll(scroll);
        
        add(scroll, BorderLayout.CENTER);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mantenerMinimoFilas();
            }
        });
    }

    public TableRow(String[] columnas, Object[][] datos) {
        this();
        modelo.setDataVector(datos, columnas);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        
        if (borderSize > 0) {
            g2.translate(borderSize / 2.0, borderSize / 2.0);
        }

        Area area = new Area(createRoundTopLeft());
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft()));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight()));
        }
        g2.fill(area);
        
        if (borderSize > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new java.awt.BasicStroke(borderSize));
            g2.draw(area);
        }

        g2.dispose();
    }
    
    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Area area = new Area(createRoundTopLeft());
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft()));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight()));
        }
        
        g2.setClip(area);
        super.paintChildren(g2);
        g2.dispose();
    }

    private Shape createRoundTopLeft() {
        int offset = borderSize > 0 ? borderSize : 0;
        int width = getWidth() - offset;
        int height = getHeight() - offset;
        int roundX = Math.min(width, roundTopLeft);
        int roundY = Math.min(height, roundTopLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundTopRight() {
        int offset = borderSize > 0 ? borderSize : 0;
        int width = getWidth() - offset;
        int height = getHeight() - offset;
        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(height, roundTopRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft() {
        int offset = borderSize > 0 ? borderSize : 0;
        int width = getWidth() - offset;
        int height = getHeight() - offset;
        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight() {
        int offset = borderSize > 0 ? borderSize : 0;
        int width = getWidth() - offset;
        int height = getHeight() - offset;
        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(height, roundBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private void configurarTabla() {
        tabla.setRowHeight(45); 
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setShowGrid(false);
        tabla.setShowVerticalLines(false); 
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(Color.WHITE);
        
        // Juntamos las columnas al máximo
        tabla.getColumnModel().setColumnMargin(0);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSelectionAllowed(true); 
        tabla.setColumnSelectionAllowed(false);
        tabla.setFocusable(false);

        tabla.setSelectionBackground(new Color(245, 245, 245));
        tabla.setSelectionForeground(Color.BLACK);
        
        tabla.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (esFilaReal(index0)) {
                    super.setSelectionInterval(index0, index1);
                }
            }
        });

        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setBorder(BorderFactory.createEmptyBorder()); 
        header.setReorderingAllowed(false);
        
        // ¡LA SOLUCIÓN! Pintamos el fondo principal para ocultar las rendijas
        header.setBackground(headerColor);
        header.setOpaque(true);
        
        header.setDefaultRenderer(new javax.swing.table.TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setOpaque(true);
                label.setBackground(headerColor);
                label.setForeground(new Color(80, 80, 80));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                
                javax.swing.border.Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 0);
                javax.swing.border.Border bottomLine = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)); 
                label.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
                
                return label;
            }
        });

        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                boolean esFilaFalsa = !esFilaReal(row);
                
                if (esFilaFalsa) {
                    value = ""; 
                    isSelected = false; 
                }
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.LEFT);
                
                javax.swing.border.Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 0);
                javax.swing.border.Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 235, 235)); 
                setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
                
                if (isSelected && !esFilaFalsa) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(rowColor);
                }
                
                return c;
            }
        };

        tabla.setDefaultRenderer(Object.class, render);
        mantenerMinimoFilas();
    }
    
    private int cantidadDatosReales = 0; 

    private void mantenerMinimoFilas() {
        if (tabla == null || modelo == null) return;

        int altoDisponible = this.getHeight();
        if (tabla.getTableHeader() != null) {
            altoDisponible -= tabla.getTableHeader().getPreferredSize().height;
        }

        int filasRequeridas = 10;
        if (altoDisponible > 0) {
            filasRequeridas = (int) Math.ceil((double) altoDisponible / tabla.getRowHeight());
        }

        int filasDeseadas = Math.max(cantidadDatosReales, filasRequeridas);

        while (modelo.getRowCount() > filasDeseadas && modelo.getRowCount() > cantidadDatosReales) {
            modelo.removeRow(modelo.getRowCount() - 1);
        }

        while (modelo.getRowCount() < filasDeseadas) {
            modelo.addRow(new Object[modelo.getColumnCount()]);
        }
    }
    
    private boolean esFilaReal(int rowIndex) {
        return rowIndex < cantidadDatosReales;
    }

    private void personalizarScroll(JScrollPane scroll) {
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel esquina = new JPanel();
        esquina.setBackground(headerColor); 
        esquina.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230))); 
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, esquina);

        JScrollBar vertical = scroll.getVerticalScrollBar();
        vertical.setPreferredSize(new Dimension(8, 0)); 
        vertical.setBorder(null);
        vertical.setBackground(Color.WHITE); 

        vertical.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
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

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                
                int ancho = 3; 
                int arco = 10;
                int x = thumbBounds.x + ((thumbBounds.width - ancho) / 2);
                int y = thumbBounds.y + 2;
                int alto = thumbBounds.height - 4;
                
                g2.fillRoundRect(x, y, ancho, alto, arco, arco);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            }
        });
    }
    
    @Override
    public void addMouseListener(java.awt.event.MouseListener l) {
        if (tabla != null) {
            tabla.addMouseListener(l);
        } else {
            super.addMouseListener(l);
        }
    }

    public JTable getTabla() {
        return tabla;
    }

    public void agregarFila(Object[] fila) {
        if (modelo.getColumnCount() < fila.length) {
            modelo.setColumnCount(fila.length);
        }
        
        if (cantidadDatosReales < modelo.getRowCount()) {
            for (int i = 0; i < fila.length; i++) {
                modelo.setValueAt(fila[i], cantidadDatosReales, i);
            }
        } else {
            modelo.addRow(fila);
        }
        cantidadDatosReales++;
    }

    public void eliminarFila(int fila) {
        if (esFilaReal(fila)) {
            modelo.removeRow(fila);
            cantidadDatosReales--;
            mantenerMinimoFilas(); 
        }
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
        cantidadDatosReales = 0;
        mantenerMinimoFilas(); 
    }

    public void actualizarDatos(Object[][] datos) {
        modelo.setRowCount(0);
        cantidadDatosReales = 0;
        for (Object[] fila : datos) {
            agregarFila(fila);
        }
        mantenerMinimoFilas();
    }

    public void setColumnas(String[] columnas) {
        modelo.setColumnIdentifiers(columnas);
    }

    public String getNombresColumnas() {
        return nombresColumnas;
    }

    public void setNombresColumnas(String nombresColumnas) {
        this.nombresColumnas = nombresColumnas;
        
        if (nombresColumnas != null && !nombresColumnas.trim().isEmpty()) {
            String[] fragmentos = nombresColumnas.split("[,\\n\\r]+");
            
            java.util.ArrayList<String> columnasValidas = new java.util.ArrayList<>();
            for (String frag : fragmentos) {
                if (!frag.trim().isEmpty()) {
                    columnasValidas.add(frag.trim());
                }
            }
            
            String[] columnasFinales = columnasValidas.toArray(new String[0]);
            setColumnas(columnasFinales);
            mantenerMinimoFilas();
        }
    }
    
    public int getAnchoPanel() {
        return anchoPanel;
    }

    public void setAnchoPanel(int anchoPanel) {
        this.anchoPanel = anchoPanel;
        setPreferredSize(new Dimension(this.anchoPanel, this.altoPanel));
        setSize(new Dimension(this.anchoPanel, this.altoPanel));
        revalidate();
        repaint();
    }

    public int getAltoPanel() {
        return altoPanel;
    }

    public void setAltoPanel(int altoPanel) {
        this.altoPanel = altoPanel;
        setPreferredSize(new Dimension(this.anchoPanel, this.altoPanel));
        setSize(new Dimension(this.anchoPanel, this.altoPanel));
        revalidate();
        repaint();
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        this.roundTopLeft = round;
        this.roundTopRight = round;
        this.roundBottomLeft = round;
        this.roundBottomRight = round;
        repaint();
    }

    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
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
