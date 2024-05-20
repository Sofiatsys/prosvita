package com.team.prosvita;

import com.team.prosvita.entities.User;
import com.team.prosvita.repository.IUserRepository;
import com.team.prosvita.service.UserService;
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
        //userRepository.save(new User("janeeyre", "Jane", "Eyre", "jane.eyre@gmail.com", "hashed_password"));
        // instead of constructor used a builder
		/*userRepository.save(User.builder()
				.username("janeeyre")
				.name("Jane")
				.surname("Eyre")
				.email("jane.eyre@gmail.com")
				.password("hashed_password")
				.role(Role.USER)
				.status(Status.ACTIVE)
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.updatedAt(new Timestamp(System.currentTimeMillis()))
				.build());*/
//        userRepository.findByEmail("jane.eyre@gmail.com").ifPresent(System.out::println);
//
//        // DELETE
//        userRepository.deleteById(4);
//        userRepository.findById(4).ifPresent(System.out::println);
//
//        // UPDATE
//        Optional<User> optionalUser = userRepository.findById(1);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            System.out.println(user);
//            user.setName("Edward");
//            userRepository.save(user);
//            System.out.println(user);
//        }
//
//        // CUSTOM METHODS
//        boolean isValidLogin = userRepository.existsUserByEmailAndPassword("admin@example.com", "adminpassword");
//        System.out.println(isValidLogin);
//        userRepository.findUserByEmailAndPassword("admin@example.com", "adminpassword").ifPresent(System.out::println);

        // resolve conflict with loaduser
//        // USERSERVICE
//        UserService userService = new UserService(userRepository);
//        System.out.println(userService.loadUserByUsername("alice@example.com"));
//        System.out.println(userService.loadUserByUsername("noname@example.com"));
    }
}