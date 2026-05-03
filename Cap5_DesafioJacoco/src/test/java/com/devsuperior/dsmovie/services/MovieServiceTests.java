package com.devsuperior.dsmovie.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
	
	@InjectMocks
	private MovieService service;
	
	@Mock
	private MovieRepository repository;
	
	private long existingMovieId, nonExistingMovieId, dependentMovieId;
	private String movieName;
	private MovieEntity movie;
	private MovieDTO movieDTO;
	private PageImpl<MovieEntity> page;
	
	@BeforeEach
	void setUp() throws Exception {
		existingMovieId = 1L;
		nonExistingMovieId = 2L;
		dependentMovieId = 3L;
		
		movieName = "Cisne Negro";
		
		movie = MovieFactory.createMovieEntity();
		movieDTO = new MovieDTO(movie);
		page = new PageImpl<>(List.of(movie));
		Pageable pageable = PageRequest.of(0, 12);
		
		/*Mockito.when(repository.searchByTitle(movieName, pageable).thenReturn(Optional.of(movie));
		
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.searchByName(any(), (Pageable)any())).thenReturn(page);
		
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(repository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.existsById(existingProductId)).thenReturn(true);
		Mockito.when(repository.existsById(dependentProductId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingProductId)).thenReturn(false);
		
		Mockito.doNothing().when(repository).deleteById(existingProductId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentProductId);*/
	}
	
	@Test
	public void findAllShouldReturnPagedMovieDTO() {
	}
	
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}
	
	@Test
	public void insertShouldReturnMovieDTO() {
	}
	
	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
	}
}
