package com.farmacia.core;

import com.farmacia.db.DatabaseConnection;
import com.farmacia.dto.ProductoDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioFarmacia {

    public boolean agregarProducto(ProductoDTO producto) {
        String sql = "INSERT INTO productos (codigo_barras, nombre, precio_unitario, cantidad_stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getCodigoBarras());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecioUnitario());
            stmt.setInt(4, producto.getCantidadStock());

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            DatabaseConnection.rollbackConnection();
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public ProductoDTO buscarProductoPorCodigo(String codigoBarras) {
        String sql = "SELECT * FROM productos WHERE codigo_barras = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ProductoDTO(
                        rs.getString("nombre"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad_stock")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public List<ProductoDTO> mostrarInventario() {
        List<ProductoDTO> inventario = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inventario.add(new ProductoDTO(
                        rs.getString("nombre"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad_stock")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar inventario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return inventario;
    }

    public boolean venderProducto(String codigoBarras, int cantidadVendida) {
        String sql = "UPDATE productos SET cantidad_stock = cantidad_stock - ? WHERE codigo_barras = ? AND cantidad_stock >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadVendida);
            stmt.setString(2, codigoBarras);
            stmt.setInt(3, cantidadVendida);

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            if (affectedRows == 0) {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock o el producto no existe",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (SQLException e) {
            DatabaseConnection.rollbackConnection();
            JOptionPane.showMessageDialog(null, "Error al vender producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarPrecio(String codigoBarras, double nuevoPrecio) {
        String sql = "UPDATE productos SET precio_unitario = ? WHERE codigo_barras = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, nuevoPrecio);
            stmt.setString(2, codigoBarras);

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            DatabaseConnection.rollbackConnection();
            JOptionPane.showMessageDialog(null, "Error al actualizar precio: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean eliminarProducto(String codigoBarras) {
        String sql = "DELETE FROM productos WHERE codigo_barras = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoBarras);

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            DatabaseConnection.rollbackConnection();
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<ProductoDTO> buscarProductosPorNombre(String nombre) {
        List<ProductoDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ? ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultados.add(new ProductoDTO(
                        rs.getString("nombre"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad_stock")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return resultados;
    }
}