### The Back-End
The back-end is all about security and connecting the front-end to database data and actions.

1. Managing user access with Spring Security
- You have to restrict unauthorized users from accessing pages other than the login and signup pages. To do this, you must create a security configuration class that extends the `WebSecurityConfigurerAdapter` class from Spring. Place this class in a package reserved for security and configuration. Often this package is called `security` or `config`.
- Spring Boot has built-in support for handling calls to the `/login` and `/logout` endpoints. You have to use the security configuration to override the default login page with one of your own, discussed in the front-end section.
- You also need to implement a custom `AuthenticationProvider` which authorizes user logins by matching their credentials against those stored in the database.  

