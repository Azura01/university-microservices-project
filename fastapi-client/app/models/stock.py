from pydantic import BaseModel

class StockInfo(BaseModel):
    product_id: int
    name: str
    quantity: int
    
class StockUpdate(BaseModel):
    quantity: int
    
class StockReservation(BaseModel):
    quantity: int