CREATE DATABASE sistemagestionfarmaciasql;
USE sistemagestionfarmaciasql;

CREATE TABLE productos (
    codigo_barras VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    cantidad_stock INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_precio CHECK (precio_unitario > 0),
    CONSTRAINT chk_stock CHECK (cantidad_stock >= 0)
) ENGINE=InnoDB;

-- Datos de ejemplo
INSERT INTO productos (codigo_barras, nombre, precio_unitario, cantidad_stock) VALUES
('7501001234567', 'Paracetamol 500mg', 25.50, 100),
('7501007654321', 'Ibuprofeno 400mg', 32.75, 80);