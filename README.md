# Blockbuster

An Android application to browse movie information from TheMovieDB.org

The goal of this project is to provide a demonstration about how to write an effective Android application using a series of popular libraries and programming concepts (reactive programming, MVVM design, clean architecture, dependency injection). 

##### Libraries used
RxJava, RxAndroid, Retrofit, Dagger 2, Android Databinder, Facebook Fresco

###### TODO
- Step 0 - app design/architecture:
  - ~~Domain-driven-design: create model first and keep separated from UI and business logic~~
  - ~~Clean architecture (different layers for repository, presentation and domain)~~
  - ~~MVVM for presentation layer (Android databinder)~~
  - ~~Reactive interface (RxJava)~~
- Step 1 - first working solution:
  - ~~Connect to TheMovieDB.org API (query, search movie, etc.)~~
  - ~~Show results in a grid of posters~~
  - ~~Show basic movie details (title, overview) in a new activity when the user select a poster~~
  - ~~Include search field in the toolbar~~
  - ~~Hide the toolbar when the user is scrolling~~
- Step 2 - code review:
  - Clean code (short classes, meaningful names, separation of concerns, easy to understand, etc.)
  - ~~Implement dependency injection (Dagger 2) - improve testability and flexibility through inversion of control~~
  - Check design choices were well adopted
- Step 3 - improve UI/UX quality:
  - Improve movie details layout
  - Show movie title in the gallery when the poster cover is not available
- Step 4 - tests:
  - Write automatic tests based on requirements
  - Performance profiling to investigate potential issues (memory leaks, too much work on UI thread, etc.)
  - UI test on different devices
- Step 5 - new features:
  - Add movie filter
  - New animations
  - Check movies as "seen" or "favourite" 
  - Include crash analytics
  - TBD
