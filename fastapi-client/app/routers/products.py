from fastapi import APIRouter, HTTPException, Depends
from typing import List
from app.models.product import Product, ProductCreate, ProductUpdate
from app.services.product_service import ProductService

router = APIRouter()

def get_product_service():
    return ProductService()

@router.get("/", response_model=List[Product])
async def get_products(service: ProductService = Depends(get_product_service)):
    """Obtener todos los productos"""
    try:
        return service.get_all_products()
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.get("/{product_id}", response_model=Product)
async def get_product(product_id: int, service: ProductService = Depends(get_product_service)):
    """Obtener un producto por ID"""
    try:
        product = service.get_product_by_id(product_id)
        if not product:
            raise HTTPException(status_code=404, detail="Producto no encontrado")
        return product
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.post("/", response_model=Product)
async def create_product(product: ProductCreate, service: ProductService = Depends(get_product_service)):
    """Crear un nuevo producto"""
    try:
        return service.create_product(product)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.put("/{product_id}", response_model=Product)
async def update_product(product_id: int, product: ProductUpdate, service: ProductService = Depends(get_product_service)):
    """Actualizar un producto"""
    try:
        updated_product = service.update_product(product_id, product)
        if not updated_product:
            raise HTTPException(status_code=404, detail="Producto no encontrado")
        return updated_product
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.delete("/{product_id}")
async def delete_product(product_id: int, service: ProductService = Depends(get_product_service)):
    """Eliminar un producto"""
    try:
        success = service.delete_product(product_id)
        if not success:
            raise HTTPException(status_code=404, detail="Producto no encontrado")
        return {"message": "Producto eliminado exitosamente"}
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))