# VLRickMorty

The Rick and Morty Android application consists of two screens. During the application development process, the endpoints provided by https://rickandmortyapi.com were taken into consideration. According to documentation of Ricky and Morty API, the following 3 API endpoints are accessed with query and path parameters and information is presented.

* https://rickandmortyapi.com/api/character/
* https://rickandmortyapi.com/api/episode/
* https://rickandmortyapi.com/api/location/


- On the first screen, the characters who played in the Rick and Morty series are listed. This list includes the type, status, gender, and last known location of each character.
- Pagination is used for lists, and when the user clicks the Next button, the character list on the next page is displayed to the user, and when the user clicks the Previous button, the character list on the previous page is displayed to the user.
- When the user clicks on the character, the second screen, the detail page, is shown to the user. On the detail screen, the status, gender, species, origin and species properties of the character are displayed. In addition, the last known location of the character and the information of how many people reside in this location, the first episode the character played and the information on how many episodes the character played in total are also included in the detail screen.
- The application has a cache system and shows the user by keeping the previously accessed information in its memory even when there is no internet.
- Animation has been added as a frame to the character photos.
- Obfuscation has also been done to ensure code confidentiality.



https://user-images.githubusercontent.com/60930674/175447445-dd335ba3-d927-4198-b7e1-120c62494594.mp4

Libraries
- RecyclerView
- Glide
- Retrofit
- OkHTTP
- ToolTipManager
- CircleImageView


