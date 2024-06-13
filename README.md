## List and details

This is a typical test task that employers often request - show list on a 1st screen and by clicking
on any item show 2nd screen with detailed information.

This app has been created for testing of difference between RecyclerView (XML View) and LazyColumn (
Jetpack Compose). I've never tested this performance difference by myself, I only heard from Google
that Compose much better than XML View.

All the results I've got I copied into directory `/benchmark-results` (included *.perfetto-trace
files)

[Link to the article on dev.to](https://dev.to/mardsoul/recyclerview-vs-lazycolumn-5g6g)

### Application description

There are 5 modules in the app:

- app-compose - only UI with Composable functions
- app-view - only UI with Fragments and XML Layouts
- share - for everything else (from Retrofit interface to ViewModels)
- benchmark-compose - testing Compose build
- benchmark-view - testing View build

The app works with [GitHub REST API](https://docs.github.com/en/rest)

#### What the application does?

1. Get list of GitHub users (request: https://api.github.com/users)
2. By clicking on Icon button for expanded load list of repositories (
   request: [https://api.github.com/users/{userLogin}/repos (for example: defunkt)](https://api.github.com/users/defunkt/repos))
3. By clicking on any GitHub user show screen with detailed information (
   request: [https://api.github.com/user/{userId} (for example: 2)](https://api.github.com/user/2))

#### Testing scenarios:

1. `fun sturtup()` - Cold startup
2. `fun scrollReposList()` - Get list from internet and scroll down
3. `fun clickOnExpandedButton()` - Get list from internet, click on expanded button, wait for
   loading, click on expanded button
4. `clickToDetails()` - Get list from internet, click on item, wait for loading, scroll down

#### What data do I want to compare:

1. Speed of cold boot
2. Speed of rendering list items

#### Used

- Kotlin
- Hilt
- Navigation
- Retrofit
- Coil
- Benchmark

<img src="screenshots%2FScreenshot_20240614_012853.png" width=25% height=25%>
