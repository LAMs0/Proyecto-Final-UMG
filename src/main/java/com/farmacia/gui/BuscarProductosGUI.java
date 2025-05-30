package com.farmacia.gui;


import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuscarProductosGUI extends JFrame {
    private final InventarioFarmacia inventario;
    private final JFrame parent;

    private final JTextField txtBusqueda;
    private final JTextArea txtResultado;

    public BuscarProductosGUI(JFrame parent) {
        this.parent = parent;
        this.inventario = new InventarioFarmacia();

        setTitle("Buscar Producto");
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        panelBusqueda.add(new JLabel("Buscar por código o nombre:"));
        txtBusqueda = new JTextField(20);
        panelBusqueda.add(txtBusqueda);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProducto());
        panelBusqueda.add(btnBuscar);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtResultado);

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private void buscarProducto() {
        String busqueda = txtBusqueda.getText().trim();

        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código o nombre para buscar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar por código exacto
        ProductoDTO producto = inventario.buscarProductoPorCodigo(busqueda);

        if (producto != null) {
            txtResultado.setText("Resultado por código:\n\n" + producto.toString());
        } else {
            // Buscar por nombre aproximado
            List<ProductoDTO> resultados = inventario.buscarProductosPorNombre(busqueda);

            if (resultados.isEmpty()) {
                txtResultado.setText("No se encontraron productos con: " + busqueda);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Resultados por nombre (").append(resultados.size()).append("):\n\n");

                for (ProductoDTO p : resultados) {
                    sb.append(p.toString()).append("\n\n");
                }

                txtResultado.setText(sb.toString());
            }
        }
    }
}