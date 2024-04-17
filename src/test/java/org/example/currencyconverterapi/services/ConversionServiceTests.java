package org.example.currencyconverterapi.services;

import org.example.currencyconverterapi.configurations.WebClientConfig;
import org.example.currencyconverterapi.exceptions.EntityNotFoundException;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.example.currencyconverterapi.repositories.ConversionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.currencyconverterapi.helpers.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceTests {

    @Mock
    private ConversionRepository currencyRepo;
    @Mock
    private WebClientConfig webClient;
    @Mock
    private WebClient client;
    @InjectMocks
    private ConversionServiceImpl currencyService;

    private Conversion mockConversion;
    private Conversion anotherMockConversion;
    private Pageable page;
    private List<Conversion> conversions;
    private Page<Conversion> output;
    private ConversionFilterOptions mockFilterOptions;

    @BeforeEach
    void setUp() {
        mockConversion = createMockConversion();
        anotherMockConversion = createMockConversion();
        page = PageRequest.of(0, 5);
        conversions = List.of(mockConversion, anotherMockConversion);
        output = new PageImpl<>(conversions);
    }

    @Test
    void getAllWithFilter_Should_ReturnConversionsByAfterAndBefore_WhenArgumentsValid() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                LocalDateTime.of(2024, 4, 4, 10, 10, 10),
                LocalDateTime.now(), null, null, null
        );

        Mockito.when(currencyRepo.findAllByTimeStampIsAfterAndTimeStampIsBeforeOrderByTimeStamp
                (mockFilterOptions.getAfter().get(),
                        mockFilterOptions.getBefore().get(), page)).thenReturn(output);

        //Act
        currencyService.getAllWithFilter(mockFilterOptions);

        //Assert
        Mockito.verify(currencyRepo, Mockito.times(1))
                .findAllByTimeStampIsAfterAndTimeStampIsBeforeOrderByTimeStamp
                        (mockFilterOptions.getAfter().get(), mockFilterOptions.getBefore().get(), page);
    }

    @Test
    void getAllWIthFilter_Should_ReturnConversionsByTimestampAfter_WhenArgumentsValid() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                LocalDateTime.now(), null, null, null, null
        );

        Mockito.when(currencyRepo.findAllByTimeStampAfterOrderByTimeStamp
                (mockFilterOptions.getAfter().get(), page)).thenReturn(output);

        //Act
        currencyService.getAllWithFilter(mockFilterOptions);

        //Assert
        Mockito.verify(currencyRepo, Mockito.times(1))
                .findAllByTimeStampAfterOrderByTimeStamp
                        (mockFilterOptions.getAfter().get(), page);
    }

    @Test
    void getAllWIthFilter_Should_ReturnConversionsByTimestampBefore_WhenArgumentsValid() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                null, LocalDateTime.now(), null, null, null
        );

        Mockito.when(currencyRepo.findAllByTimeStampBeforeOrderByTimeStamp
                (mockFilterOptions.getBefore().get(), page)).thenReturn(output);

        //Act
        currencyService.getAllWithFilter(mockFilterOptions);

        //Assert
        Mockito.verify(currencyRepo, Mockito.times(1))
                .findAllByTimeStampBeforeOrderByTimeStamp
                        (mockFilterOptions.getBefore().get(), page);
    }

    @Test
    void getAllWIthFilter_Should_ReturnConversionsByUUID_WhenArgumentsValid() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                null, null, "uniqueTransaction", null, null
        );

        Mockito.when(currencyRepo.findAllByUUID
                (mockFilterOptions.getTransactionId().get(), page)).thenReturn(output);

        //Act
        currencyService.getAllWithFilter(mockFilterOptions);

        //Assert
        Mockito.verify(currencyRepo, Mockito.times(1))
                .findAllByUUID(mockFilterOptions.getTransactionId().get(), page);
    }

    @Test
    void getAllWithFilter_ShouldThrow_InvalidRequestException_WhenNoRequestParamsUsed() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                null, null, null, null, null
        );

        //Act & Assert
        Assertions.assertThrows(InvalidRequestException.class,
                () -> currencyService.getAllWithFilter(mockFilterOptions));
    }

    @Test
    void getAllWithFilter_ShouldThrow_InvalidRequestException_WhenBothCriteriaUsed() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                LocalDateTime.now(), null, "uniqueTransaction", null, null
        );

        //Act & Assert
        Assertions.assertThrows(InvalidRequestException.class,
                () -> currencyService.getAllWithFilter(mockFilterOptions));
    }

    @Test
    void getAllWIthFilter_ShouldThrow_InvalidRequestException_WhenTimeframeIsInvalid() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                LocalDateTime.MAX, LocalDateTime.now(), null, null, null
        );

        //Act & Assert
        Assertions.assertThrows(InvalidRequestException.class,
                () -> currencyService.getAllWithFilter(mockFilterOptions));
    }

    @Test
    void getAllWithFilter_ShouldThrow_EntityNotFoundException_WhenNoResourceHasBeenFound() {
        //Arrange
        mockFilterOptions = new ConversionFilterOptions(
                null, null, "uniqueTransaction", null, null
        );

        conversions = new ArrayList<>();
        output = new PageImpl<>(conversions);
        Mockito.when(currencyRepo.
                        findAllByUUID(mockFilterOptions.getTransactionId().get(), page))
                .thenReturn(output);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> currencyService.getAllWithFilter(mockFilterOptions));
    }
}