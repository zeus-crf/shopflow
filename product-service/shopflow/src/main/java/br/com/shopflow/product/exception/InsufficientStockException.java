package br.com.shopflow.product.exception;

public class InsufficientStockException extends RuntimeException {

public InsufficientStockException(Integer requested, Integer available){
        super("Estoque insuficiente. Solicitado: " + requested + ", disponível: " + available);
    }
}
