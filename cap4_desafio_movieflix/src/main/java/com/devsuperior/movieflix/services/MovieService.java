package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Optional<Movie> obj = repository.findById(id);
        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
        // Força ordenação por título, ignorando o que vier da requisição
        PageRequest pageRequest = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("title")
        );

        // genreId == 0 significa "sem filtro" → passa null para a query
        Long filterId = (genreId == 0) ? null : genreId;

        // 1ª query: paginação nativa, leve
        Page<MovieProjection> page = repository.searchByGenre(filterId, pageable);

        // Extrai os IDs da página atual
        List<Long> movieIds = page.map(MovieProjection::getId).toList();

        // 2ª query: busca entidades completas com genre carregado
        List<Movie> entities = repository.searchMoviesWithGenre(movieIds);

        // Converte para DTO e monta a página com os metadados da 1ª query
        List<MovieCardDTO> dtos = entities.stream()
                .map(MovieCardDTO::new)
                .toList();

        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }
}