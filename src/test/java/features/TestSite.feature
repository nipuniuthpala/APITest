Feature: Test the site after login and order item and then update personal information.

  Scenario: Validate user can get the  correct user details
    Given user invoke the api for users
    When the user pass the user name
    And the user will get 200 response
    Then user will see correct user details


  Scenario: Validate the user can get all the posts that a particular user have published
    Given user invoke the post api with the user id
    When the user pass the user id
    And the user will get 200 response in Posts
    Then user will see all the posts that user has published



  Scenario: Validate the user can get all the comments for the posts and validate comment count for the particular user
    Given user invoke the comments api with the user id
    When the user pass the post ids
    Then user will see all the comments that user has published for the posts


  Scenario Outline: Validate the user can check emails in comments and validate those emails are in correct format
    Given user invoke the comments api with the user id
    When the user pass the post ids
    And user will check the "<emails>"
    Then user will see all the email in the comments of the posts of a particular user are valid emails
    Examples:
      | emails |
      |Vella.Mayer@colten.net|
      |Caleb_Dach@kathleen.us|
      |Patience_Bahringer@dameon.biz|
      |Destinee.Simonis@jose.tv|
      |Keshaun@brown.biz |

