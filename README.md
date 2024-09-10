Task Manager
Project Overview
Task Manager is a simple web application that allows users to create, edit, and manage their tasks. The application provides user authentication, task management features (create, update, delete tasks), and a clean UI for interacting with tasks.

The project is built using Spring Boot as the backend framework, Thymeleaf for server-side rendering, and PostgreSQL as the database.

Features
User Authentication: Register and log in with an email and password.
Task Management:
Create new tasks with a title and description.
View, edit, and delete existing tasks.
Associate tasks with users.
Responsive UI: Built with Thymeleaf and Bootstrap for a user-friendly experience.
Data Persistence: Uses PostgreSQL to store user and task data.
Technologies Used
Backend: Spring Boot
Frontend: Thymeleaf, Bootstrap
Database: PostgreSQL
Security: Spring Security
Build Tool: Gradle
Version Control: Git
Java Version: 17+

Installation and Setup Prerequisites
Ensure you have the following installed on your local machine:
Java 17+
Gradle
PostgreSQL

Clone the Repository
git clone https://github.com/andicretu/TaskManager.git
cd TaskManager

Database Setup
1. Create a PostgreSQL database for the project:
createdb taskmanager

2. Update the application.properties (or application.yml) file with your PostgreSQL database credentials:
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

Running the Application
Build the project using Gradle:
./gradlew build

Run the application:
./gradlew bootRun

Open your browser and navigate to:
http://localhost:8080


Usage
1. User Authentication
Register: Navigate to /registerUser to create a new account.
Login: After registering, log in with your email and password.
2. Task Management
Create Task: After logging in, click on the "Create Task" button to add a new task.

Edit Task: Click on a task name to edit its title and description.
Delete Task: Click on the "Delete" button next to a task to remove it.
