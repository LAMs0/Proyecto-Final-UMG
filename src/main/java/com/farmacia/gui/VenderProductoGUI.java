package com.farmacia.gui;

import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VenderProductoGUI extends JFrame {
    private final InventarioFarmacia inventario;
    private final JFrame parent;

    private final JTextField txtCodigo, txtCantidad;
    private final JTextArea txtInfoProducto;

    public VenderProductoGUI(JFrame parent) {
        this.parent = parent;
        this.inventario = new InventarioFarmacia();

        setTitle("Vender Producto");
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 5, 5));

        panelSuperior.add(new JLabel("Código de Barras:"));
        txtCodigo = new JTextField();
        panelSuperior.add(txtCodigo);

        JButton btnBuscar = new JButton("Buscar Producto");
        btnBuscar.addActionListener(this::buscarProducto);
        panelSuperior.add(btnBuscar);

        panelSuperior.add(new JLabel()); // Espacio vacío

        panelSuperior.add(new JLabel("Cantidad a vender:"));
        txtCantidad = new JTextField();
        panelSuperior.add(txtCantidad);

        panel.add(panelSuperior, BorderLayout.NORTH);

        txtInfoProducto = new JTextArea();
        txtInfoProducto.setEditable(false);
        txtInfoProducto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtInfoProducto);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnVender = new JButton("Confirmar Venta");
        btnVender.addActionListener(this::venderProducto);
        panelInferior.add(btnVender);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelInferior.add(btnCancelar);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void buscarProducto(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de barras",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProductoDTO producto = inventario.buscarProductoPorCodigo(codigo);

        if (producto == null) {
            txtInfoProducto.setText("Producto no encontrado");
        } else {
            txtInfoProducto.setText(String.format(
                    "Producto encontrado:\n\n" +
                            "Nombre: %s\n" +
                            "Código: %s\n" +
                            "Precio unitario: $%.2f\n" +
                            "Stock disponible: %d",
                    producto.getNombre(),
                    producto.getCodigoBarras(),
                    producto.getPrecioUnitario(),
                    producto.getCantidadStock()
            ));
        }
    }

    private void venderProducto(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        if (codigo.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (inventario.venderProducto(codigo, cantidad)) {
                ProductoDTO producto = inventario.buscarProductoPorCodigo(codigo);
                double total = producto.getPrecioUnitario() * cantidad;

                JOptionPane.showMessageDialog(this,
                        String.format("Venta realizada exitosamente!\n\n" +
                                        "Producto: %s\n" +
                                        "Cantidad: %d\n" +
                                        "Precio unitario: $%.2f\n" +
                                        "Total: $%.2f",
                                producto.getNombre(), cantidad,
                                producto.getPrecioUnitario(), total));
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}