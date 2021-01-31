# Git-Commits

# Project requirements document :

1. Create a free GitHub account (if you do not already have one) 
2. Create a new GitHub repository 
3. Create a mobile app using Java, Swift or React Native that accomplishes the following: 
	a) Connects to the GitHub API; 
	b) Uses that API to retrieve the most recent commits (at least 25) Note: if you are using Android, try to use Dagger 
	c) Displays those commits in a list with the author, commit hash, and commit message. 
As you create this app, please make frequent commits of your work in progress because we want to be able to follow the process you went through in creating the app. 
We would like you to provide the link to your public GitHub repo.  
Please include any unit tests you performed on this app.

# Description

1. This project uses the Github api to get the latest commits on a repository.
2. The name of the repository and it's owner are taken as inputs from the user.
3. After validation the api is triggered to fetch the list of commits for the api response.
4. The details about the commits, such as, the commit author's name, profile picture, etc. are shown, along with the commit hash, and the date of the commit.
5. The state of the network is validated before trying to trigger the network request.
6. A UI state machine has been imitated to control the ui elements such as data fetching in progress, bad input details, network availability, etc.

# Implementation details

1. Dagger 2 has been used for dependency injection.
2. Coroutines have been used for handling asynchronous network requests.
3. The application is built using the MVVM architecture.
4. Retrofit 2 is used for calling the network APIs and get the results.
5. Gson converter factory has been used to populate the models with the API response data.
6. Picasso has been used to show the profile pictures of the commit authors.(Was intending to use Glide but had trouble with injecting the Glide objects into the adapter)
7. MockK is the testing framework to write unit tests.(Couldn't write more tests due to lack of time)
8. Timber has been used for logging.
9. Databinding and Viewbinding has been used.

# Future Improvements

1. Pagination can be added to fetch more commit details, if required.
2. UI can be made more interactive by showing individual commit details and particulars in additional screens.
3. More tests can be added.
4. The user input can be managed with databinding.
