## Features

- **API Endpoints:** Provides endpoints for managing AI profiles and handling user interactions.
- **MongoDB Integration:** Stores and retrieves data using MongoDB.

## Technologies Used

- **Spring Boot:** Framework for building the backend application.
- **MongoDB:** NoSQL database for storing AI profiles and user interactions.
- **Spring Data MongoDB:** Simplifies MongoDB data access.
- **Maven:** Build and dependency management tool.
- **Spring ai:** LLama3.1 and GPT model for chat responses
## Installation

To set up and run this project locally, follow these steps:

1. **Clone the repository:**
   - git clone https://github.com/adityasinghskit/tinder-ai.git
   - cd tinder-ai
2. **Install dependencies:**
    - mvn clean install
3. **Configure MongoDB:**
    - spring.data.mongodb.uri=mongodb://localhost:27017/tinder-ai
4. **Run the application:**
    - mvn spring-boot:run
