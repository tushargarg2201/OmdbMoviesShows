# OmdbMoviesShows

1. This application used below rest api to retrieve the data https://www.omdbapi.com
2. Shown the data in the vertical recycler view list.
3. If any movie or show if favorited by pressing heart icon, it's been displayed in the horizontal list.
Horizontal list is scrollable.
4. We can add and remove favorite either from vertical or horizontal list.
5. If we click it on any movie or tv shows then it's goes to Movie detail page. We can also do favorite and unfavorite
from detail page as well.
6. By default Search start with "Friends" key.


#Architecture which followed to develop this application
1. I used MVVM architecutre for developing this application. Used ModelView, LiveData and Repository pattern
2. Network call is been made through Retrofit with combination of RXJava.
3. Also included a caching on a retrofit level. Also set a timeout for retrofit to 10 seconds.
4. Room database is used for storing the data in the database.
5. Complete application's code is written in Kotlin.