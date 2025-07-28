package com.university.config;

import com.university.model.*;
import com.university.model.enums.PurchaseOrderStatus;
import com.university.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la base de datos está vacía
        if (customerRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        // Crear Customers
        Customer customer1 = new Customer();
        customer1.setName("Carlos Andrés Rodríguez");
        customer1.setEmail("carlos.rodriguez@email.com");
        customer1.setPhone("+57 310 456 7890");
        customer1.setAddress("Carrera 15 #85-32, Bogotá");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("María Fernanda Gómez");
        customer2.setEmail("maria.gomez@email.com");
        customer2.setPhone("+57 310 234 5678");
        customer2.setAddress("Calle 70 #11-40, Medellín");
        customerRepository.save(customer2);

        Customer customer3 = new Customer();
        customer3.setName("Alejandro Vargas Moreno");
        customer3.setEmail("alejandro.vargas@email.com");
        customer3.setPhone("+57 310 987 6543");
        customer3.setAddress("Avenida 6N #23-45, Cali");
        customerRepository.save(customer3);

        // Crear Employees - ACTUALIZADO CON EMAILS DEL POLITÉCNICO GRANCOLOMBIANO
        Employee employee1 = new Employee("Ana Lucía Herrera", "ana.herrera@poligran.edu.co", "password123", "Ventas", "Gerente");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee("Diego Fernando Martínez", "diego.martinez@poligran.edu.co", "password456", "Compras", "Analista");
        employeeRepository.save(employee2);

        Employee employee3 = new Employee("Camila Andrea Torres", "camila.torres@poligran.edu.co", "password789", "Inventario", "Coordinadora");
        employeeRepository.save(employee3);

        // Crear Suppliers
        Supplier supplier1 = new Supplier();
        supplier1.setName("TecnoSuministros Colombia S.A.S.");
        supplier1.setEmail("contacto@tecnosuministros.com.co");
        supplier1.setPhone("+57 310 111 2222");
        supplier1.setAddress("Zona Industrial Bogotá, Calle 13 #68-45");
        supplierRepository.save(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setName("Oficinas y Más Ltda.");
        supplier2.setEmail("ventas@oficinasymas.com.co");
        supplier2.setPhone("+57 310 333 4444");
        supplier2.setAddress("Centro Comercial Andino, Local 205, Bogotá");
        supplierRepository.save(supplier2);

        Supplier supplier3 = new Supplier();
        supplier3.setName("Distribuidora Antioqueña");
        supplier3.setEmail("info@distribuidoraant.com.co");
        supplier3.setPhone("+57 310 555 6666");
        supplier3.setAddress("Carrera 50 #25-30, Medellín");
        supplierRepository.save(supplier3);

        // Crear Products
        Product product1 = new Product();
        product1.setName("Laptop Dell Inspiron 15");
        product1.setDescription("Laptop para oficina con 8GB RAM y 256GB SSD");
        product1.setPrice(2800000.0);  // Precio en pesos colombianos
        product1.setQuantity(25);
        product1.setSupplier(supplier1);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Monitor Samsung 24 pulgadas");
        product2.setDescription("Monitor Full HD para oficina");
        product2.setPrice(650000.0);  // Precio en pesos colombianos
        product2.setQuantity(50);
        product2.setSupplier(supplier1);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Silla Ergonómica Ejecutiva");
        product3.setDescription("Silla de oficina con soporte lumbar ajustable");
        product3.setPrice(450000.0);  // Precio en pesos colombianos
        product3.setQuantity(30);
        product3.setSupplier(supplier2);
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setName("Escritorio en L");
        product4.setDescription("Escritorio modular en forma de L para oficina");
        product4.setPrice(850000.0);
        product4.setQuantity(15);
        product4.setSupplier(supplier2);
        productRepository.save(product4);

        Product product5 = new Product();
        product5.setName("Impresora Multifuncional HP");
        product5.setDescription("Impresora, escáner y copiadora todo en uno");
        product5.setPrice(380000.0);
        product5.setQuantity(20);
        product5.setSupplier(supplier3);
        productRepository.save(product5);

        // Crear Purchase Orders
        PurchaseOrder order1 = new PurchaseOrder();
        order1.setCustomer(customer1);
        order1.setEmployee(employee1);
        order1.setProducts(Arrays.asList(product1, product2));
        order1.setTotalAmount(3450000.0);  // Total en pesos colombianos
        order1.setStatus(PurchaseOrderStatus.PENDING);
        purchaseOrderRepository.save(order1);

        PurchaseOrder order2 = new PurchaseOrder();
        order2.setCustomer(customer2);
        order2.setEmployee(employee2);
        order2.setProducts(Arrays.asList(product3, product4));
        order2.setTotalAmount(1300000.0);
        order2.setStatus(PurchaseOrderStatus.CONFIRMED);
        purchaseOrderRepository.save(order2);

        PurchaseOrder order3 = new PurchaseOrder();
        order3.setCustomer(customer3);
        order3.setEmployee(employee3);
        order3.setProducts(Arrays.asList(product5));
        order3.setTotalAmount(380000.0);
        order3.setStatus(PurchaseOrderStatus.PROCESSING);
        purchaseOrderRepository.save(order3);

        System.out.println("✅ Datos de prueba del Politécnico Grancolombiano cargados exitosamente!");
        System.out.println("📊 Datos creados:");
        System.out.println("   - " + customerRepository.count() + " customers");
        System.out.println("   - " + employeeRepository.count() + " employees");
        System.out.println("   - " + supplierRepository.count() + " suppliers");
        System.out.println("   - " + productRepository.count() + " products");
        System.out.println("   - " + purchaseOrderRepository.count() + " purchase orders");
    }
}