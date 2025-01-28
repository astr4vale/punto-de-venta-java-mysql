CREATE DATABASE puntoderopajuvenil2;
USE puntoderopajuvenil2;

-- Tabla: usuarios
CREATE TABLE usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    dniUsuario VARCHAR(18) UNIQUE NOT NULL,
    nombreUsuario VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    correo VARCHAR(250),
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(40) DEFAULT 'vendedor',
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE clientes (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    dniRuc VARCHAR(20) UNIQUE NOT NULL,   -- DNI o RUC del cliente
    nombre VARCHAR(100) NOT NULL,         -- Nombre del cliente
    telefono VARCHAR(15),                 -- Teléfono (opcional)
    direccion VARCHAR(200),               -- Dirección (opcional)
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla: proveedores
CREATE TABLE proveedores (
    idProveedor INT AUTO_INCREMENT PRIMARY KEY,
    ruc VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla: productos
CREATE TABLE productos (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombreProducto VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    idProveedor INT,
    sku VARCHAR(50) UNIQUE NOT NULL,
    talla VARCHAR(10),
    color VARCHAR(20),
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idProveedor) REFERENCES proveedores(idProveedor) ON DELETE CASCADE
);

-- Tabla: ventas
CREATE TABLE ventas (
    idVenta INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario) ON DELETE CASCADE
);

-- Tabla: detalleVentas
CREATE TABLE detalleVentas (
    idDetalle INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT,
    idProducto INT,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idVenta) REFERENCES ventas(idVenta) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES productos(idProducto) ON DELETE CASCADE
);

-- Nueva Tabla: configuracion
CREATE TABLE configuracion (
    idConfiguracion INT AUTO_INCREMENT PRIMARY KEY,
    nombreTienda VARCHAR(100) NOT NULL,
    ruc VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Modificación de Tabla: facturas (con relación a configuracion)
CREATE TABLE facturas (
    idFactura INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT NOT NULL,
    idConfiguracion INT NOT NULL,
    idCliente INT NOT NULL,
    fechaFactura DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idVenta) REFERENCES ventas(idVenta) ON DELETE CASCADE,
    FOREIGN KEY (idConfiguracion) REFERENCES configuracion(idConfiguracion) ON DELETE CASCADE,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente) ON DELETE CASCADE
);


DELIMITER //

CREATE PROCEDURE RealizarVenta(
    IN p_idUsuario INT,
    IN p_dniRucCliente VARCHAR(20),
    IN p_nombreCliente VARCHAR(100),
    IN p_telefonoCliente VARCHAR(15),
    IN p_direccionCliente VARCHAR(200),
    IN p_productosJSON JSON
)
BEGIN
    DECLARE v_idVenta INT;
    DECLARE v_total DECIMAL(10,2) DEFAULT 0;
    DECLARE v_idConfiguracion INT;
    
    -- Iniciar transacción
    START TRANSACTION;
    
    -- Obtener la configuración activa de la tienda
    SELECT idConfiguracion INTO v_idConfiguracion
    FROM configuracion
    LIMIT 1;
    
    -- Crear la venta
    INSERT INTO ventas (idUsuario, total)
    VALUES (p_idUsuario, 0);
    
    SET v_idVenta = LAST_INSERT_ID();
    
    -- Procesar cada producto en el JSON
    BEGIN
        DECLARE v_finished INT DEFAULT 0;
        DECLARE v_idProducto INT;
        DECLARE v_cantidad INT;
        DECLARE v_precio DECIMAL(10,2);
        DECLARE v_stockActual INT;
        
        -- Iterar sobre el array JSON de productos
        BEGIN
            DECLARE i INT DEFAULT 0;
            DECLARE v_numItems INT;
            
            SET v_numItems = JSON_LENGTH(p_productosJSON);
            
            WHILE i < v_numItems DO
                -- Obtener valores del JSON
                SET v_idProducto = JSON_EXTRACT(p_productosJSON, CONCAT('$[', i, '].idProducto'));
                SET v_cantidad = JSON_EXTRACT(p_productosJSON, CONCAT('$[', i, '].cantidad'));
                
                -- Verificar stock disponible
                SELECT cantidad, precio INTO v_stockActual, v_precio
                FROM productos
                WHERE idProducto = v_idProducto;
                
                IF v_stockActual >= v_cantidad THEN
                    -- Insertar detalle de venta
                    INSERT INTO detalleVentas (idVenta, idProducto, cantidad, precioUnitario, subtotal)
                    VALUES (v_idVenta, v_idProducto, v_cantidad, v_precio, v_cantidad * v_precio);
                    
                    -- Actualizar stock
                    UPDATE productos
                    SET cantidad = cantidad - v_cantidad
                    WHERE idProducto = v_idProducto;
                    
                    -- Actualizar total
                    SET v_total = v_total + (v_cantidad * v_precio);
                ELSE
                    -- Si no hay stock suficiente, hacer rollback
                    ROLLBACK;
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Stock insuficiente';
                END IF;
                
                SET i = i + 1;
            END WHILE;
        END;
    END;
    
    -- Actualizar el total en la venta
    UPDATE ventas
    SET total = v_total
    WHERE idVenta = v_idVenta;
    
    -- Crear la factura
    INSERT INTO facturas (
        idVenta,
        idUsuario,
        idConfiguracion,
        dniRucCliente,
        nombreCliente,
        telefonoCliente,
        direccionCliente,
        total
    ) VALUES (
        v_idVenta,
        p_idUsuario,
        v_idConfiguracion,
        p_dniRucCliente,
        p_nombreCliente,
        p_telefonoCliente,
        p_direccionCliente,
        v_total
    );
    
    -- Confirmar la transacción
    COMMIT;
    
    -- Retornar el ID de la venta creada
    SELECT v_idVenta AS idVenta, v_total AS total;
    
END //

DELIMITER ;

CALL RealizarVenta(
    1, -- ID del usuario vendedor
    '12345678', -- DNI/RUC del cliente
    'Juan Pérez', -- Nombre del cliente
    '123456789', -- Teléfono del cliente
    'Av. Principal 123', -- Dirección del cliente
    '[
        {"idProducto": 1, "cantidad": 2},
        {"idProducto": 3, "cantidad": 1}
    ]' -- JSON con los productos
);

select * from detalleventas;

select * from productos;

select * FROM ventas;

select * from facturas;
