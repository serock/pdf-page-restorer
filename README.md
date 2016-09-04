# pdf-page-restorer
A utility that restores unviewable PDF pages

## Overview
A PDF file may contain pages that are unviewable because those pages are not in the Page Tree within the PDF file.
This program removes the pages from the PDF file's Page Tree and then adds all of the pages to the Page Tree.

## Development Environment
* Java 8
* Eclipse Neon (with Gradle 2.13)
* Git 2.6.6

## How to Build the Program with Eclipse
1. Use git to clone this repository.
2. In Eclipse, import a Gradle project specifying the directory created when this repository was cloned.

## How to Run the Program
The program requires two arguments:

1. The PDF file that has unviewable pages.
2. The new PDF file to be created.

## License
This program is licensed under the MIT License (same as the Expat License).
