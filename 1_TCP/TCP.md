

## TCP

TCP 协议提供面向连接的服务，通过它建立的是可靠地连接


## 一、JAVA 的 TCP支持

Java 为 TCP 协议提供了两个类
1. Socket 类
2. ServerSocket 类


### Socket类

Socket 实例代表了 TCP 连接的一个 **客户端**

### ServerSocket

 ServerSocket 实例代表了 TCP 连接的一个 **服务器端**

## 二、客户端-服务端模型

在TCP Socket编程中来说

### 客户端

客户端的 Socket 可以有多个

客户端 TCP 向服务器端 TCP 发送连接请求

### 服务端

1、一个连接请求，对应一个Socket 实例

服务器端的 ServerSocket 实例则监听来自客户端的 TCP 连接请求，并为每个请求创建新的 Socket 实例

2、每个 Socket 连接，需要一个线程处理

由于服务端在调用 accept（）等待客户端的连接请求时会阻塞，直到收到客户端发送的连接请求才会继续往下执行代码，因此要为每个 Socket 连接开启一个线程。

3、服务器端要同时处理 ServerSocket 实例和 Socket 实例

而客户端只需要使用 Socket 实例。

## 三、Socket 实例

 每个 Socket 实例会关联一个 InputStream 和 OutputStream 对象，我们通过将字节写入套接字的 OutputStream 来发送数据，并通过从 InputStream 来接收数据

## 四、TCP 连接的建立过程

### 客户端连接

* 创建一个 Socket 实例：构造函数向指定的远程主机和端口建立一个 TCP 连接
* 通过套接字的 I/O 流与服务端通信
* 使用 Socket 类的 close 方法关闭连接

注意，客户端需要像服务端主动发起连接，而服务端只能被动的等待连接

### 服务端连接

1、创建一个 ServerSocket 实例并指定本地端口，用来监听客户端在该端口发送的 TCP 连接请求；

2、重复执行：

> 调用 ServerSocket 的 accept（）方法以获取客户端连接，并通过其返回值创建一个 Socket 实例
>
> 为返回的 Socket 实例开启新的线程，并使用返回的 Socket 实例的 I/O 流与客户端通信； 通信完成后，使用 Socket 类的 close（）方法关闭该客户端的套接字连接









---
