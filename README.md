# Spring Reactor workshop

This repository contains a workshop to introduce reactive programming using Spring Reactor.

Firstly, some theory, tips and use cases will be given. Then, some exercises will be proposed.

For additional information, take a look at the [Official documentation](https://projectreactor.io) 
Additional official training is available in the 
[Reference guide](https://projectreactor.io/docs/core/release/reference/)

## Overview

Project reactor is based in the following principles:
- I/O Multiplexing
- Callback avoidance 
- Backpressure handling

## I/O Multiplexing
I/O multiplexing is not a new technology, it has existed since the 80's and takes advantage of the OS native handling 
of file descriptors, which allows access to sockets, pipes and files to handle multiple server requests in a single 
thread being shared among multiple clients.

POSIX implementations treats access to sockets, pipes (communication between processes) and files in a standardized way.
When a file is opened, or sockets and pipes are created, a unique file descriptor is assigned to them.

Several functions are defined to create such resources and assign a file descriptor to them. Such as the ones shown 
below:

### Open file
Returns file descriptor

https://man7.org/linux/man-pages/man2/openat.2.html

### Create a pipe
Similar to open file but for process intercommunication
Returns an array of two file descriptor (for read and write)

https://man7.org/linux/man-pages/man2/pipe.2.html

### Sockets
*Socket*: Create a socket and returns a file descriptor for an unnamed socket

https://man7.org/linux/man-pages/man2/socket.2.html

*Bind*: Associates an unnamed socket to an IP address and port. This is typically done in servers

https://man7.org/linux/man-pages/man2/bind.2.html

*Listen*: Marks a socket to receive connections passively. This is done so that servers can receive connections
https://man7.org/linux/man-pages/man2/listen.2.html

*Accept*: Accepts a connection to a socket when a connection is received
https://man7.org/linux/man-pages/man2/accept.2.html

### Reading and writing
Reading and writing on an open file descriptor is done using:

*Read*:
https://man7.org/linux/man-pages/man2/read.2.html

*Write*:
https://man7.org/linux/man-pages/man2/write.2.html

Additionally, on sockets sending and receiving methods exist that allows providing additional flags to configure 
sockets communications and headers:

*Sendto*: Sends data through a socket
https://man7.org/linux/man-pages/man2/sendto.2.html

*Recvfrom*: Reads data from a socket
https://man7.org/linux/man-pages/man2/recvfrom.2.html

Notice that calls for reading and writing can be blocking, because device might be busy at hardware level, data has not 
arrived through network, or maximum network or device throughput is reached and no further data can be written at 
request time.

### Select
Typically, the OS provides buffering capabilities when reading or writing to network or file resources. I/O multiplexing
takes advantage of this fact to share a single thread to handle multiple requests (i.e. sockets).

I/O can be multiplexed using calls to *select*:
https://man7.org/linux/man-pages/man2/select.2.html

Select accepts an array of file descriptors and indicates which ones contain data to be read or written immediately 
without blocking when making read or write calls.

Typically, in servers implemented using POSIX calls, **select is called in a main loop**, to share a single thread to 
read or write data to different sockets as they become available.

![Main Loop](docs/main-loop.png)

It is important to notice that I/O multiplexing allows sharing a single thread to handle multiple requests in a server.
This is important, because each thread in a server consumes memory (the OS assigns a stacktrace to each thread), and
switching between threads represents a small context switching overhead to the CPU. Consequently, the smaller the number
of required threads to handle a given amount of traffic, the more efficient the server will be.

### Java implementation
On initial Java versions, only blocking I/O API's were provided in the SDK I/O package:

*Java IO*: Streams, sockets, file descriptors...
https://docs.oracle.com/javase/7/docs/api/java/io/package-summary.html

In Java version 1.5, the NIO package was added to provide non-blocking support for I/O calls:

*Java NIO*: Channels and selectors to do multiplexed I/O
https://picodotdev.github.io/blog-bitix/2018/04/introduccion-a-nio-2-el-sistema-de-entrada-salida-de-java/
https://docs.oracle.com/javase/8/docs/api/java/nio/package-summary.html
https://docs.oracle.com/javase/8/docs/api/java/nio/channels/package-summary.html#multiplex

Although Java NIO allowed I/O multiplexing thanks the use of channels and selectors, their usage was kind of cumbersome
due to the need of callbacks. To avoid developing within a "callback hell" for complex implementations, Futures were
also supported.

## Callback avoidance
One of the principles of Spring reactor is based on the avoidance of callbacks (as they tend to make code more 
complex), and instead an API is provided based in Flux and Mono, which work similarly to Futures in Java NIO.

- Flux: https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
- Mono: https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html

Just like Futures, a Flux or a Mono instance represents a piece of code that will be executed in the future once a 
certain condition is accomplished (e.g. data becomes available). It must be notice that a Flux, or a Mono will never be
executed until someone subscribes. If no-one is subscribed a Flux, or a mono is just an entity containing a piece of code, 
but such piece of code is not actually executed.

### Nifty features
Unlike Futures, project reactor adds some nifty features to Flux and Mono, to allow certain functionalities such as:
- Buffers: to help in backpressure handling
- Delays: in some situations we might need to throttle requests being made or received (to prevent attacks or excessive
  usage of third party services).
- Retries: to develop resilient solutions when external services are prone to error

## Flux vs. Mono
Mono defines a piece of code that will emit one or no items in the future, while Flux defines a piece of code that will 
emmit more than item in the future.
Since both entities are reactive, that means that a server does not need to receive a full request o generate a full 
response in order to start reading or writing data.
This has several advantages:
- the server can start processing a request or start sending a response sooner, decreasing delays.
- since items in reactive programming are parsed or generated as they are being received or sent, for large requests or
responses, there is no need to keep in memory a full collection of items, thus reducing the amount of required memory. 
  In extreme cases, a Flux could be used for a continuous streaming of data between servers.

It must be noticed that a flux can be converted to a Mono of a collection:
https://grokonez.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming

However, this has implications in delays (we must wait until the Flux completes) and memory usage (the whole collection 
of data emitted by the flux must be kept in memory).

## Schedulers
By default, a reactive flow is processed on a single thread (immediate scheduler), however, emitted results of a Flux 
or Mono can be published on a given scheduler, and likewise, subscription to a given Flux or Mono can also occur on a 
given scheduler.

A scheduler is an abstract representation of a thread, and Reactor provides multiple representations by default (that 
can be further customized if needed).
Some of the most important schedulers are:
- Immediate: represents the current thread. No additional thread context switching is needed.
- Parallel: Is a pool of threads which has a size equal to the number of CPU processors. This pool of threads is meant
to be used by long CPU intensive tasks that can be run in parallel. To avoid thread context switching, each thread in 
  the pool is used by each CPU core.
- Elastic: Is a pool of threads meant to be used for I/O purposes. This means that the pool should have a maximum size
equal to the number of concurrent file descriptors (e.g. sockets or requests) being handled concurrently.

Schedulers:
https://projectreactor.io/docs/core/release/api/reactor/core/scheduler/Schedulers.html

publishon vs subscribeon:
https://stackoverflow.com/questions/48073315/publishon-vs-subscribeon-in-project-reactor-3

## Reactor context
When a reactive chain of Flux and Mono is built, each Flux or Mono in the chain does not need to be processed in the 
same thread. Each step might be processed in different threads, and this has several implications in transversal 
services typically used in a server such as:
- Authentication
- Transactions
- Caching

Reactor context allows storing in an in-memory map certain data for each reactive flow that is shared between elements 
(Flux or Mono) in the reactive chain.

Access to Reactor context is done through calls like `Mono.subscriberContext(...)`. Notice that setting values in 
reactor context is done as the last step in the chain, right before subscription, and it applies to all previous 
elements in the chain up to the root element.

Thanks to reactor context, transversal functionalities such as the ones shown above can be implemented.

In Spring 5, proper support for these features has been added for reactive programming using Spring Reactor and common 
Spring annotations.

### Authentication
By default, Spring security parses JWT (Java Web Tokens) received on a request to determine a user role and permission.
Spring Security uses annotations such as @Preauthorize or @Secured to automatically handle authorization of methods 
being executed.

Additional information about method security:
https://www.baeldung.com/spring-security-method-security

It must be noticed that Spring handles usage of @Preauthorize or @Secured annotations in reactive implementations just
as it does for non-reactive ones, so that their behavior is the same. However, internally Spring uses 
`ReactiveSecurityContextHolder` to access to security information and store such data into Reactor context so that it
can live to multiple thread context switches.

Example:
```
@Test
public void setContextAndClearAndGetContextThenEmitsEmpty() {
  
  SecurityContext expectedContext = new SecurityContextImpl(
    new TestingAuthenticationToken("user", "password", "ROLE_USER"));

  Mono<SecurityContext> context = Mono.subscriberContext()
    .flatMap( c -> ReactiveSecurityContextHolder.getContext())
    .subscriberContext(ReactiveSecurityContextHolder.clearContext())
    .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(expectedContext)));
  
  StepVerifier.create(context)
    .verifyComplete();
}
```

### Transactions
Typically, transactions in non-reactive Java servers are handled through @Transactional annotation, to
ensure that multiple requests to a transactional database (typicaly SQL databases) or to queueing services (such as JMS 
queues) are handled as an atomic operation that either succeeds or fails and is rolled-back, either by using local or
distributed transactions when multiple services are involved.

In those cases, since non-reactive servers reserved on thread per request, typically 
[ThreadLocal](https://www.baeldung.com/java-threadlocal) variables were used to keep transaction status.

However, in reactive implementations, ThreadLocal variables are no longer appropriate since Thread context switching 
might occur at different steps in a reactive chain.

Again, spring comes to the rescue and is able transparently to handle @Transactional annotations in reactive chains 
thanks to Reactor context.

### Caching
As expected, @Cache is also supported by Spring in reactive implementations, however in this case some considerations
must be taken into account.

Typically, caches are either distributed (in a server) or in memory. Each cache is assigned a name, and a given type of 
object is stored by a given key.

When using @Cache in reactive implementations, care must be taken to not share the same cache name for reactive and 
non-reactive implementations, since different kinds of objects will be stored in the cache (Monos and Fluxes in reactive 
ones vs the actual values in non-reactive ones), and this will cause ClassCastException's at runtime.

## Backpressure
One of the features that Reactor provides is backpressure, which is the ability to adapt the rate at which elements are 
emitted in a reactive flow, so that a slow consumer can be able to process them.

Methods such as `limitRange()`, `onBackpressureDrop()` or `buffer()` can be used to determine a given backpressure 
strategy. However, for typical servers backpressure is handled automatically and should rarely be manually defined.

https://www.baeldung.com/spring-webflux-backpressure

https://stackoverflow.com/questions/57296097/how-does-backpressure-work-in-project-reactor

https://www.e4developer.com/2018/04/28/springs-webflux-reactor-parallelism-and-backpressure/

## Sequential and parallel executions
Flux parallel vs Mono.zip
https://www.baeldung.com/spring-webclient-simultaneous-calls

## Interleaving non-reactive code

## Branching non-reactive code

## Error handling

## Retries

## Anti-patterns

## Testing


## Exercises

### Exercise 1:

Arithmetic Sequence generation

We will implement a REST endpoint accepting minimum and maximum values to be generated sequentially in the response
and then compute the arithmetic sum of each value in the sequence:
https://www.mathsisfun.com/algebra/sequences-sums-arithmetic.html

Solution must be implemented:
- Non-reactively without pagination
- Non-reactively with pagination
- Reactively without pagination
- Reactively with pagination

For each response, execution time and memory usage must be returned.
https://stackoverflow.com/questions/17374743/how-can-i-get-the-memory-that-my-java-program-uses-via-javas-runtime-api/17376879

### Exercise 2:

Polynomial operations
- Input multiple trees of polynomial operations
- Each tree contains the following operations:
    - polynomial addition
    - polynomial subtraction
    - polynomial multiplication
- For each tree we must compute:
    - The resulting polynomial of the operations tree
    - The roots of the resulting polynomial
    - The minima of the resulting polynomial
    - The maxima of the resulting polynomial
    - The parameters of the first derivative of the polynomial
    - The parameters of the second derivative of the polynomial
    - The parameters of the integral of the polynomial
    
Solution must be implemented:
- Using rest controllers with POST endpoints to compute the polynomial operations mentioned above:
    - With a non-reactive implementation
    - With a reactive implementation
    - With a reactive implementation parallelized as much as possible
- Delays will be accepted as a query parameter on the endpoints. Delays will be added to each evaluation in each 
  provided tree of operations to compare non-reactive, vs reactive vs parallel reactive implementations

Hint: https://github.com/albertoirurueta/irurueta-numerical
