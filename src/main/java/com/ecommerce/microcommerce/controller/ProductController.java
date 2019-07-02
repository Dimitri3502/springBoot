package com.ecommerce.microcommerce.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;

@RestController
@RequestMapping("/produits")
public class ProductController {
	
	private final ProductDao productDao;

	
	public ProductController(ProductDao productDao) {
		this.productDao = productDao;
	}

	@GetMapping
	public List<Product> listeProduits() {
		return productDao.findAll();
	}
	
	@GetMapping("/{id}")
	public Product afficherUnProduit(@PathVariable int id) {
		return productDao.findById(id);
		
	}
	
	@DeleteMapping("/{id}")
	public void supprimerUnProduit(@PathVariable int id) {
		productDao.deleteById(id);
		
	}
	
	@PutMapping
	  public void updateProduit(@RequestBody Product product) {
	      productDao.save(product);
	  }
	
	@PostMapping()
	public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {
		Product save = productDao.save(product);
		
		if(save == null) {
			return ResponseEntity.noContent().build();
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value = "test/{prixLimit}")
	public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
	    return productDao.chercherUnProduitCher(prixLimit);
	}
	
	
}
