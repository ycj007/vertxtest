package io.vertx.RXjava;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.ObservableHandler;
import io.vertx.rx.java.RxHelper;
import io.vertx.rxjava.core.WorkerExecutor;
import io.vertx.rxjava.core.http.HttpServer;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaSchedulersHook;

import java.util.concurrent.TimeUnit;

/**
 * Created by yuanchongjie on 2017/5/10.
 */
public class Readstreamsupport {


    public static void helper() {

        Vertx vertx = Vertx.vertx();
        FileSystem fileSystem = vertx.fileSystem();
        fileSystem.open("bar.txt", new OpenOptions(), result -> {

            AsyncFile asyncFile = result.result();

            Observable<Buffer> observable = RxHelper.toObservable(asyncFile);
            observable.forEach(r -> {
                System.out.println(r.toString());
            });


        });


    }

    public static void rxjava() {

        io.vertx.rxjava.core.Vertx vertx = io.vertx.rxjava.core.Vertx.vertx();
        io.vertx.rxjava.core.file.FileSystem fileSystem = vertx.fileSystem();
        fileSystem.open("bar.txt", new OpenOptions(), asyncFileAsyncResult -> {
            io.vertx.rxjava.core.file.AsyncFile asyncFile = asyncFileAsyncResult.result();
            Observable<io.vertx.rxjava.core.buffer.Buffer> observable = asyncFile.toObservable();
            observable.forEach(r -> {
                System.out.println(r.toString());
            });
        });


    }

    public static void handler() {
        Vertx vertx = Vertx.vertx();
        ObservableHandler<Long> observable = RxHelper.observableHandler();
        observable.subscribe(id -> {
            System.out.println("Test.....");
        });
        vertx.setTimer(1000, observable.toHandler());
    }

    public static void asyncResultSupport() {
        Subscriber subscriber = RxHelper.toSubscriber(r -> {

        });

        Observable observable = Observable.just("1", "2");
        observable.subscribe(subscriber);

    }

    public static void obserablefuture() {
        io.vertx.rxjava.core.Vertx vertx = io.vertx.rxjava.core.Vertx.vertx();
        ObservableFuture<HttpServer> observable = RxHelper.observableFuture();
        observable.subscribe(
                server -> {
                    System.out.println("Server is listening");
                    // Server is listening
                },
                failure -> {
                    System.out.println("Server could not start");
                    // Server could not start
                }
        );
        HttpServer httpServer = vertx.createHttpServer(new HttpServerOptions().
                setPort(1234).
                setHost("localhost")
        );
        /*httpServer.requestHandler(ar ->{

            System.out.println("test");
        });*/

        httpServer.requestStream().toObservable().subscribe(result -> {

            System.out.println("ok");

        });

        httpServer.listen(observable.toHandler());
    }


    public static void schedulerSupport() {

        Scheduler scheduler = RxHelper.scheduler(Vertx.vertx());
        Observable<Long> timer = Observable.timer(100, 100, TimeUnit.MILLISECONDS, scheduler);
        timer.subscribe(result -> {
            System.out.println(result.toString());
        });

    }

    private  static  io.vertx.rxjava.core.Vertx vertx = io.vertx.rxjava.core.Vertx.vertx();
    public static io.vertx.rxjava.core.Vertx getVertx() {

        return vertx;
    }

    public static void rxScheduler() {

        RxJavaSchedulersHook hook = io.vertx.rxjava.core.RxHelper.schedulerHook(getVertx());
        RxJavaHooks.setOnIOScheduler(f -> hook.getIOScheduler());
        RxJavaHooks.setOnNewThreadScheduler(f -> hook.getNewThreadScheduler());
        RxJavaHooks.setOnComputationScheduler(f -> hook.getComputationScheduler());
    }

    public static void rxWorkPoolSchedule() {

        io.vertx.core.WorkerExecutor executor = Vertx.vertx().createSharedWorkerExecutor("my-worker-pool");

        WorkerExecutor workerExecutor = WorkerExecutor.newInstance(executor);
        Scheduler scheduler = io.vertx.rxjava.core.RxHelper.scheduler(workerExecutor);
        Observable<Long> timer = Observable.interval(100, 1000, TimeUnit.MILLISECONDS, scheduler);
        timer.subscribe(result -> {
            System.out.println(result);
        });
    }

    public static void jsonUnmarshalling() {
        FileSystem fileSystem = Vertx.vertx().fileSystem();
        fileSystem.open("bar.txt", new OpenOptions(), result -> {
            AsyncFile file = result.result();
            Observable<Buffer> observable = RxHelper.toObservable(file);
            /*observable.forEach(ar->{
                System.out.println(ar);
            });*/
            observable.onErrorReturn(r -> {
                r.printStackTrace();
                return null;
            });
            observable.lift(RxHelper.unmarshaller(MyPojo.class)).subscribe(
                    mypojo -> {
                        System.out.println("success");
                        System.out.println(mypojo.toString());
                    }
            );
        });
    }

    public static void josnUnmarshallingRx() {

        io.vertx.rxjava.core.file.FileSystem fileSystem = io.vertx.rxjava.core.Vertx.vertx().fileSystem();
        fileSystem.open("bar.txt", new OpenOptions(), asyncFileAsyncResult -> {

            io.vertx.rxjava.core.file.AsyncFile file = asyncFileAsyncResult.result();
            Observable<io.vertx.rxjava.core.buffer.Buffer> observable = file.toObservable();
            /*observable.forEach(ar->{
                System.out.println(ar);
            });*/
            observable.onErrorReturn(r -> {
                r.printStackTrace();
                return null;
            });
            observable.lift(io.vertx.rxjava.core.RxHelper.unmarshaller(MyPojo.class)).subscribe(
                    mypojo -> {
                        System.out.println("success");
                        System.out.println(mypojo.toString());
                    }
            );
        });
    }

    public static void eventBusRx() {

        io.vertx.rxjava.core.eventbus.EventBus eventBus = getVertx().eventBus();
        Subscription sub = eventBus.consumer("test").toObservable().subscribe(r -> {
            System.out.println(r.body());
        });
        getVertx().setTimer(10000, h -> {

            sub.unsubscribe();
        });


    }
    public static void eventBusRxSend(){
        io.vertx.rxjava.core.eventbus.EventBus eventBus = getVertx().eventBus();
        long deploy = getVertx().setPeriodic(1000,h->{
           eventBus.send("test","test");
        });
        getVertx().setTimer(10000,h->{
           getVertx().cancelTimer(deploy);
        });


    }

    public static void httpServerRx(){

        HttpServer httpServer = getVertx().createHttpServer();
        httpServer.requestStream().toObservable().subscribe(
                request ->{
                    Observable<MyPojo> observable = request.
                            toObservable().
                            lift(io.vertx.rxjava.core.RxHelper.unmarshaller(MyPojo.class));

                    observable.subscribe(data->{
                        System.out.println(data.toString());
                    });
                }
        );
        httpServer.rxListen(1234,"localhost").subscribe(
                success ->{
                    System.out.println("listening");
                },
                error ->{
                    error.printStackTrace();
                }

        );

    }




    public static void main(String[] args) {
        /*eventBusRxSend();
        eventBusRx();*/
        httpServerRx();
    }

}
