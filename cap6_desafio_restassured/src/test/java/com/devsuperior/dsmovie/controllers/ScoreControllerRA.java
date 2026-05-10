package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	private Long existingMovieId, nonExistingMovieId, dependentMovieId;		
	
	private Map<String, Object> putScoreInstance;
	
	@BeforeEach
	public void setup() throws JSONException {
		baseURI = "http://localhost:8080";
		
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";		
				
		putScoreInstance = new HashMap<>();
		putScoreInstance.put("movieId", 1L);
		putScoreInstance.put("score", 4.5);		
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		
		nonExistingMovieId = 100L;
		putScoreInstance.put("movieId", nonExistingMovieId);
		JSONObject score = new JSONObject(putScoreInstance);		
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(score)	
		.when()
		    .put("/scores")
		.then()
			.statusCode(404)
			.body("error", equalTo("Recurso não encontrado"))
			.body("status", equalTo(404));
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		
		putScoreInstance.remove("movieId");
	    JSONObject score = new JSONObject(putScoreInstance);

	    given()
	        .header("Content-type", "application/json")
	        .header("Authorization", "Bearer " + adminToken)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	        .body(score)
	    .when()
	        .put("/scores")
	    .then()
	        .statusCode(422)
	        .body("errors.message", hasItems("Campo requerido"));
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		
		existingMovieId = 1L;
		putScoreInstance.put("score", -1);
		JSONObject score = new JSONObject(putScoreInstance);		
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(score)	
		.when()
		    .put("/scores")
		.then()
			.statusCode(422)
			.body("error", equalTo("Dados inválidos"))
			.body("errors.message", hasItems("Valor mínimo 0"))
			.body("status", equalTo(422));
	}
}
