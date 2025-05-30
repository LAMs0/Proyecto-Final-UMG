package com.farmacia.gui;

import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;

import javax.swing.*;
import java.awt.*;

public class AgregarProductosGUI extends JFrame {
    private final InventarioFarmacia inventario;
    private final JFrame parent;

    private final JTextField txtNombre, txtCodigo, txtPrecio, txtStock;

    public AgregarProductosGUI(JFrame parent) {
        this.parent = parent;
        this.inventario = new InventarioFarmacia();

        setTitle("Agregar Producto");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Código de Barras:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Precio Unitario:"));
        txtPrecio = new JTextField();
        panel.add(txtPrecio);

        panel.add(new JLabel("Cantidad en Stock:"));
        txtStock = new JTextField();
        panel.add(txtStock);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarProducto());
        panel.add(btnAgregar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar);

        add(panel);
        setVisible(true);
    }

    private void agregarProducto() {
        try {
            String nombre = txtNombre.getText().trim();
            String codigo = txtCodigo.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (nombre.isEmpty() || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y código son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (precio <= 0 || stock < 0) {
                JOptionPane.showMessageDialog(this, "Precio y stock deben ser valores positivos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProductoDTO producto = new ProductoDTO(nombre, codigo, precio, stock);

            if (inventario.agregarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar producto. ¿Código ya existe?");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
