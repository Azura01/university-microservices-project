import asyncio
import requests
import json
from typing import List, Dict

class FastAPIDataLoader:
    def __init__(self, base_url: str = "http://localhost:8080/api"):
        self.base_url = base_url
        self.session = requests.Session()
        
    def load_sample_data(self):
        """Carga datos de prueba del Politécnico Grancolombiano via API calls"""
        print("🔄 Iniciando carga de datos de prueba para FastAPI Client...")
        
        try:
            # Verificar conexión con core-service
            response = self.session.get(f"{self.base_url}/products")
            if response.status_code == 200:
                products = response.json()
                print(f"✅ Conexión exitosa con core-service")
                print(f"📦 Productos disponibles: {len(products)}")
                
                # Mostrar productos colombianos disponibles
                for product in products:
                    print(f"   - {product['name']}: ${product['price']:,.0f} COP (Stock: {product['quantity']})")
                    
                # Verificar stock
                stock_response = self.session.get(f"{self.base_url}/stock")
                if stock_response.status_code == 200:
                    stock_data = stock_response.json()
                    print(f"📊 Información de inventario disponible: {len(stock_data)} items")
                    
                print("✅ FastAPI Client configurado correctamente para trabajar con datos del Politécnico Grancolombiano")
                return True
                
            else:
                print(f"❌ Error conectando con core-service: {response.status_code}")
                return False
                
        except requests.exceptions.ConnectionError:
            print("❌ No se puede conectar con core-service. Asegúrate de que esté ejecutándose en puerto 8080")
            return False
        except Exception as e:
            print(f"❌ Error inesperado: {str(e)}")
            return False
    
    def test_product_operations(self):
        """Prueba operaciones de productos específicas del FastAPI client"""
        print("\n🧪 Probando operaciones específicas del FastAPI Client...")
        
        try:
            # Test: Obtener productos con filtros (simulado)
            response = self.session.get(f"{self.base_url}/products")
            if response.status_code == 200:
                products = response.json()
                
                # Filtrar productos colombianos por precio
                expensive_products = [p for p in products if p['price'] > 500000]
                affordable_products = [p for p in products if p['price'] <= 500000]
                
                print(f"💰 Productos premium (>$500,000 COP): {len(expensive_products)}")
                print(f"💵 Productos accesibles (≤$500,000 COP): {len(affordable_products)}")
                
                # Mostrar estadísticas de inventario
                total_value = sum(p['price'] * p['quantity'] for p in products)
                print(f"💎 Valor total del inventario: ${total_value:,.0f} COP")
                
                return True
        except Exception as e:
            print(f"❌ Error en pruebas: {str(e)}")
            return False
    
    def generate_sample_requests(self):
        """Genera ejemplos de requests que el FastAPI client puede hacer"""
        print("\n📋 Ejemplos de requests para FastAPI Client:")
        
        sample_requests = [
            {
                "method": "GET",
                "url": f"{self.base_url}/products",
                "description": "Obtener todos los productos del Politécnico Grancolombiano"
            },
            {
                "method": "GET", 
                "url": f"{self.base_url}/stock",
                "description": "Consultar inventario disponible"
            },
            {
                "method": "GET",
                "url": f"{self.base_url}/products/1",
                "description": "Obtener detalles de producto específico"
            }
        ]
        
        for i, req in enumerate(sample_requests, 1):
            print(f"   {i}. {req['method']} {req['url']}")
            print(f"      → {req['description']}")

def main():
    """Función principal para ejecutar el data loader"""
    print("🎓 Data Loader - FastAPI Client del Politécnico Grancolombiano")
    print("=" * 60)
    
    loader = FastAPIDataLoader()
    
    # Cargar y verificar datos
    if loader.load_sample_data():
        loader.test_product_operations()
        loader.generate_sample_requests()
        print("\n✅ Data Loader completado exitosamente!")
    else:
        print("\n❌ Data Loader falló. Verifica que core-service esté ejecutándose.")

if __name__ == "__main__":
    main()