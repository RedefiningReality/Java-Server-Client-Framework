This is a framework collection created by John Ford. See the source code within the ServerExample and ClientExample for example usage.
It consists of the following:
	1. A Graphical User Interface framework built off of JavaFX
	2. A set of standard libraries for logging information to a log file as well as reading from a properties file
	3. A Server-Client framework built off of the standard Java Input-Output tools
All frameworks within the collection are compatible and designed to be used in conjunction with one another.

Libraries within the framework:
	1. Standard Libraries (slibs)
		- logging to a log file
		- reading from and writing to a properties file
	2. Graphical User Interface Templates (guit)
		- creating graphical dialogs with ease
		dependencies: slibs
	3. Common Server-Client Data (cscdata)
		- storage of transfers
		- unused classes for easy reading from and writing to channels
		dependencies: slibs, guit
	4. Server Framework (srvrfw)
		- server side of server-client connections
		- properties file information storage (if desired)
		dependencies: slibs, cscdata
	5. Client Framework (clntfw)
		- client side of server-client connections
		- pre-created graphical user interface (if desired)
		- properties file information storage (if desired)
		dependencies: slibs, cscdata, guit

The Data Structures and Algorithms concepts utilised in the various frameworks:
	1. Stack => used by the client for transfer selection
	2. Queue => used to send protocols and execute transfers in sequential order
	3. Linked-List => used to bridge together the aforementioned stack and Queue
	4. ArrayList => various purposes, most notably storing values for the various dialogs
	5. HashMap => used by both the server and the client for storing developer-defined transfers
	6. Sorting Algorithms => various purposes
	7. Multithreading => used by the server to connect to multiple clients

Bugs and future releases:
	1. The standard Input-Output tools are used for Client-Server communication, and therefore require multithreading in order to connect one server to multiple clients. Therefore, this framework should not be used on servers which require a large amount of simultaneous client connections. This can be fixed by modifying the server and client frameworks to use the Java New Input-Output packages (SocketChannel rather than Socket). Two classes have already been created for easy reading and writing to and from socket channels. These classes are currently located in cscdata and are not being used.
	2. The server fails to disconnect properly from the client after the completion of all transfers. It only disconnects after the user of the client closes the application. This does not pose a problem in any sense, but can be quite a nuisance.
	3. The graphical chat dialogue is yet to be completed.
	4. The documentation is yet to be completed.

A list of a few sources used in no particular order:
	https://www.tutorialspoint.com/java/java_stack_class.htm
	https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
	http://crunchify.com/java-properties-files-how-to-update-config-properties-file-in-java/
	http://www.informit.com/articles/article.aspx?p=26316&seqNum=3
	http://code.makery.ch/blog/javafx-dialogs-official/
	http://stackoverflow.com/questions/29625170/display-popup-with-progressbar-in-javafx
	http://tutorials.jenkov.com/java-nio/nio-vs-io.html
	Java 8 Documentation

The client framework was based off of ClientGUI. The server framework was based off of ApplicationServer. Both of these are from projects which I was previously working on yet never completed.
