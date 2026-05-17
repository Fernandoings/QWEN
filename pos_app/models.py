import sqlite3
from datetime import datetime

class Database:
    def __init__(self, db_name='pos_inventory.db'):
        self.db_name = db_name
    
    def get_connection(self):
        return sqlite3.connect(self.db_name)
    
    def add_product(self, name, price, cost, stock, category='', barcode=''):
        conn = self.get_connection()
        cursor = conn.cursor()
        try:
            cursor.execute('''
                INSERT INTO products (name, price, cost, stock, category, barcode)
                VALUES (?, ?, ?, ?, ?, ?)
            ''', (name, price, cost, stock, category, barcode))
            conn.commit()
            return True
        except sqlite3.IntegrityError:
            return False
        finally:
            conn.close()
    
    def update_product(self, product_id, name, price, cost, stock, category, barcode):
        conn = self.get_connection()
        cursor = conn.cursor()
        try:
            cursor.execute('''
                UPDATE products 
                SET name=?, price=?, cost=?, stock=?, category=?, barcode=?
                WHERE id=?
            ''', (name, price, cost, stock, category, barcode, product_id))
            conn.commit()
            return True
        except:
            return False
        finally:
            conn.close()
    
    def delete_product(self, product_id):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('DELETE FROM products WHERE id=?', (product_id,))
        conn.commit()
        conn.close()
    
    def get_all_products(self):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('SELECT * FROM products ORDER BY name')
        products = cursor.fetchall()
        conn.close()
        return products
    
    def get_product_by_id(self, product_id):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('SELECT * FROM products WHERE id=?', (product_id,))
        product = cursor.fetchone()
        conn.close()
        return product
    
    def search_products(self, search_term):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('''
            SELECT * FROM products 
            WHERE name LIKE ? OR barcode LIKE ? OR category LIKE ?
            ORDER BY name
        ''', (f'%{search_term}%', f'%{search_term}%', f'%{search_term}%'))
        products = cursor.fetchall()
        conn.close()
        return products
    
    def update_stock(self, product_id, quantity):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('''
            UPDATE products SET stock = stock - ? WHERE id = ?
        ''', (quantity, product_id))
        conn.commit()
        conn.close()
    
    def create_sale(self, items, total, payment_method):
        conn = self.get_connection()
        cursor = conn.cursor()
        
        # Crear la venta
        cursor.execute('''
            INSERT INTO sales (total, payment_method)
            VALUES (?, ?)
        ''', (total, payment_method))
        sale_id = cursor.lastrowid
        
        # Agregar detalles de la venta
        for item in items:
            product_id, quantity, price, subtotal = item
            cursor.execute('''
                INSERT INTO sale_details (sale_id, product_id, quantity, price, subtotal)
                VALUES (?, ?, ?, ?, ?)
            ''', (sale_id, product_id, quantity, price, subtotal))
            
            # Actualizar inventario
            cursor.execute('''
                UPDATE products SET stock = stock - ? WHERE id = ?
            ''', (quantity, product_id))
        
        conn.commit()
        conn.close()
        return sale_id
    
    def get_sales(self):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('''
            SELECT s.id, s.total, s.payment_method, s.created_at,
                   COUNT(sd.id) as items_count
            FROM sales s
            LEFT JOIN sale_details sd ON s.id = sd.sale_id
            GROUP BY s.id
            ORDER BY s.created_at DESC
        ''')
        sales = cursor.fetchall()
        conn.close()
        return sales
    
    def get_sale_details(self, sale_id):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('''
            SELECT sd.quantity, p.name, sd.price, sd.subtotal
            FROM sale_details sd
            JOIN products p ON sd.product_id = p.id
            WHERE sd.sale_id = ?
        ''', (sale_id,))
        details = cursor.fetchall()
        conn.close()
        return details
    
    def get_low_stock_products(self, threshold=10):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('''
            SELECT * FROM products WHERE stock <= ? ORDER BY stock
        ''', (threshold,))
        products = cursor.fetchall()
        conn.close()
        return products
    
    def get_inventory_value(self):
        conn = self.get_connection()
        cursor = conn.cursor()
        cursor.execute('SELECT SUM(cost * stock) FROM products')
        value = cursor.fetchone()[0]
        conn.close()
        return value if value else 0.0
