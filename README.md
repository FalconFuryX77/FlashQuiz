# FlashQuiz

A desktop flashcard application built in Java Swing that helps users create, organize, revise, and test themselves using interactive flashcards.

FlashQuiz was developed as an Object-Oriented Programming project to demonstrate Java GUI development, file handling, object-oriented design, and event-driven programming. The application supports both subjective and multiple-choice flashcards while providing dedicated Revision and Quiz modes for effective learning. :contentReference[oaicite:2]{index=2}

---

## Features

### Flashcard Management
- Create new flashcards
- Edit existing flashcards
- Delete flashcards
- Persistent local storage
- Import flashcards from external text files

### Multiple Flashcard Types
- Subjective questions
- Multiple Choice Questions (MCQs)
- Custom number of answer choices

### Revision Mode
- Browse flashcards one by one
- Reveal or hide answers
- Navigate using Previous / Next controls
- Review subjective and MCQ flashcards

### Quiz Mode
- Automatically filters MCQ flashcards
- Interactive multiple-choice interface
- Score tracking
- Final performance summary

### Data Persistence
- Saves flashcards locally
- Automatically loads previous flashcards on startup
- Supports importing flashcard collections

---

# Screenshots

> Screenshots are available inside the project report.

- Home Screen
- Flashcard Creation
- Revision Mode
- Quiz Mode
- Import Flashcards
- Quiz Results

---

# Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java |
| GUI | Java Swing |
| Layout Managers | BorderLayout, BoxLayout, FlowLayout |
| Storage | Local Text File |
| IDE | IntelliJ IDEA / Eclipse (Compatible) |

---

# Project Structure

```
FlashQuiz
│
├── FlashQuizApp.java
├── Flashcards.txt
└── README.md
```

Internally, the application consists of:

```
FlashQuizApp
│
├── Flashcard
├── FlashcardCreationModeFrame
├── RevisionModeFrame
└── QuizModeFrame
```

Each class has a single responsibility:

- **FlashQuizApp**
  - Application entry point
  - Creates the home screen
  - Launches each module

- **Flashcard**
  - Stores question and answer data
  - Encapsulates flashcard information

- **FlashcardCreationModeFrame**
  - CRUD operations
  - Import functionality
  - Persistent storage

- **RevisionModeFrame**
  - Sequential review
  - Answer reveal
  - Navigation controls

- **QuizModeFrame**
  - MCQ parser
  - Quiz engine
  - Score calculation

---

# How It Works

FlashQuiz stores every flashcard inside a plain text file using a custom format.

Example:

```text
Q: What is Java?

A: Object Oriented Programming Language
```

MCQ flashcards are represented using lightweight XML-like tags.

```text
Q: What is Java?

A:
<option>C++</option>
<option>Python</option>
<option>Java</option>
<correct>Java</correct>
```

The application parses these tags during runtime to generate quiz questions dynamically.

---

# Installation

## Clone Repository

```bash
git clone https://github.com/FalconFuryX77/FlashQuiz.git
```

## Compile

```bash
javac FlashQuizApp.java
```

## Run

```bash
java FlashQuizApp
```

---

# Usage

## Flashcard Creation

- Create new flashcards
- Choose Subjective or MCQ
- Save automatically
- Edit answers
- Delete unwanted cards
- Import existing flashcard collections

---

## Revision Mode

Revision Mode displays one flashcard at a time.

Users can:

- View question
- Reveal answer
- Hide answer
- Navigate forward/backward

---

## Quiz Mode

Quiz Mode automatically extracts only MCQ flashcards.

Workflow:

1. Read question
2. Select answer
3. Continue
4. View final score

The application keeps track of:

- Total Questions
- Correct Answers
- Wrong Answers

---

# Object-Oriented Design

The project demonstrates several OOP concepts:

## Encapsulation

The `Flashcard` class stores question and answer fields using private variables with getters and setters.

## Abstraction

Different application modes hide implementation details behind independent GUI classes.

## Modularity

Each module handles a single responsibility:

- Creation
- Revision
- Quiz

making the project easier to maintain and extend.

---

# File Handling

The application uses Java File I/O for persistent storage.

Operations include:

- File creation
- Reading flashcards
- Saving flashcards
- Importing external files
- Parsing custom file format

---

# Key Learning Outcomes

This project demonstrates practical implementation of:

- Java Swing GUI development
- Event-driven programming
- File handling
- Object-Oriented Programming
- Custom data parsing
- Dynamic UI generation
- MVC-inspired separation of responsibilities

---

# Future Improvements

Potential enhancements include:

- SQLite/MySQL integration
- Search and filtering
- Categories and tags
- Timed quizzes
- Randomized quiz mode
- Progress tracking
- Statistics dashboard
- Dark mode
- Export functionality
- Spaced repetition algorithm
- User profiles
- Better UI styling using JavaFX

---

# Course Information

Developed as the final Object-Oriented Programming project for B.Tech Information Technology at MPSTME, NMIMS during Semester IV (2024–25). :contentReference[oaicite:3]{index=3}

---

# Authors

- Praneel Paliwal
- Meet Maheshwari

---

# License

This project is intended for educational purposes.
