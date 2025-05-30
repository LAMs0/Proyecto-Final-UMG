package com.farmacia.gui;

import javax.swing.*;
import java.awt.*;

public class FarmaciaGUI extends JFrame {

    public FarmaciaGUI() {
        setTitle("Sistema de Gestión de Farmacia");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo);

        JButton btnAgregar = new JButton("1. Agregar Producto");
        JButton btnBuscar = new JButton("2. Buscar Producto");
        JButton btnInventario = new JButton("3. Mostrar Inventario");
        JButton btnVender = new JButton("4. Vender Producto");
        JButton btnActualizar = new JButton("5. Actualizar Precio");
        JButton btnEliminar = new JButton("6. Eliminar Producto");

        btnAgregar.addActionListener(e -> new AgregarProductosGUI(this));
        btnBuscar.addActionListener(e -> new BuscarProductosGUI(this));
        btnInventario.addActionListener(e -> new MostrarInventarioGUI(this));
        btnVender.addActionListener(e -> new VenderProductoGUI(this));
        btnActualizar.addActionListener(e -> new ActualizarPrecioGUI(this));
        btnEliminar.addActionListener(e -> new EliminarProductoGUI(this));

        panel.add(btnAgregar);
        panel.add(btnBuscar);
        panel.add(btnInventario);
        panel.add(btnVender);
        panel.add(btnActualizar);
        panel.add(btnEliminar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FarmaciaGUI();
        });
    }
}