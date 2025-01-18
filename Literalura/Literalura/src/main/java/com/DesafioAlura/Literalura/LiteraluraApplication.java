package com.DesafioAlura.Literalura;

import com.DesafioAlura.Literalura.Main.Main;
import com.DesafioAlura.Literalura.Repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Main main = new Main(repository);
		main.MostrarMenu();
	}
}
