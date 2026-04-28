package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true,
        value = """
            SELECT tb_movie.id, tb_movie.title, tb_movie.sub_title AS subTitle,
                   tb_movie.movie_year AS movieYear, tb_movie.img_url AS imgUrl
            FROM tb_movie
            WHERE (:genreId IS NULL OR tb_movie.genre_id = :genreId)
            ORDER BY tb_movie.title
            """,
        countQuery = """
            SELECT COUNT(*) FROM tb_movie
            WHERE (:genreId IS NULL OR tb_movie.genre_id = :genreId)
            """)
    Page<MovieProjection> searchByGenre(Long genreId, Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :movieIds ORDER BY obj.title")
    List<Movie> searchMoviesWithGenre(List<Long> movieIds);
}