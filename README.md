# Text file to json converter

## Overview
The following application reads a text file and transforms into JSON format

## Guidelines

1. Clone this repository
2. Go to the folder and run next command (define two paths to the 'in' input and 'out' output file )
```
IN=src/test/resources/in.txt
OUT=src/test/resources/result.json

java -jar out/artifacts/big_json_converter/big-json-converter.jar in=$IN out=$OUT
```

3. After the program runtime is completed, the json file can be found in `src/test/resources/result.json`
4. Testing. The result of parsing can be observed by running junit tests.

## Nex step
In the future, the data from the text file can be saved in the database (PostgreSQL, MySQL). This approach will make it easy to make changes to the translation.&nbsp;
As an example, data can be stored in a table: &nbsp;

Table: UI_TRANSLATION

#ID | #TRANSLATION_KEY | #LANGUAGE_KEY | #TRANSLATION_TEXT 
--- | --- | --- | --- |
1 | HomePage.mainMenu.menu_administration.title | EN | Administration |
2 | HomePage.mainMenu.menu_administration.title | ES | Administración |
