Spring + Cassandra + GraphQL App

Step 1:
Create GraphQL API service that allow user CRUD operations. User record fields: email, password, first name, last name, avatar, role. Use Spring Boot, Cassandra (I recommend to launch Cassandra in the Docker container), GraphQL (I recomment to use https://github.com/leangen/graphql-spqr  for easier GraphQL endpoints declartion).

Step 2: Add authentication based on JWT. So only users with the role "ADMIN" could user this API.

Step 3: (optional) Add UI application built with Angular. So user with ADMIN role could manage users via UI.
