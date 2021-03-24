# Flashcards app

Implemented using Play Framework with Scala and the Google spreasheet API 

## Run
```
    sbt clean           # Clean existing build artifacts

    sbt stage           # Build your application from your project’s source directory

    sbt run             # Run both backend and frontend builds in watch mode

    sbt dist            # Build both backend and frontend sources into a single distribution artifact

    sbt test            # Run both backend and frontend unit tests
```

## Requests
http --verbose GET http://localhost:9000/api/flashcards

## Documentation
- https://developers.google.com/sheets/api/samples/rowcolumn

## Todo

-[ ] Configure docker container
-[ ] Follow tips (UI)
    - https://javascript.plainenglish.io/5-dry-principles-to-follow-in-react-6ca0405986a9
    - https://itnext.io/simple-tips-for-writing-clean-react-components-c3facbf6680e
