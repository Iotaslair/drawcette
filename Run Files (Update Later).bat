@echo off

IF EXIST bin\ (
echo Bin already exists 
) ELSE (
echo Creating bin
mkdir bin\
)

javac src\projects\shortproj\ShortProject.java -cp src\ -d bin\
javac src\projects\shortproj\gui\TopMenu.java -cp src\ -d bin\
javac src\projects\shortproj\gui\SideBar.java -cp src\ -d bin\
javac src\projects\shortproj\gui\ColorBar.java -cp src\ -d bin\
javac src\projects\shortproj\util\Context.java -cp src\ -d bin\
javac src\projects\shortproj\gui\DrawingSurface.java -cp src\ -d bin\
javac src\projects\shortproj\util\ElementGroup.java -cp src\ -d bin\

cd bin

java -cp ".;." projects.shortproj.ShortProject