Sistema de Gestión de Farmacia
Java
MariaDB
Maven
Swing

Aplicación de escritorio para gestión de inventario de farmacia desarrollada con:

- Java Swing para la interfaz gráfica

- MariaDB como base de datos

- Maven para gestión de dependencias

📋 Requisitos Previos
Java JDK 11+ (recomendado Amazon Corretto 11)

bash
java -version
MariaDB 10.5+ instalado y configurado

bash
mysql --version
Maven 3.9+ para construcción del proyecto

bash
mvn -v
HeidiSQL para administración de la base de datos

🚀 Configuración Inicial
1. Configuración de la Base de Datos
Ejecuta el script SQL en MariaDB (usar HeidiSQL):
-- Datos de ejemplo
INSERT INTO productos (codigo_barras, nombre, precio_unitario, cantidad_stock) VALUES
('P001', 'Paracetamol 500mg', 25.50, 100),
('P002', 'Ibuprofeno 400mg', 32.75, 80);
2. Configura las credenciales de conexión
Edita el archivo src/main/java/com/farmacia/db/DatabaseConnection.java:
java
private static final String URL = "jdbc:mariadb://localhost:3306/sistemagestionfarmaciasql";
private static final String USER = "tu_usuario";  // Ej: root
private static final String PASSWORD = "tu_contraseña";
🏗️ Compilación y Ejecución
Opción 1: Ejecutar directamente con Maven
bash
mvn clean compile
mvn exec:java
Opción 2: Generar JAR ejecutable
bash
mvn clean package
Esto generará un archivo ejecutable en:

target/inventario-farmacia-1.0.0.jar
Ejecútalo con:

bash
java -jar target/inventario-farmacia-1.0.0.jar
Opción 3: Ejecutar en IDE (Eclipse/IntelliJ)
Importa el proyecto como proyecto Maven

Ejecuta la clase principal: com.farmacia.gui.FarmaciaGUI

🖥️ Funcionalidades Principales
✅ Gestión completa de productos (CRUD)

✅ Búsqueda por código de barras o nombre

✅ Control de inventario y ventas

✅ Actualización de precios

✅ Visualización de inventario en tabla

               
