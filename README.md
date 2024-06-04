# MovieListingApp
The MovieListingApp is an Android application project that showcases modern development practices and my developmental skills. Built using Kotlin, MVVM architecture, and dependency injection, the app features robust UI and unit testing. The project uses a local JSON file stored in the assets folder as its data source, enabling functionalities such as listing movies with pagination and search. Leveraging the latest Android development tools and practices, this app ensures a robust and maintainable codebase.

[AppVideo.webm](https://github.com/venkatselva8/MovieListing/assets/15832831/cd3c475f-6574-42ec-a4b5-54f7d606ca68)

## Table of Contents
- [Technologies Used](#technologies-used)
- [Project Architecture](#project-architecture)
- [Data Source](#data-source)
- [UI Components](#ui-components)
- [Unit and UI Testing](#unit-and-ui-testing)

## Technologies Used
- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Koin
- **Image Loading**: Glide
- **JSON Parsing**: Moshi
- **Testing**: JUnit, MockK, Espresso

## Project Architecture
The application follows the MVVM architecture pattern, which helps in separating concerns and ensuring that the UI components are loosely coupled with the business logic. The primary components include:

- **View (Activities and Fragments)**: Handles the UI and user interactions.
- **ViewModel**: Provides data to the UI and survives configuration changes.
- **Model (Repository)**: Manages data operations and abstracts the data source.

## Data Source
Unlike traditional apps that fetch data from remote servers or APIs, the MovieListingApp uses a local JSON file stored in the assets folder as the data source.

The JSON file contains a list of movies, which is parsed using Moshi and provided to the ViewModel through the repository layer.

## UI Components
The application consists of two main fragments:

1. **ListingFragment**: Displays a list of movies using a RecyclerView with pagination.
2. **SearchFragment**: Displays search results based on the user's query.

### MainActivity
The `MainActivity` serves as the host for the fragments and handles the toolbar and search functionality.

### Fragments
- **ListingFragment**: Observes movie data from the ViewModel and updates the RecyclerView accordingly.
- **SearchFragment**: Observes search results and displays them.

### ViewModel
The `MovieViewModel` manages the data for both listing and search functionalities. It fetches data from the repository and exposes it to the UI via LiveData.

## Unit and UI Testing
The project includes comprehensive unit and UI tests to ensure the robustness of the application.

- **Unit Tests**: 
  - ViewModel tests using JUnit, MockK.
  - Repository tests to ensure correct data fetching and processing.

- **UI Tests**: 
  - Fragment tests using Espresso.
  - Integration tests to verify the navigation and data flow between UI components.
