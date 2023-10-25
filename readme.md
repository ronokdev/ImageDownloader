## How to Run the app
As this is a CLI application, we can not start the application from the main class.

You need to have java 11 setup in your machine before starting the application


In order to see the app in action we need to 

- clone this git repo
- open the CLI in the root directory of this repo and run this bash file with appropriate parameter `./runImageDownloader.sh` 


```bash

# Main command 
./runImageDownloader.sh ValidPathToTheTxtFile ValidDownloadPath

# Example
./runImageDownloader.sh /Users/farhan/input.txt /Users/farhan/Downloads/
```

If as a developer you want to do further changes then, first change the code base and then run from the root directory of this repo 

```bash
 ./gradlew build && java -jar build/libs/ImageDownloader-1.0-SNAPSHOT.jar -i src/test/resources/input.txt -o src/test/resources/download/
```

Here,
- `./gradlew build` : will build the application with the new changes and create the jar file
- `java -jar build/libs/ImageDownloader-1.0-SNAPSHOT.jar` : will run the application 
- `./-i src/test/resources/input.txt` : indicates which file to read for URL
- `-o src/test/resources/download/` : indicates where it will download the images
