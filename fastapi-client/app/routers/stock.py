from fastapi import APIRouter, HTTPException, Depends
from app.models.stock import StockInfo, StockUpdate, StockReservation
from app.services.stock_service import StockService

router = APIRouter()

def get_stock_service():
    return StockService()

@router.get("/{product_id}", response_model=StockInfo)
async def get_stock(product_id: int, service: StockService = Depends(get_stock_service)):
    """Consultar el stock de un producto"""
    try:
        stock_info = service.get_stock_info(product_id)
        if not stock_info:
            raise HTTPException(status_code=404, detail="Producto no encontrado")
        return stock_info
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.put("/{product_id}", response_model=StockInfo)
async def update_stock(product_id: int, stock_update: StockUpdate, service: StockService = Depends(get_stock_service)):
    """Actualizar el stock de un producto"""
    try:
        updated_stock = service.update_stock(product_id, stock_update.quantity)
        if not updated_stock:
            raise HTTPException(status_code=404, detail="Producto no encontrado")
        return updated_stock
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.post("/{product_id}/reserve")
async def reserve_stock(product_id: int, reservation: StockReservation, service: StockService = Depends(get_stock_service)):
    """Reservar stock de un producto"""
    try:
        success = service.reserve_stock(product_id, reservation.quantity)
        return {
            "success": success,
            "message": "Stock reservado exitosamente" if success else "No se pudo reservar el stock"
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))