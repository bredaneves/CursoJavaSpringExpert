package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.services.GenreService;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

	@Autowired
	private GenreService service;
	
	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<Page<GenreDTO>> findAll(Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		Page<GenreDTO> page = service.findAll(pageRequest);		
		return ResponseEntity.ok().body(page);
	}
}