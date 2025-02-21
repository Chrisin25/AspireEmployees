**AspireEmployees** is a Java-based Spring Boot application designed to manage employee records, integrate with an SQL database, and offer API endpoints for employee and stream management. The application is built using JPA (Hibernate) for ORM mapping and MySQL for the database.

### Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Data JPA** for database interaction
- **MySQL** for data storage
- **JUnit & Mockito** for unit testing and mocking dependencies
- **Maven** for project management

## Features
- **Employee Management**: Add, update, and retrieve employee records.
- **Stream Management**: Retrieve available streams associated with employees.
- **SQL Database**: Stores employee and stream data using MySQL.
- **Testing**: Unit tests written using JUnit and Mockito.

## API Endpoints

### 1. Add Employee
- **Endpoint**: `POST /api/v1/employee`
- **Description**: Adds a new employee to the system.
- **Request Body**: JSON representation of an `Employee` object.
- **Response**: Returns a success message with the newly created employee's ID.

```json
{
  "message": "Successfully created",
  "id": 123
}
```

### 2. Get Employees
- **Endpoint**: `GET /api/v1/employee`
- **Description**: Retrieves a list of employees whose names start with the provided string.
- **Request Parameter**: `startsWith` (String) - The prefix of employee names to filter by.
- **Response**: List of employees matching the filter.

```json
[
  {
    "employeeId": 1,
    "employeeName": "John Doe",
    "designation": "Software Engineer",
    "managerId": 5,
    "streamName": "Development",
    "accountName": "Account1"
  },
  ...
]
```

### 3. Get Streams
- **Endpoint**: `GET /api/v1/streams`
- **Description**: Retrieves all available streams.
- **Response**: List of all streams.

```json
[
  {
    "streamId": 1,
    "streamName": "Development",
    "accountName": "Account1"
  },
  {
    "streamId": 2,
    "streamName": "Testing",
    "accountName": "Account2"
  },
  ...
]
```

### 4. Update Employee
- **Endpoint**: `PUT /api/v1/employee`
- **Description**: Updates employee details such as manager ID or designation.
- **Request Parameters**:
  - `employeeId` (Integer) - The ID of the employee to be updated.
  - `managerId` (Integer, optional) - The ID of the manager for the employee.
  - `designation` (String, optional) - The new designation of the employee.
- **Response**: A success or error message.

```json
{
  "message": "Successfully updated employee details"
}
```

## Database Schema

The application relies on a MySQL database to store employee and stream data. Below is the SQL schema used:

```sql
CREATE TABLE Account (
    accountId INT AUTO_INCREMENT PRIMARY KEY,
    accountName VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Stream (
    streamId INT AUTO_INCREMENT PRIMARY KEY,
    streamName VARCHAR(255) UNIQUE NOT NULL,
    accountName VARCHAR(255),
    FOREIGN KEY (accountName) REFERENCES Account(accountName)
);

CREATE TABLE Employee (
    employeeId INT AUTO_INCREMENT PRIMARY KEY,
    employeeName VARCHAR(255) NOT NULL,
    designation VARCHAR(255),
    streamName VARCHAR(255),
    managerId INT,
    accountName VARCHAR(255),
    FOREIGN KEY (streamName) REFERENCES Stream(streamName),
    FOREIGN KEY (accountName) REFERENCES Account(accountName),
    FOREIGN KEY (managerId) REFERENCES Employee(employeeId)
);
```

## Installation

Follow these steps to set up the project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/Chrisin25/AspireEmployees.git
   cd AspireEmployees
   ```

2. Set up your MySQL database:
   - Create a database schema in MySQL and configure the database connection in `src/main/resources/application.properties`.
   - Example connection string:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/aspire_employees_db
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     ```

3. Install dependencies and build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Testing

The project is equipped with unit tests for service methods and controllers. The tests are written using **JUnit 5** and **Mockito** for mocking dependencies.

To run the tests:

```bash
mvn test
```
