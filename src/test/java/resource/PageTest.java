package resource;

public interface PageTest {

    String addElementButton = "//button[@onclick='addElement()']";
    String deleteButton = "//button[@onclick='deleteElement()']";
    String brokenImagesList = "//*[@id=\"content\"]/div/img";
    String dropdown = "//*[@id=\"dropdown\"]";
    String selectedOption = "//*[@selected=\"selected\"]";
    String checkbox1 = "//*[@id=\"checkboxes\"]/input[1]";
    String username = "//*[@id=\"username\"]";
    String password = "//*[@id=\"password\"]";
    String loginButton = "//*[@id=\"login\"]/button";
    String errorLogin = "//*[@id=\"flash\"]";
}
