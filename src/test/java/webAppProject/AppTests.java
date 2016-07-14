package webAppProject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.servlet.Filter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import webAppProject.persistence.repository.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes={
		webAppProjectApplication.class, 
		SecurityConfig.class, 
		WebConfig.class,
		})
public class AppTests {
	
	@Autowired
	private WebApplicationContext wac;
	  
	@Autowired
	private Filter springSecurityFilterChain;
		
	private MockMvc mockMvc;
	
	@Before
	public void setup(){		
		this.mockMvc = MockMvcBuilders.
						webAppContextSetup(this.wac)
						.addFilter(springSecurityFilterChain)
						.apply(springSecurity())
						.build();
	}

	@Test
	public void cantViewPageIfNotLoggedIn() throws Exception {
		mockMvc.perform(get("https://localhost/"))
			   .andExpect(status().is(302))
			   .andExpect(redirectedUrl("https://localhost/user-login"))
			   .andDo(print());
	}
	
	@Test
	public void loggingInWithCorrectDetails() throws Exception {
		mockMvc.perform(post("https://localhost/login")
				.param("username", "alice")
				.param("password", "password")
				.with(csrf()))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/success-login"))
				.andDo(print());
	}
	
	@Test
	public void loggingInWithIncorrectDetails() throws Exception {
		mockMvc.perform(post("https://localhost/login")
				.param("username", "alice")
				.param("password", "wrong")
				.with(csrf()))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/error-login"))
				.andDo(print());	
		}
	
	@Test
	@WithUserDetails("alice")
	public void canAccessUserPageIfUser() throws Exception {
		mockMvc.perform(get("https://localhost/UserPage"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("userpage"))
			   .andDo(print());
	}
	
	@Test
	@WithUserDetails("alice")
	public void cantAccessAdminPageIfUser() throws Exception {
		mockMvc.perform(get("https://localhost/AdminPage"))
			   .andExpect(status().is(403))
			   .andExpect(forwardedUrl("/user-error"))
			   .andDo(print());
	}
	
	@Test
	@WithUserDetails("bob")
	public void cantAccessUserPageIfNotUser() throws Exception {
		mockMvc.perform(get("https://localhost/UserPage"))
		   .andExpect(status().is(403))
		   .andExpect(forwardedUrl("/user-error"))
		   .andDo(print());	
	}
	
	@Test
	@WithUserDetails("bob")
	public void canAccessAdminPageIfAdmin() throws Exception {
		mockMvc.perform(get("https://localhost/AdminPage"))
		   .andExpect(status().isOk())
		   .andExpect(view().name("adminpage"))
		   .andDo(print());
	}
	
}
