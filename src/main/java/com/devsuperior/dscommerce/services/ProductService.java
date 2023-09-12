package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.exeptions.ResouceNotFoundException;
import com.devsuperior.dscommerce.intities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id).orElseThrow(
				() -> new ResouceNotFoundException("Recurso não encontrado"));
		return new ProductDTO(product);
		
		/* 
		 * Optional<Product> result = repository.findById(id);
		Product product = result.get();
		ProductDTO dto = new ProductDTO(product);
		return dto;
		*/
		
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}	

	@Transactional
	public ProductDTO upDate(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			copyToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResouceNotFoundException("Recurso não encontrado");
		}
		
	}
	
	private void copyToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
	}
	
	@Transactional
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResouceNotFoundException("Recurso não encontrado");
		}
		repository.deleteById(id);
	}
}
