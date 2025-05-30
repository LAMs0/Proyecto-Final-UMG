package com.farmacia.gui;

import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MostrarInventarioGUI extends JFrame {
    private final InventarioFarmacia inventario;

    public MostrarInventarioGUI(JFrame parent) {
        this.inventario = new InventarioFarmacia();

        setTitle("Inventario Completo");
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");
        model.addColumn("Última Actualización");

        // Obtener datos
        List<ProductoDTO> productos = inventario.mostrarInventario();

        for (ProductoDTO producto : productos) {
            model.addRow(new Object[]{
                    producto.getCodigoBarras(),
                    producto.getNombre(),
                    String.format("$%.2f", producto.getPrecioUnitario()),
                    producto.getCantidadStock(),
                    "N/A" // Podrías agregar la fecha si la tienes en tu DTO
            });
        }

        JTable tabla = new JTable(model);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel lblTotal = new JLabel("Total de productos: " + productos.size());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblTotal, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}