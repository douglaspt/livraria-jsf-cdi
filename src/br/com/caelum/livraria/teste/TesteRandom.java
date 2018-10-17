package br.com.caelum.livraria.teste;

import java.util.Random;

public class TesteRandom {
	
	public static void main(String[] args) {
		
		Random gerador = new Random(123);

        int resultado = gerador.nextInt(10);
        System.out.println(resultado);

        int resultado2 = gerador.nextInt(10);
        System.out.println(resultado2);
        
        System.out.println("**************************************");
        
        long millis = System.currentTimeMillis();
        Random geradorBoolean = new Random(millis);

        boolean valor = geradorBoolean.nextBoolean();
        System.out.println(valor);

        boolean valor2 = geradorBoolean.nextBoolean();
        System.out.println(valor2);



		
		
		
	}

}
