# Student Grading System

A JavaFX-based Student Grading System that allows users to register, log in, and manage academic tasks and performance. The application supports user authentication, grade computation, task tracking, and test score evaluation using a MySQL backend.

## 🔧 Built With

- Java 17+
- JavaFX
- FXML with SceneBuilder
- MySQL (via JDBC)

---

## ✨ Features

- **Login & Account Creation**
  - Secure user login system with account registration.
- **General Average Calculator**
  - Add grades and units, compute total GWA (General Weighted Average).
- **Task Management (To-Do List)**
  - Create tasks with priorities (To Do, Need, Immediate, Crucial).
  - Mark tasks as complete and monitor progress.
- **Test Score Evaluation**
  - Add test scores and total items.
  - Compute percentages, transmuted grade, and GWA.
  - Pie chart visualization using JavaFX.
- **Database Integration**
  - Stores users, tasks, grades, and test scores in a MySQL database.

---

## 📂 Project Structure

StudentGradingSystem/
├── application/ # JavaFX main application
├── controller/ # All controller classes (event handlers)
├── model/ # Data models and logic
├── view/ # FXML and CSS files
├── DatabaseMySQL/ # MySQL JDBC connectivity

---

## 🛠️ Setup Instructions

### Prerequisites

- JDK 17+
- JavaFX SDK
- MySQL Server
- SceneBuilder (optional)

### Database Setup

1. Create database and tables in MySQL:

```sql
CREATE DATABASE studentgradingsystem;

CREATE TABLE users (
  usernames VARCHAR(50),
  passwords VARCHAR(50),
  id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE completed (
  completed INT DEFAULT 0,
  id INT,
  pending INT DEFAULT 0
);

CREATE TABLE grades (
  grades DOUBLE,
  units DOUBLE,
  id INT AUTO_INCREMENT PRIMARY KEY,
  user INT
);

CREATE TABLE tasks (
  task VARCHAR(255),
  `date due` DATE,
  `time due` VARCHAR(20),
  subject VARCHAR(100),
  type VARCHAR(50),
  level VARCHAR(50),
  id INT,
  taskid INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE test (
  score INT,
  total INT,
  id INT
);
Update database credentials in DatabaseConnection.java:

java
Copy
Edit
con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/studentgradingsystem", 
    "your_mysql_user", 
    "your_mysql_password"
);
💻 How to Run
Open the project in an IDE (e.g., Eclipse or IntelliJ).

Ensure JavaFX libraries are added to the classpath.

Run the Main.java file.

Register an account and start using the system.

📌 Future Improvements
Hashing and salting for passwords

Email verification or reset

User types (Admin/Student)

Export to PDF or Excel

Notification system for deadlines

👨‍💻 Author
Raphael Sanjuan
Developed after ~1.5 month of Java experience.

📃 License
This project is open-source and free to use.

---

Let me know if you also want a `README.md` file version that you can directly upload or push to GitHub.
