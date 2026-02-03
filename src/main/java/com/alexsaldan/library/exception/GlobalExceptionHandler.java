package com.alexsaldan.library.exception;

//GlobalExceptionHandler.java - Manipulador global de exceções
//Esta classe captura exceções e retorna respostas HTTP apropriadas.
//Usamos @ControllerAdvice para aplicar a todos os controladores.



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice // Aplica este handler a todos os controladores
public class GlobalExceptionHandler {

 // Manipula exceções genéricas (ex: erros inesperados)
 @ExceptionHandler(Exception.class)
 public ResponseEntity<String> handleGenericException(Exception ex) {
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + ex.getMessage());
 }

 // Manipula erros de tipo nos parâmetros (ex: ID não numérico)
 @ExceptionHandler(MethodArgumentTypeMismatchException.class)
 public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetro inválido: " + ex.getMessage());
 }
}