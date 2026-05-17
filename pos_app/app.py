from flask import Flask, request, jsonify, send_from_directory
from flask_cors import CORS
from models import Database
import os

app = Flask(__name__)
CORS(app)
db = Database()

# Servir el archivo HTML
@app.route('/')
def index():
    return send_from_directory('.', 'index.html')

# API de Productos
@app.route('/api/products', methods=['GET'])
def get_products():
    products = db.get_all_products()
    result = []
    for p in products:
        result.append({
            'id': p[0],
            'name': p[1],
            'price': p[2],
            'cost': p[3],
            'stock': p[4],
            'category': p[5],
            'barcode': p[6],
            'created_at': p[7]
        })
    return jsonify(result)

@app.route('/api/product', methods=['POST'])
def add_product():
    data = request.json
    success = db.add_product(
        name=data['name'],
        price=data['price'],
        cost=data['cost'],
        stock=data['stock'],
        category=data.get('category', ''),
        barcode=data.get('barcode', '')
    )
    if success:
        return jsonify({'message': 'Producto agregado exitosamente'}), 201
    else:
        return jsonify({'error': 'Error al agregar producto'}), 400

@app.route('/api/product/<int:product_id>', methods=['PUT'])
def update_product(product_id):
    data = request.json
    success = db.update_product(
        product_id=product_id,
        name=data['name'],
        price=data['price'],
        cost=data['cost'],
        stock=data['stock'],
        category=data.get('category', ''),
        barcode=data.get('barcode', '')
    )
    if success:
        return jsonify({'message': 'Producto actualizado exitosamente'}), 200
    else:
        return jsonify({'error': 'Error al actualizar producto'}), 400

@app.route('/api/product/<int:product_id>', methods=['DELETE'])
def delete_product(product_id):
    db.delete_product(product_id)
    return jsonify({'message': 'Producto eliminado exitosamente'}), 200

# API de Ventas
@app.route('/api/sale', methods=['POST'])
def create_sale():
    data = request.json
    items = data['items']  # [[product_id, quantity, price, subtotal], ...]
    total = data['total']
    payment_method = data['payment_method']
    
    sale_id = db.create_sale(items, total, payment_method)
    return jsonify({'message': 'Venta creada exitosamente', 'sale_id': sale_id}), 201

@app.route('/api/sales', methods=['GET'])
def get_sales():
    sales = db.get_sales()
    result = []
    for s in sales:
        result.append({
            'id': s[0],
            'total': s[1],
            'payment_method': s[2],
            'created_at': s[3],
            'items_count': s[4]
        })
    return jsonify(result)

@app.route('/api/sale/<int:sale_id>/details', methods=['GET'])
def get_sale_details(sale_id):
    details = db.get_sale_details(sale_id)
    result = []
    for d in details:
        result.append({
            'quantity': d[0],
            'name': d[1],
            'price': d[2],
            'subtotal': d[3]
        })
    return jsonify(result)

# API de Reportes
@app.route('/api/low-stock', methods=['GET'])
def get_low_stock():
    products = db.get_low_stock_products(threshold=10)
    result = []
    for p in products:
        result.append({
            'id': p[0],
            'name': p[1],
            'price': p[2],
            'cost': p[3],
            'stock': p[4],
            'category': p[5],
            'barcode': p[6]
        })
    return jsonify(result)

if __name__ == '__main__':
    # Inicializar la base de datos
    from database import init_db
    init_db()
    
    print("Iniciando servidor POS en http://localhost:5000")
    app.run(debug=False, host='0.0.0.0', port=5000)
