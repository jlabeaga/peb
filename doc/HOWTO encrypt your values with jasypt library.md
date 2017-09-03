## HOWTO easily encrypt your secret values in your properties files using jasypt library

Let's explain this task with an example. Imagine that you have 2 property values in your `application.properties` file of your Spring Boot application:

```
spring.mail.username=me@gmail.com  
spring.mail.password=myGmailPassword
```

####3 steps:

1. Use the jasypt command line utility to encrypt all your secret values
    * Download the jasypt utility from [http://www.jasypt.org](http://www.jasypt.org). I put a copy of version 1.9.2 under the `utils`folder
    * Choose your own encryption password (i.e. "myEncPassword")
    * Cd to the `/bin` folder and proceed to encrypt your values with the command:
    ```
    ./encrypt.sh input=me@gmail.com password=myEncPassword
    ```
    this will return the encrypted output that you will use in your property file
    ```
    output = 8IS4pDLRMPto6NmP2YH+xXtuE5xByIbW
    ```
    * The same goes for the `spring.mail.password`property value:
    ```
    ./encrypt.sh input=myGmailPassword password=myEncPassword
    ```
    which returns the encrypted value for `myGmailPassword`
    ```
    output = x/4EYZ8L8CdnMs6YSm0TQ2CxWILWsFl3
    ```
2. Substitute all your secret values by their encrypted counterparts in your `application.properties` file and dont forget to use the ENC() prefix:

```
spring.mail.username=ENC(8IS4pDLRMPto6NmP2YH+xXtuE5xByIbW)  
spring.mail.password=ENC(x/4EYZ8L8CdnMs6YSm0TQ2CxWILWsFl3)
```

3. Enable jasypt to be used for value encryption/decryption in your Spring Boot application
    * Include the jasypt library in your app by adding this entry in your pom.xml maven file:
    ```xml
		<dependency>
	        <groupId>com.github.ulisesbocchio</groupId>
	        <artifactId>jasypt-spring-boot-starter</artifactId>
	        <version>1.11</version>
		</dependency>
	```    
    * Add a `@EnableEncryptableProperties`annotation to your SpringBootApplication class so that Spring will insprect your property files for the default prefix 
    ```Java
    @SpringBootApplication
	@EnableEncryptableProperties
	public class PebApplication {
	```
    * Add the VM argument `-Djasypt.encryptor.password=myEncPassword`when launching your SpringBoot app so that jasypt can use it without saving it any config file 	

You're done! Start your Spring Boot app and it should work

More info at [http://www.jasypt.org](http://www.jasypt.org)
