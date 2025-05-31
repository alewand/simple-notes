# SimpleNotes

Write, save and remember! 
Simple web application for creating and storing notes.

## Table of Contents

- [About The Project](#about-the-project)
- [Features](#features)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [How To Run](#how-to-run)
- [Gallery](#gallery)

## About The Project

SimpleNotes - application for managing notes wrote in purpose to learn something about Spring Boot and it's security side. 

Create, edit and delete your notes as you like.

Project written in Polish language.

## Features

- User authenticaction with JWT short-term access tokens and long-term refresh tokens.
- Server-side users actions logging.
- User registering and logging in.
- Changing your account details (nickname, email).
- Basic actions you can do with your account (change password, log out, log out from all devices and delete account entirerly).
- Client-side alert system (alerts after successful and unsuccessful actions).
- Creating, viewing, editing and deleting notes.
- Mobile devices adapting UI.

## Technologies

### Client-Side:
- ![TypeScript](https://img.shields.io/badge/TYPESCRIPT-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
- ![React](https://img.shields.io/badge/REACT-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
- ![Axios](https://img.shields.io/badge/AXIOS-5A29E4?style=for-the-badge&logo=axios&logoColor=white)
- ![Tailwind CSS](https://img.shields.io/badge/TAILWIND_CSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)
- ![Vite](https://img.shields.io/badge/VITE-646CFF?style=for-the-badge&logo=vite&logoColor=white)

### Server-Side:
- ![Java](https://img.shields.io/badge/JAVA-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/SPRING_BOOT-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- ![Hibernate](https://img.shields.io/badge/HIBERNATE-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
- ![Gradle](https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Database:
- ![PostgreSQL](https://img.shields.io/badge/POSTGRESQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)

### Others:
- ![Docker](https://img.shields.io/badge/DOCKER-2496ED?style=for-the-badge&logo=docker&logoColor=white)

[⬆️ Go Back Up ⬆️](#table-of-contents)

## Requirements

Project is fully dockerized so the only requirements in order to run it is Docker, make sure you have it installed.

## How To Run

After cloning repository:

1. Copy .env.example file and fill it accordingly.
```bash
cp .env.example .env
``` 

2. Then, build and start the app by running the following command:
```bash
docker compose up
```

3. Go on `http://localhost:5173/` in your browser and enjoy.

[⬆️ Go Back Up ⬆️](#table-of-contents)

## Gallery

Some screenshots to present UI.

**Main Page**

![Main Page](https://i.imgur.com/g0pIwW1.jpeg)

**Register Page**

![Register Page](https://i.imgur.com/FQzY3h8.jpeg)


**Login Page**

![Login Page](https://i.imgur.com/asMEYi5.jpeg)

**Dashboard Page**

![Dashboard Page](https://i.imgur.com/sUAnaon.jpeg)

**User Details Page**

![User Details Page](https://i.imgur.com/VrDymbc.jpeg)

**Notes Page**

![Notes Page](https://i.imgur.com/vI9Y7qC.png)

**Note View && Editing Page**

![Note View Page](https://i.imgur.com/MiDvBZG.png)

![Note Editing Page](https://i.imgur.com/Z54Mh8c.png)

[⬆️ Go Back Up ⬆️](#table-of-contents)
