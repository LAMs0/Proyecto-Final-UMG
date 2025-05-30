package com.farmacia.gui;
import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActualizarPrecioGUI extends JFrame {
    private final InventarioFarmacia inventario;
    private final JFrame parent;

    private final JTextField txtCodigo, txtNuevoPrecio;
    private final JTextArea txtInfoProducto;

    public ActualizarPrecioGUI(JFrame parent) {
        this.parent = parent;
        this.inventario = new InventarioFarmacia();

        setTitle("Actualizar Precio");
        setSize(500, 300);
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

        panelSuperior.add(new JLabel("Nuevo Precio:"));
        txtNuevoPrecio = new JTextField();
        panelSuperior.add(txtNuevoPrecio);

        panel.add(panelSuperior, BorderLayout.NORTH);

        txtInfoProducto = new JTextArea();
        txtInfoProducto.setEditable(false);
        txtInfoProducto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtInfoProducto);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this::actualizarPrecio);
        panelInferior.add(btnActualizar);

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
                    "Producto actual:\n\n" +
                            "Nombre: %s\n" +
                            "Código: %s\n" +
                            "Precio actual: $%.2f\n" +
                            "Stock: %d",
                    producto.getNombre(),
                    producto.getCodigoBarras(),
                    producto.getPrecioUnitario(),
                    producto.getCantidadStock()
            ));
        }
    }

    private void actualizarPrecio(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String nuevoPrecioStr = txtNuevoPrecio.getText().trim();

        if (codigo.isEmpty() || nuevoPrecioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

            if (nuevoPrecio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (inventario.actualizarPrecio(codigo, nuevoPrecio)) {
                JOptionPane.showMessageDialog(this, "Precio actualizado exitosamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el precio");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}