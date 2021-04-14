package stepdefs;
import common.Headers;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import utilities.Logger;
import static common.Constants.*;


public class APITest {


    SoftAssert softAssert = new SoftAssert();


    @Given("^user invoke the api for users$")
   public void getUser(){
        Response response = Headers.GetHeader(USER_URL);
        List<String> jsonResponse = response.jsonPath().getList("username");
        List<String> jsonResponse1 = response.jsonPath().getList("id");
        List<String> jsonResponse2 = response.jsonPath().getList("name");
        Logger.Log(jsonResponse);
        Logger.Log(String.valueOf(jsonResponse1));
        Logger.Log(String.valueOf(jsonResponse2));
    }

    @When("^the user pass the user name$")
    public Response submitUsername(){
        Response response1 = Headers.GetHeader(USER_NAME_URL);
        return response1;
    }

    @And("^the user will get 200 response$")
    public void validate200(){
        softAssert.assertTrue(String.valueOf(submitUsername().getStatusCode()).equals("200"));
        softAssert.assertAll();
    }

    @Then("^user will see correct user details$")
    public void validateUserDetails(){
        softAssert.assertEquals(submitUsername().getBody().jsonPath().get("username[0]"), "Delphine", "success message not displayed");
        softAssert.assertEquals(submitUsername().getBody().jsonPath().get("name[0]"), "Glenna Reichert", "success message not displayed");
        softAssert.assertAll();

    }

    @Given("^user invoke the post api with the user id$")
    public  int getAllPosts() {

        Response response_users = Headers.GetHeader(USER_NAME_URL);
        int id = response_users.getBody().jsonPath().get("id[0]");
        return id;

    }

    @When("^the user pass the user id$")
    public Response submitUserid(){
        Response response = Headers.GetHeader(POSTS_URL + getAllPosts());
        return response;
    }

    @And("^the user will get 200 response in Posts$")
    public void validate200InPostAPI(){
        softAssert.assertTrue(String.valueOf(submitUserid().getStatusCode()).equals("200"));
        softAssert.assertAll();
    }


    @Then("^user will see all the posts that user has published$")
    public List<Integer>  validatePostDetails(){
        List<Integer> ids = submitUserid().jsonPath().getList("id");
        softAssert.assertEquals(ids.size(), 10, "success message not displayed");
        softAssert.assertAll();
        return ids;

    }

    @Given("^user invoke the comments api with the user id$")
    public List<Integer> getAllCommentsIdsForEachPosts() {

        List<Integer> ids = validatePostDetails();
        return ids;
    }

    @When("^the user pass the post ids$")
    public void submitPostIds() {
        for (int i = 0; i < getAllCommentsIdsForEachPosts().size(); i++) {

            Response response1 = Headers.GetHeader(COMMENTS_URL + getAllCommentsIdsForEachPosts().get(i));

        }
    }



    @Then("^user will see all the comments that user has published for the posts$")
        public void validateComments() {
            List<String> emailCount = new ArrayList<>();
            for (int i = 0; i < getAllCommentsIdsForEachPosts().size(); i++) {


                    Response response1 = Headers.GetHeader(COMMENTS_URL + getAllCommentsIdsForEachPosts().get(i));


                softAssert.assertTrue(String.valueOf(response1.getStatusCode()).equals("200"));
                softAssert.assertAll();
                List<String> email = response1.jsonPath().getList("email");
                Logger.Log(email);

                for (int y = 0; y < email.size(); y++) {

                    emailCount.add(email.get(y));

                }
            }
            softAssert.assertEquals(emailCount.size(), 50, "success message not displayed");
            softAssert.assertAll();
        }

    @And("^user will check the \\\"(.*)\\\"$")
    public  boolean isValidEmailAddress(String emails) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emails.matches(regex);

    }

    @Then("^user will see all the email in the comments of the posts of a particular user are valid emails$")
    public void validateEmails() {

        for (int i = 0; i < getAllCommentsIdsForEachPosts().size(); i++) {


            Response response1 = Headers.GetHeader(COMMENTS_URL + getAllCommentsIdsForEachPosts().get(i));


            softAssert.assertTrue(String.valueOf(response1.getStatusCode()).equals("200"));
            softAssert.assertAll();
            List<String> email = response1.jsonPath().getList("email");


            for (int y = 0; y < email.size(); y++) {


                softAssert.assertTrue(isValidEmailAddress(email.get(y)));
                softAssert.assertAll();
            }
        }


    }

}
