from fastapi import FastAPI
from app.routers import products, stock
from data_loader import FastAPIDataLoader
import asyncio

app = FastAPI(
    title="FastAPI Client - Sistema de Compras Politécnico Grancolombiano",
    description="Cliente FastAPI para gestión de productos e inventario del Politécnico Grancolombiano",
    version="1.0.0"
)

# Incluir routers
app.include_router(products.router, prefix="/products", tags=["products"])
app.include_router(stock.router, prefix="/stock", tags=["stock"])

@app.on_event("startup")
async def startup_event():
    """Ejecutar data loader al iniciar la aplicación"""
    print("🚀 Iniciando FastAPI Client del Politécnico Grancolombiano...")
    
    # Ejecutar data loader en background
    loader = FastAPIDataLoader()
    
    # Esperar un poco para que core-service esté listo
    await asyncio.sleep(3)
    
    # Cargar datos de prueba
    loader.load_sample_data()

@app.get("/")
async def root():
    return {
        "message": "FastAPI Client - Politécnico Grancolombiano",
        "description": "Sistema de gestión de compras e inventario",
        "endpoints": {
            "/products": "Gestión de productos",
            "/stock": "Gestión de inventario",
            "/docs": "Documentación interactiva"
        }
    }

@app.get("/health")
async def health_check():
    return {"status": "healthy", "service": "fastapi-client"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8001)