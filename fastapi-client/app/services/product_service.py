import requests
from typing import List, Optional
from app.models.product import Product, ProductCreate, ProductUpdate

class ProductService:
    def __init__(self, base_url: str = "http://localhost:8080/api"):
        self.base_url = base_url
    
    def get_all_products(self) -> List[Product]:
        response = requests.get(f"{self.base_url}/products")
        response.raise_for_status()
        return [Product(**product) for product in response.json()]
    
    def get_product_by_id(self, product_id: int) -> Optional[Product]:
        response = requests.get(f"{self.base_url}/products/{product_id}")
        if response.status_code == 404:
            return None
        response.raise_for_status()
        return Product(**response.json())
    
    def create_product(self, product: ProductCreate) -> Product:
        response = requests.post(
            f"{self.base_url}/products",
            json=product.model_dump()  # ✅ Para Pydantic 2.x
        )
        response.raise_for_status()
        return Product(**response.json())
    
    def update_product(self, product_id: int, product: ProductUpdate) -> Optional[Product]:
        response = requests.put(
            f"{self.base_url}/products/{product_id}",
            json=product.model_dump(exclude_unset=True)  # ✅ Para Pydantic 2.x
        )
        if response.status_code == 404:
            return None
        response.raise_for_status()
        return Product(**response.json())
    
    def delete_product(self, product_id: int) -> bool:
        response = requests.delete(f"{self.base_url}/products/{product_id}")
        return response.status_code == 200