package com.devsuperior.dsmovie.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;
	
	@Mock
	private ScoreRepository repository;
			
	@Mock
	private MovieRepository movieRepository;	
	
	@Mock
	private UserService userService;
	
	private Long existingMovieId, nonExistingMovieId;	
	private ScoreEntity score;
	private ScoreDTO scoreDTO;
	private UserEntity user;
	private MovieEntity movie;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingMovieId = 1L;
		nonExistingMovieId = 2L;		
		
		user = UserFactory.createUserEntity();		
		
		score = ScoreFactory.createScoreEntity();		
		scoreDTO = ScoreFactory.createScoreDTO();				
		
		movie = MovieFactory.createMovieEntity();
		movie.setId(existingMovieId);
		movie.getScores().add(score);
		
		Mockito.when(userService.authenticated()).thenReturn(user);
		
		Mockito.when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));
	    Mockito.when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
	    
	    Mockito.when(movieRepository.save(any())).thenReturn(movie);
	    Mockito.when(repository.saveAndFlush(any())).thenReturn(score);		
	}
	
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		
		MovieDTO result = service.saveScore(scoreDTO);

	    Assertions.assertNotNull(result);
	    Assertions.assertEquals(existingMovieId, result.getId());
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		
		ScoreDTO scoreDTOWithInvalidId = new ScoreDTO(nonExistingMovieId, 4.5);

	    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
	        service.saveScore(scoreDTOWithInvalidId);
	    });
	}
}
