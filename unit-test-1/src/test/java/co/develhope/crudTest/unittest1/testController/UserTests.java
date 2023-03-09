package co.develhope.crudTest.unittest1.testController;


import co.develhope.crudTest.unittest1.controllers.UserController;
import co.develhope.crudTest.unittest1.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserController controller;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void userControllerLoads(){
	assertThat(controller).isNotNull();
	}

	private User getUserFromId(Long id) throws Exception{

		try {
			MvcResult result = this.mockMvc.perform(get("/user/" + id))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();


			String userJSON = result.getResponse().getContentAsString();
			User user = objectMapper.readValue(userJSON, User.class);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isNotNull();

			return user;
		}catch (Exception e){
			return null;
		}
	}


	private User createUser1() throws Exception {
		User user = new User();
		user.setActive(true);
		user.setName("Nicola");
		user.setSurname("Boniello");
		user.setAge(25);

		return createUser1(user);
	}

	private User createUser1(User user) throws Exception {
		MvcResult result = createAUsers(user);
		User userFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

		assertThat(userFromResponse).isNotNull();
		assertThat(userFromResponse.getId()).isNotNull();

		return userFromResponse;
	}



	// Funzione ausiliaria per la creazione di un utente per la lista

	private MvcResult createAUsers() throws Exception {
		User user = new User();
		user.setActive(true);
		user.setName("Nicola");
		user.setSurname("Boniello");
		user.setAge(25);

		return createAUsers(user);
	}

	private MvcResult createAUsers(User user) throws Exception {
		if (user == null) return null;

		String userJson = objectMapper.writeValueAsString(user);

		return this.mockMvc.perform(post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

	}

	// Test controller createUser.
	@Test
	void createUser() throws Exception {

		MvcResult result = createAUsers();

		User userFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
		assertThat(userFromResponse.getId()).isNotNull();
	}

	// Test controller getALlUser.
	@Test
	void readUserList() throws Exception {
		createUser();

		MvcResult result = this.mockMvc.perform(get("/user"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		List<User> usersFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
		assertThat(usersFromResponse.size()).isNotZero();
	}

	//Test controller get user by id.

	@Test
	void readSingleUser() throws Exception {
		User user = createUser1();
		User userFromResponse = getUserFromId(user.getId());
		assertThat(userFromResponse.getId()).isEqualTo(user.getId());

	}

	//Test controller updateUser
	@Test
	void updateUser() throws Exception{
		User user = createUser1();

		String newName = "Mario";
		String newSurname = "rossi";
		user.setName(newName);
		user.setSurname(newSurname);

		String userJSON = objectMapper.writeValueAsString(user);

		MvcResult result = this.mockMvc.perform(put("/user/"+user.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		User userFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

		assertThat(userFromResponse.getId()).isEqualTo(user.getId());
		assertThat(userFromResponse.getName()).isEqualTo(newName);

		User userFromResponseGet = getUserFromId(user.getId());
		assertThat(userFromResponseGet.getId()).isEqualTo(user.getId());
		assertThat(userFromResponseGet.getName()).isEqualTo(newName);
		assertThat(userFromResponseGet.getSurname()).isEqualTo(newSurname);
	}

	//Test controller deleteUserById
	@Test
	void deleteUser() throws Exception{
		User user = createUser1();
		assertThat(user.getId()).isNotNull();

		this.mockMvc.perform(delete("/user/"+user.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

	    User userFromResponse = getUserFromId(user.getId());
		assertThat(userFromResponse).isNull();

	}

}











