# Udacity - Movie project

Movie project was devided into 2 stages:

### First stage:
* Fetch data from the Internet with theMovieDB API.
* Use adapters and custom list layouts to populate list views.
* Incorporate libraries to simplify the amount of code you need to write

### Second stage:
* Allow users to view and play trailers ( either in the youtube app or a web browser).
* Allow users to read reviews of a selected movie.
* Allow users to mark a movie as a favorite in the details view by tapping a button(star).
* Create a database to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
* Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.

## Project requirements

### User Interface Layout
* UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
* Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
* UI contains a screen for displaying the details for a selected movie.
* Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
* Movie Details layout contains a section for displaying trailer videos and user reviews.

### User Interface Function
* When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
* When a movie poster thumbnail is selected, the movie details screen is launched.
* When a trailer is selected, app uses an Intent to launch the trailer.
* In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.

### Network API Implementation
* In a background thread, app queries the `/movie/popular` or `/movie/top_rated` API for the sort criteria specified in the settings menu.
* App requests for related videos for a selected movie via the `/movie/{id}/videos` endpoint in a background thread and displays those details when the user selects a movie.
* App requests for user reviews for a selected movie via the `/movie/{id}/reviews` endpoint in a background thread and displays those details when the user selects a movie.

### Data Persistence
* The titles and IDs of the user’s favorite movies are stored in a native SQLite database and exposed via a ContentProvider
OR stored using Room.
* Data is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.

### Android Architecture Components
* If Room is used, database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.
* If Room is used, database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.

### User Interface Samples
<details>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-favorite2.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-favorite.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-detail.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-detail-favorite.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-detail-reviews.png"/>
  <img src="https://github.com/PaloPodstreleny/Udacity-movies/blob/master/ReadmeImages/movie-detail-video.png"/>
</detail>

### Run appplication
In order to run the app and get same result as showed in the User Interface Samples section you have to provie <a href="https://www.themoviedb.org/documentation/api">`API_KEY`</a> which is situated in the `ApiKey` class.

