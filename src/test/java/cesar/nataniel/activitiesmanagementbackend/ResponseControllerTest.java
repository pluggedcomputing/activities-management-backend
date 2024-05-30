package cesar.nataniel.activitiesmanagementbackend;

import cesar.nataniel.activitiesmanagementbackend.controller.ResponseController;
import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.repository.ResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ResponseControllerTest {

	@Mock
	private ResponseRepository responseRepository;

	@InjectMocks
	private ResponseController responseController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateResponseQuestion() {
		// Given
		Response response = new Response();
		response.setUserName("user123");
		response.setIdApp("app456");
		response.setIsCorrect(true);

		// When
		responseController.createResponse(response);

		// Then
		verify(responseRepository, times(1)).save(response);
	}

	@Test
	public void testGetAllQuestion() {
		// Given
		List<Response> responses = new ArrayList<>();
		responses.add(new Response());
		responses.add(new Response());
		when(responseRepository.findAll()).thenReturn(responses);

		// When
		List<Response> result = responseController.getAllResponse();

		// Then
		assertEquals(2, result.size());
	}

	// Add more tests for other controller methods similarly
}

