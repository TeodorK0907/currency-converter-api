# Currency-Converter-API

This project provides a currency conversion service with endpoints for retrieving exchange rates, creating conversions,
and retrieving conversion history from the created conversions in the application. 
The app makes use of an in-memory H2 database with pre-generated data and table,
integration with an external API for exchange rates and conversion functionality.

## Endpoints

### 1. Get Exchange Rate

Endpoint: `GET/exchange-rate`

Description: Retrieves the exchange rate based on the provided currency code pairs and target currency.  
The exchange rate response contains the following components:
1. Quote in the target currency
2. The target currency for which the quote corresponds to.


### 2. Create Conversion

Endpoint: `POST/conversion`

Description: Creates a conversion based on the provided currency code pairs and amount in the target currency,
utilizing the exchange rate.  
The conversion contains the following components:
1. Converted amount in target currency.
2. Transaction timestamp.
3. Unique transaction identifier (UUID) for short.

### 3. Get Conversion History List

Endpoint: `GET/conversions`

Description: Returns a paginated list of created conversions in the system.  
The paginated list can be filtered by either a unique transaction identifier or a timeframe.  
The paginated conversion list contains the following components:
1. Timestamp after to list all conversions after that point in time (Can be combined with timestamp before).
2. Timestamp before to list all conversions before that point in time (Can be combined with timestamp after).
3. Unique transaction identifier (UUID) for short to list either one or more transactions with the provided identifier.
4. Page number on the list either the default (1) or a provided number as a query parameter if such page is available.
5. Page size on the list either the default (5) or a provided number as a query parameter.

## Database

This project utilizes an in-memory H2 database. The schema containing pre-generated data can be found in `schema.sql`.

## External API

The project makes calls to an external API for both exchange rates and creating conversions.

## Docker Support

The project includes a Dockerfile and a `docker-compose.yml` file to facilitate deployment on any hosting system. 
The project image can be pulled from https://hub.docker.com/r/teodork0907/teodor-kehayov-repository/tags under the tag  
converter-v1.

### Starting the Project with Docker

1. Navigate to the project directory.
2. Run `docker-compose up`.
3. The web app will boot and be available for use.

## Swagger Documentation

The project includes Swagger documentation with sample data for testing purposes.  
In Swagger you may learn more about the correct inputs the web app requires and what error message it may return given
incorrect input.
Once the web app is running, the documentation can be accessed from the default localhost port link.

