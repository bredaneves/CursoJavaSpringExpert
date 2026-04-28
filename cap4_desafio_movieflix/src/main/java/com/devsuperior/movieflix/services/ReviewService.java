package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> findByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().map(ReviewDTO::new).toList();
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        // Busca o filme pelo id informado no DTO
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));

        // Pega o usuário autenticado via JWT — sem receber nada por parâmetro
        User user = authService.authenticated();

        // Monta a entidade
        Review review = new Review();
        review.setText(dto.getText());
        review.setMovie(movie);
        review.setUser(user);

        // Salva e converte para DTO já com os dados do usuário
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }
}
