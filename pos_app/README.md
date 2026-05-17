# Sistema POS con Inventario

Aplicación completa de Punto de Venta (POS) con gestión de inventario, base de datos SQLite e interfaz amigable.

## Características

- ✅ **Punto de Venta**: Interfaz intuitiva para realizar ventas
- ✅ **Gestión de Inventario**: Agregar, editar y eliminar productos
- ✅ **Base de Datos SQLite**: Almacenamiento persistente de productos y ventas
- ✅ **Interfaz Amigable**: Diseño moderno y responsive
- ✅ **Reportes**: Estadísticas de ventas y alertas de stock bajo
- ✅ **Múltiples Métodos de Pago**: Efectivo, tarjeta, transferencia

## Estructura del Proyecto

```
pos_app/
├── app.py              # Servidor Flask con API REST
├── database.py         # Inicialización de la base de datos
├── models.py           # Modelo de datos y operaciones DB
├── index.html          # Interfaz de usuario
├── requirements.txt    # Dependencias de Python
└── README.md           # Este archivo
```

## Instalación

1. Instalar dependencias:
```bash
pip install -r requirements.txt
```

2. Ejecutar la aplicación:
```bash
python app.py
```

3. Abrir el navegador en: `http://localhost:5000`

## Uso

### Punto de Venta
- Buscar productos por nombre o código de barras
- Hacer clic en un producto para agregarlo al carrito
- Ajustar cantidades con los botones + / -
- Procesar venta seleccionando método de pago

### Gestión de Inventario
- Click en "+ Nuevo Producto" para agregar
- Editar o eliminar productos existentes
- El stock se actualiza automáticamente con las ventas

### Reportes
- Ver ventas totales
- Consultar valor del inventario
- Alertas de productos con stock bajo (≤10 unidades)

## API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/products | Obtener todos los productos |
| POST | /api/product | Crear nuevo producto |
| PUT | /api/product/{id} | Actualizar producto |
| DELETE | /api/product/{id} | Eliminar producto |
| POST | /api/sale | Registrar nueva venta |
| GET | /api/sales | Obtener historial de ventas |
| GET | /api/sale/{id}/details | Ver detalle de venta |
| GET | /api/low-stock | Productos con stock bajo |

## Tecnologías

- **Backend**: Python + Flask
- **Frontend**: HTML5, CSS3, JavaScript
- **Base de Datos**: SQLite
- **API**: RESTful JSON

## Capturas

La aplicación incluye:
- Diseño moderno con gradientes y animaciones
- Tarjetas de productos interactivas
- Carrito de compras en tiempo real
- Modales para formularios
- Tablas de datos organizadas
- Estadísticas visuales
