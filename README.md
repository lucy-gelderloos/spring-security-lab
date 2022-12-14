# Code Fellowship

## To run

In codefellowship folder, run './gradlew build', then './gradlew bootRun'. By default, the site will run on port 8080.

## Lab 16 Feature Tasks

- [x] The site should have a login page with a link to a signup page
- [x] The site should have an AppUser class with username, password, first name, last name, birthdate, and bio
- [x] Users should be able to create a new user on the signup page and save that user into the database
- [x] When a user is created, their password should be hashed before being saved to the database
- [x] After a user signs up, they should be automatically logged in and redirected to the home page
- [x] Users should be able to log in to the app (**Note:** uses can log in, but for some reason, logging in redirects to "/css/styles.css". After logging in, go to localhost:8080 to see the logged-in view.)
- [x] The site should have a home page with information about the site. If the user is not logged in, it should show a login link and a signup link. If the user is logged in, it should show a logout link.
- [x] Users should be able to log out and be redirected to the home page
- [x] When a user is logged in, their username should be displayed on each page
- [x] The home page, login, and signup pages should be accessible to users who are not logged in
- [x] The site should use templates to display its information
- [x] The site should be well-styled and attractive (**Note:** static content is not being served correctly, so the styling doesn't show up until the user is logged in.)

## Lab 17 Feature Tasks

- [ ] Upon logging in, the user should be directed to a profile page with their user details and a profile picture (default image for now)
- [x] All logged-in users should be able to see all other users' details at a route similar to `/users/{id}`
- [x] Create a Post entity with properties of author, post body, and timestamp
- [x] A logged-in user should be able to create a post, which should be associated with that user
- [ ] Each user's posts should be visible on their profile page
- [x] The site should use reusable fragments for its information
- [ ] The site should have a non-whitelabel error page that gives the error code and a brief message
- [x] Stretch: Users should be able to edit their own profiles, but not other users' profiles
- [ ] Stretch: An admin user should be able to edit all users' profiles
