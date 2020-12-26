set currDir=%~dp0
set currDir=%currDir:~0,-1%
set baseDir=%currDir%\..
set springStaticDir=%baseDir%\src\main\resources\static

@REM build frontend
cd "%baseDir%\react-spa"
call npm run build

@REM serve react app with spring
rmdir /Q /S "%springStaticDir%"
move "build" "%springStaticDir%"
@REM rmdir /Q /S build

@REM run spring boot app
cd "%baseDir%"
call mvnw.cmd spring-boot:run

