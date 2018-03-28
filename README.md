# demo-bug-lift-session
Demostratin Lift bug

This project demo lift bugs https://github.com/lift/framework/issues/1946 and https://github.com/lift/framework/issues/1947

To run it, just exec `./sbt` and when it finally gives you the hand, `jetty:start`. 
The server is started on `http://localhost:8080`.

Now, open you JS console debugger on "network" tab, play with the links between statefull/stateless pages, and observe the two problems:

- the problem with `changeSessionId` is triggered with link `Go to statefull page with session swap and redirect`
- for the other problem, you just have to come back with the link `destroy container session` to observe it. 

In both cases, you will see "HTTP 404 errors" for `/lift/page/xxxx.js`
