Feature: User registration

  As a user
  I want to create an account
  So that I can join the Basketball England group

  Scenario Outline: Skapa användare – olika valideringsfall
    Given I open webpage "file:///C:/Users/soire/Downloads/Register%20(1)/Register.html"
    When I enter date of birth "<dateOfBirth>"
    And I enter first name "<firstName>"
    And I enter last name "<lastName>"
    And I enter email "<email>"
    And I confirm email "<confirmEmail>"
    And I enter password "<password>"
    And I confirm password "<confirmPassword>"
    And I set terms and conditions to "<termsAccepted>"
    And I submit the registration form
    Then the result should be "<expectedResult>"



    Examples:
      | dateOfBirth | firstName | lastName | email         | confirmEmail   | password | confirmPassword | termsAccepted | expectedResult           |
      | 2000-01-01  | Emily     | Lin      | emily@test.se | emily@test.se  | 12345    | 12345           | true          | account created          |
      | 2000-01-01  | Emily     |          | emily@test.se | emily@test.se  | 12345    | 12345           | true          | missing last name error  |
      | 2000-01-01  | Emily     | Lin      | emily@test.se | emily@test.se  | 12345    | 54321           | true          | password mismatch error  |
      | 2000-01-01  | Emily     | Lin      | emily@test.se | emily@test.se  | 12345    | 12345           | false         | terms not accepted error |

