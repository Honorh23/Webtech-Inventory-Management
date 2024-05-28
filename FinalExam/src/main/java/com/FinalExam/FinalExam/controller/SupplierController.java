package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.model.Supplier;
import com.FinalExam.FinalExam.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping("/supplier")
    Supplier newSupplier(@RequestBody Supplier newSupplier){
        return supplierRepository.save(newSupplier);
    }

    @GetMapping("/supplier")
    List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    @GetMapping("/supplier/{id}")
    Supplier getSupplierById(@PathVariable Long id) {
        return supplierRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @PutMapping("/supplier/{id}")
    Supplier updateSupplier(@RequestBody Supplier newSupplier, @PathVariable Long id){
        return supplierRepository.findById(id).map(supplier -> {
            supplier.setName(newSupplier.getName());
            supplier.setContactPerson(newSupplier.getContactPerson());
            supplier.setEmail(newSupplier.getEmail());
            supplier.setPhone(newSupplier.getPhone());
            return supplierRepository.save(supplier);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/supplier/{id}")
    String deleteSupplier(@PathVariable Long id){
        if (!supplierRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        supplierRepository.deleteById(id);
        return "Supplier deleted successfully";
    }
}
