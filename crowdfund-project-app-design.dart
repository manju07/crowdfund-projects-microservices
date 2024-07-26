

Functional Requirements:
  - Innovator able to post the project and ask for contribution to their projects and browse their projects.
  - Donors able to contribute to innovator projects.
  - Donation on a project reaches requested amount, the project is archived and donation is
  transferred to innovator.


Flows:

  - Roles: ADMIN, INNOVATOR, DONOR

  - Admin:
    - Login
    - Manage innovator accounts.


  - INNOVATOR:
    - Login
    - Post project
    - View Previous Projects


  - DONOR:
    - Login
    - View Projects
    - Contribute to projects



System APIs:
- post - signup /donor - public and /innovator - private
- post - /login
- post - /project
- get - /project/{id}, /project
- post - /contribute  

  - check the status of project 
  - create an entry in contribute table 
  - add amount to received_amount
    - debit money from donor wallet
    - credit money to admin wallet

  - if received_amount >= required_amount 
      - change the status to completed

Data Models:
- user
  - id, email, fnanme, lname, gender, username, password, phone, address_id, is_deleted, is_enabled,created_by, created_time, updated_by, updated_time

- role
  - id, name

- user_role
  - user_id, role_id

- address
  - id, area. city, country, pincode, state, created_time,  updated_time

- project
  - id, name, description, required_amount, received_amount, status, user_id, created_by, created_time, updated_by, updated_time

- wallet
  - id, user_id, balance, created_by, created_time, updated_by, updated_time

- transaction
  - id, wallet_id,  project_id, amount, transaction_id, transaction_type, payment_status, created_by, created_time, updated_by, updated_time


transaction_type: credit, debit

Project_Status
  - IN_PROGRESS -> COMPLETED -> ARCHIVED

Services:
 - cloud-config-server
 - eureka-naming-server
 - oauth2-authorization-server
 - user-service
 - project-service
 - zuul-api-gateway
 - cron-job:
  - Running every 1 hour
  - transfer money from admin wallet to innovator those projects are already received required money.
    - get all projects with COMPLETED states,
    - debit money from admin wallet
    - credit money to innovator wallet
    - mark project state to archived.

- swagger - documentation






