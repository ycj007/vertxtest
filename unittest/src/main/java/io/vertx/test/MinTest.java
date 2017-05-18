package io.vertx.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
public class MinTest {
    public static void main(String[] args) {

        minTest();
    }


    public static  void asynTest(){
        TestSuite suite = TestSuite.create("the_test_suite");
        suite.test("my_test_case", context -> {
            Async async = context.async();

           /* eventBus.consumer("the-address", msg -> {
                (2)
                async.complete();
            });
            (1)*/
        });

    }



    private static  void executeBeforeAndAfter(){


        TestSuite suite = TestSuite.create("the_test_suite");
        suite.beforeEach(context -> {
            System.out.println(context.assertNull(null));
        }).test("my_test_case_1", context -> {
            // Test 1
            String s = "value";
            context.assertEquals("value", s);
        }).test("my_test_case_2", context -> {
            // Test 2
            String s = "value";
            context.assertEquals("value", s);
        }).test("my_test_case_3", context -> {
            // Test 3
            String s = "value";
            context.assertEquals("value", s);
        }).afterEach(context -> {
            // Test case cleanup
            System.out.println("cleanup");
        });
        suite.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));
    }
    private  static  void minTest(){
        TestSuite suite = TestSuite.create("the_test_suite");
        suite.test("my_test_case", context -> {

                // Assert that 0.1 is equals to 0.2 +/- 0.5

                context.assertInRange(0.1, 1.0, 0.5);
            });

        suite.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));

    }
}
