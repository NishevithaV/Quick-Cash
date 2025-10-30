Ezekiel Loty User Story 4
Role Selection and Dashboard Display
Open
  Issue created 2 weeks ago by Olamiposi Olaiya
US - As a user with dual roles (Employer and Employee), I want to switch my role from the settings page so that I can access features and dashboards relevant to my selected role while ensuring the switch is secure and verified.

Acceptance Criteria:

AT-1: Given that the user is logged in, when they open the settings page, then they should see a “Switch Role” button.

AT-2: Given that the user clicks the “Switch Role” button, when the action is triggered, then the app should navigate to a new screen displaying: “You are now switching from [current_role] to [new_role]. Input your email to proceed.”

AT-3: Given that the user inputs an email, when they submit, then the system should verify if the entered email matches the currently logged-in user’s email.

AT-4: Given that the email does not match the logged-in user’s email, when the verification fails, then a toast message should display: “Email does not match your current logged-in account.”

AT-5: Given that the email matches the logged-in user’s email, when verification succeeds, then the system should update the user’s role in Firebase.

AT-6: Given a successful role update, when the process completes, then a toast message should confirm: “Role switched successfully.”

AT-7: Given that the role has been switched, when the user returns to the dashboard, then the dashboard should dynamically refresh and load the features associated with the new role.

Engineering Tasks:

Implement a “Switch Role” button in the settings page.

Create a new “Role Switch Confirmation” screen showing the message: “You are now switching from [current_role] to [new_role]. Input your email to proceed.”. For example, “You are now switching from Employer to Employee. Input your email to proceed.

Add an input field for the user to enter their email and a “Confirm Switch” button.

Retrieve the currently logged-in user’s email from Firebase Authentication.

Compare the input email with the logged-in user’s email.

If the emails match, update the user’s role in Firebase.

If the emails do not match, display a toast message explaining the error.

Redirect the user back to the updated dashboard and display a toast confirming success.

Conduct UI testing to ensure smooth navigation, accurate email validation, and correct dashboard updates.

Write unit tests to validate email verification, Firebase role updates, and toast notifications.