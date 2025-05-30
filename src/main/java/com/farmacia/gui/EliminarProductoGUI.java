package com.farmacia.gui;
import com.farmacia.core.InventarioFarmacia;
import com.farmacia.dto.ProductoDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EliminarProductoGUI extends JFrame {
    private final InventarioFarmacia inventario;
    private final JFrame parent;

    private final JTextField txtCodigo;
    private final JTextArea txtInfoProducto;

    public EliminarProductoGUI(JFrame parent) {
        this.parent = parent;
        this.inventario = new InventarioFarmacia();

        setTitle("Eliminar Producto");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        panelSuperior.add(new JLabel("Código de Barras:"));
        txtCodigo = new JTextField(15);
        panelSuperior.add(txtCodigo);

        JButton btnBuscar = new JButton("Buscar Producto");
        btnBuscar.addActionListener(this::buscarProducto);
        panelSuperior.add(btnBuscar);

        panel.add(panelSuperior, BorderLayout.NORTH);

        txtInfoProducto = new JTextArea();
        txtInfoProducto.setEditable(false);
        txtInfoProducto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtInfoProducto);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this::eliminarProducto);
        panelInferior.add(btnEliminar);

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
                    "Producto a eliminar:\n\n" +
                            "Nombre: %s\n" +
                            "Código: %s\n" +
                            "Precio: $%.2f\n" +
                            "Stock: %d\n\n" +
                            "¿Está seguro que desea eliminar este producto?",
                    producto.getNombre(),
                    producto.getCodigoBarras(),
                    producto.getPrecioUnitario(),
                    producto.getCantidadStock()
            ));
        }
    }

    private void eliminarProducto(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de barras",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este producto?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (inventario.eliminarProducto(codigo)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
            }
        }
    }
}