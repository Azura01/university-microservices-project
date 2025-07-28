import requests
from typing import Optional
from app.models.stock import StockInfo, StockUpdate

class StockService:
    def __init__(self, base_url: str = "http://localhost:8080/api"):
        self.base_url = base_url
    
    def get_stock_info(self, product_id: int) -> Optional[StockInfo]:
        response = requests.get(f"{self.base_url}/stock/{product_id}")
        if response.status_code == 404:
            return None
        response.raise_for_status()
        data = response.json()
        return StockInfo(
            product_id=data["product_id"],
            name=data["name"],
            quantity=data["quantity"]
        )
    
    def update_stock(self, product_id: int, quantity: int) -> Optional[StockInfo]:
        response = requests.put(
            f"{self.base_url}/stock/{product_id}",
            json={"quantity": quantity}
        )
        if response.status_code == 404:
            return None
        response.raise_for_status()
        data = response.json()
        return StockInfo(
            product_id=data["product_id"],
            name=data["name"],
            quantity=data["quantity"]
        )
    
    def reserve_stock(self, product_id: int, quantity: int) -> bool:
        response = requests.post(
            f"{self.base_url}/stock/{product_id}/reserve",
            json={"quantity": quantity}
        )
        if response.status_code != 200:
            return False
        return response.json().get("success", False)