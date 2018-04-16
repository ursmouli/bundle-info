package app.lambda;

public class TestModel {
    private String message;
    private String another;

    public TestModel() {

    }

    public TestModel(String message) {
        this.message = message;
    }

    public TestModel(String message, String another) {
        this.message = message;
        this.another = another;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnother() {
        return another;
    }

    public void setAnother(String another) {
        this.another = another;
    }
}
