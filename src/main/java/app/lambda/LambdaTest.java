package app.lambda;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

import javax.sql.rowset.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LambdaTest {
    public void go() {
        TestInterface testInterface = new TestInterface() {
            public void prepareMessage(TestModel testModel) {
                testModel.setMessage("New Message");
            }
        };

        printMessage(testInterface);
    }

    public void lamda() {
        TestInterface testInterface = (TestModel testModel) -> {
            testModel.setMessage("New Message Lambda");
            testModel.setAnother("");
        };
        printMessage(testInterface);
    }

    public void printMessage(TestInterface testInterface) {
        TestModel testModel = new TestModel();
        testModel.setMessage("Old Message");
        testInterface.prepareMessage(testModel);

        System.out.println(testModel.getMessage());

        List<String> emailList = new ArrayList<>();
        emailList.add("mouli@mail.com");
        emailList.add("krishna@mail.com");

        List<TestModel> modelList = myMethod(emailList, TestModel::new);

        modelList.forEach(model -> System.out.println(model.getMessage()));
    }

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();
        lambdaTest.go();
        lambdaTest.lamda();
    }

    private static List<TestModel> modelList = new ArrayList<>();

    public List<TestModel> myMethod(List<String> emailList, Function<String, TestModel> fn) {
        List<TestModel> testModelList = new ArrayList<>();
        emailList.forEach(email -> testModelList.add(fn.apply(email)));
        return testModelList;
    }

}
