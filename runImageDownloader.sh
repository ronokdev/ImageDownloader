#!/bin/bash

JAR_FILE="src/main/resources/ImageDownloader-1.0-SNAPSHOT.jar"

# Function to run the JAR file
run_jar() {
  echo "Running $JAR_FILE..."
  java -jar "$JAR_FILE" -i "$1" -o "$2"
}

detect_os() {
  OS=$(uname)
  case "$OS" in
    "Darwin")
      # macOS
      echo "Detected macOS."
      if [ -x "$(command -v java)" ]; then
        run_jar
      else
        echo "Java is not installed on macOS. Please install it manually."
        exit 1
      fi
      ;;

    "Linux")
      # Linux
      echo "Detected Linux. Installing Java is not implemented in this example."
      echo "You should install Java manually and then run the JAR file."
      exit 1
      ;;

    "MINGW"*)
      # Windows (MinGW/MSYS)
      echo "Detected Windows."
      if [ -x "$(command -v java)" ]; then
        run_jar
      else
        echo "Java is not installed on Windows. Please install it manually."
        exit 1
      fi
      ;;

    *)
      echo "Unsupported operating system: $OS"
      exit 1
      ;;
  esac
}


# Check if Java is already installed
if command -v java &>/dev/null; then
  echo "Java is already installed."
  run_jar "$1" "$2"
else
  # Detect the operating system
  detect_os
fi
