package com.team.prosvita;

import com.team.prosvita.entities.User;
import com.team.prosvita.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class ProsvitaApplication {

	private static IUserRepository userRepository;

	@Autowired
	public ProsvitaApplication(IUserRepository userRepository) {
		ProsvitaApplication.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProsvitaApplication.class, args);

		// READ ALL
		Iterable<User> users = userRepository.findAll();
		for (User user : users) {
			System.out.println(user);
		}

		// READ
		// if Optional is returned, use get() to unpack it
		// syntax option 1
		userRepository.findById(1).ifPresent(System.out::println);

		/*// syntax option 2
		Optional<User> user = userRepository.findById(1);
		if (user.isPresent()) {
			System.out.println(user.get());
		}*/

		/*// syntax option 3
		Optional<User> user = userRepository.findById(1);
		user.ifPresent(System.out::println);*/

		// INSERT
		userRepository.save(new User("janeeyre", "Jane", "Eyre", "jane.eyre@gmail.com", "hashed_password"));
		userRepository.findByEmail("jane.eyre@gmail.com").ifPresent(System.out::println);

		// DELETE
		userRepository.deleteById(4);
		userRepository.findById(4).ifPresent(System.out::println);

		// UPDATE
		Optional<User> optionalUser = userRepository.findById(1);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			System.out.println(user);
			user.setName("Edward");
			userRepository.save(user);
			System.out.println(user);
		}
	}
}